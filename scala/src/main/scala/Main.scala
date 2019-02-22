import scala.collection.mutable
import scala.util.Sorting

object Main extends App {
  def getTimeMilliseconds[R](block: => Unit): Long = {
    val start = System.currentTimeMillis()
    block
    System.currentTimeMillis() - start
  }

  def sumPrimes(n: Int): Long = {
    val primes = mutable.ArrayBuffer[Long]()
    var current: Long = 2
    var sum: Long = 0

    while (primes.length < n) {
      if (primes.forall(current % _ != 0)) {
        sum += current
        primes += current
      }
      current += 1
    }
    sum
  }

  val cnt = 12
  val timesMs = new Array[Long](cnt)
  for (index <- 0 until cnt) {
    timesMs(index) = getTimeMilliseconds(
      assert(sumPrimes(10000) == 496165411))
  }
  Sorting.quickSort(timesMs)

  val slowest = timesMs.last
  val average = timesMs.slice(1, timesMs.length - 1).sum.toDouble / (cnt - 2)
  printf(" %-17d| %f\n", slowest, average)
}