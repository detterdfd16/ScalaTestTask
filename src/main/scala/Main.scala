@main def run(): Unit =
  println("123")
  println(f(0))
  println(f(1))
  println(f(3))

/**
 * Recursive function given in warmup, equivalent to pow(2, x).toInt
 * Time complexity of this function if exponential O(2**n):
 * Each f(x) calls 2 f(x - 1), which call 4 f(x - 2), and so on.
 * Time complexity can be improved significantly:
 * 1. f(x - 1) + f(x - 1) = 2 * f(x - 1) (by arithmetic)
 * This would reduce time complexity to linear O(n)
 * 2. We can cache in array/dictionary previously calculated values for f(x) (memoization)
 * This would also reduce time complexity to O(n), but would increase memory complexity from O(1) to O(n)
 * 3. We can use provided functions pow and Double.toInt in Scala libraries, giving us lowest time complexity(~O(1))
 *
 * @param x power to which 2 is raised
 * @return 2 raised to the power of x
 */
def f(x: Int): Int =
  if (x == 0) {
    1
  } else {
    f(x - 1) + f(x - 1)
  }