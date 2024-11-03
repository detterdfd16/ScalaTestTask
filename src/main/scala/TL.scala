import scala.collection.mutable.ListBuffer

/** Non-binary tree implementation for TL
 * @constructor default values are empty fields: "" for id, ListBuffer() for nodes
 * @param id corresponds to ID field in TL, empty string equals no id
 * @param nodes corresponds NODE field in TL
 */
class Tree(var id: String = "", var nodes: ListBuffer[Tree] = ListBuffer()) {

  override def equals(obj: Any): Boolean = obj match {
    case that: Tree => id == that.id && nodes == that.nodes
    case _ => false
  }

  override def toString: String = {
    val sb = StringBuilder()
    if (id == "") {
      sb.append(s"(${nodes.mkString(" ")})")
    } else {
      sb.append(id)
    }
    sb.toString()
  }
}

/** Utility object for operations on TL trees */
object TreeUtils {
  private val validIdSymbols = "[a-zA-Z0-9]+".r

  /**
   * String to Tree parser
   * Throws RunTimeException if Tree is not in TL
   * @param s string representation of Tree in TL
   * @return Tree object represented by s
   */
  def parseTree(s: String): Tree = {
    var t = Tree()
    var depth = 0
    s(0) match {
      case '(' => t = parseTreeHelper(s)
      case _ => if (validIdSymbols.matches(s)) {
        t = Tree(s)
      } else {
        throw RuntimeException("invalid char found during parseTree")
      }
    }
    t
  }

  private def parseTreeHelper(s: String): Tree = {
    val t = Tree()
    var id = ""
    var depth = 0
    var start = -1
//    println("passed string: " + s)
    for (i <- 1 until s.length - 1) {
      s(i) match {
        case '(' => depth += 1
        if (depth == 1) {
          start = i
        }
        case ')' => depth -= 1
        case ' ' => if (depth == 0 && id.nonEmpty) {
          t.nodes += Tree(id)
          id = ""
        }
        case a => if (depth == 0) {
          if (!validIdSymbols.matches(a.toString)) {
            throw RuntimeException("invalid char found during parseTree")
          }
          id += a
        }
      }
      if (depth == 0 && start != -1) {
//        println("sub tree: " + s.substring(start, i + 1))
        t.nodes += parseTreeHelper(s.substring(start, i + 1))
        start = -1
      }
//      println(depth.toString + " " + t + " " + s + " " + id)
    }
    if (id != "") {
      t.nodes += Tree(id)
      id = ""
    }
    t
  }

  /**
   * Replaces specified tree by another in given tree
   * @param tree tree to be iterated through
   * @param searchTree tree to replaced 
   * @param replacement tree to replace with 
   * @return copy of tree with replaced searchTree's
   */
  def replace(tree: Tree, searchTree: Tree, replacement: Tree): Tree = {
    if (tree == searchTree) {
      return replacement
    }
    for (i <- tree.nodes.indices) {
      if (tree.nodes(i) == searchTree) {
        tree.nodes(i) = replacement
      } else {
        tree.nodes(i) = replace(tree.nodes(i), searchTree, replacement)
      }
    }
    tree
  }
}
