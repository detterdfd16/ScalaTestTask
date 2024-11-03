import org.scalatest.funsuite.AnyFunSuite
import scala.math.pow

class MainTestSuite extends AnyFunSuite{
  test("f(0) computes correctly") {
    assert(f(0) == 1)
  }

  test("f(x) computes for x >= 0 correctly") {
    for (i <- 0 to 10) {
      assert(f(i) == pow(2, i).toInt)
    }
  }
}
