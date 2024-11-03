import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.ListBuffer

class TLFunSUite extends AnyFunSuite {
  private val treeExample = Tree("", ListBuffer(Tree("", ListBuffer(Tree("a"), Tree("bb"))), Tree("ccc"), Tree("ddd")))
  test("check that empty trees are equal") {
    val t = Tree()
    val t1 = Tree()
    assert(t == t1)
  }

  test("equals works with nested trees") {
    val t = Tree(nodes = ListBuffer(Tree(nodes = ListBuffer(Tree("a"))), Tree("b"))) // ((a) b)
    val t1 = Tree(nodes = ListBuffer(Tree(nodes = ListBuffer(Tree("a"))), Tree("b")))
    val t2 = Tree(nodes = ListBuffer(Tree(nodes = ListBuffer(Tree("b"))), Tree("a"))) // ((b) a)
    println(t2)
    assert(t == t1)
    assert(t != t2)
  }

  test("string method works on nested trees") {
    println(treeExample)
    assert(treeExample.toString == "((a bb) ccc ddd)")
  }

  test("tree parser throws exception if invalid string passed") {
    val s = "(%%%%%)"
    val e = intercept[RuntimeException] {
      TreeUtils.parseTree(s)
    }
    assert(e.getMessage == "invalid char found during parseTree")
  }

  test("tree parser works on empty tree") {
    val s = "()"
    assert(TreeUtils.parseTree(s) == Tree())
  }

  test("tree parser works on tree with single child") {
    val s = "(a)"
    assert(TreeUtils.parseTree(s) == Tree("", ListBuffer(Tree("a"))))
  }

  test("tree parser works correctly") {
    val s = "((a bb) ccc ddd)"
    assert(TreeUtils.parseTree(s) == treeExample)
  }

  test("tree parser with multiple trees on the same level works correctly") {
    val s = "(((a) bb) (ccc ddd) (eee))"
    val tComplex = Tree(nodes = ListBuffer(Tree(nodes = ListBuffer(Tree(nodes = ListBuffer(Tree("a"))), Tree("bb"))),
      Tree(nodes = ListBuffer(Tree("ccc"), Tree("ddd"))),
      Tree(nodes = ListBuffer(Tree("eee")))
    ))
    println(tComplex)
    assert(TreeUtils.parseTree(s) == tComplex)
  }

  test("replace works correctly on test example") {
    val s = "((a bb) ccc eee)"
    assert(TreeUtils.replace(TreeUtils.parseTree(s), Tree("eee"), Tree("ddd")) == treeExample)
  }

  test("replace works correctly on nested trees") {
    val s = "((a bb) (fff) eee)"
    val t = TreeUtils.replace(TreeUtils.parseTree(s), Tree("eee"), Tree("ddd"))
    assert(TreeUtils.replace(t, Tree(nodes = ListBuffer(Tree("fff"))), Tree("ccc")) == treeExample)
  }

  test("replace can replace multiple trees") {
    val s = "(a (a))"
    val t = TreeUtils.replace(TreeUtils.parseTree(s), Tree("a"), Tree("b"))
    val result = "(b (b))"
    assert(t == TreeUtils.parseTree(result))
  }
}
