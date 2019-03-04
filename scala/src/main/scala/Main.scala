import scala.collection.mutable
import scala.util.Sorting

object Main extends App {
  def getTimeMs(block: => Unit): Long = {
    val start = System.currentTimeMillis()
    block
    System.currentTimeMillis() - start
  }

  def sumPrimesLong(n: Int): Long = {
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

  def sumPrimesInt(n: Int): Int = {
    val primes = mutable.ArrayBuffer[Int]()
    var current: Int = 2
    var sum: Int = 0

    while (primes.length < n) {
      if (primes.forall(current % _ != 0)) {
        sum += current
        primes += current
      }
      current += 1
    }
    sum
  }

  if (args.length != 1) {
    throw new IllegalArgumentException("Expect 1 argument.")
  }

  val sumPrimes: Int => Long = if (args(0) == "--uint64") sumPrimesLong
  else if (args(0) == "--uint32") sumPrimesInt
  else throw new IllegalArgumentException("Unknown arguments: " + args(0) + ".")

  val cnt = 12
  val timesMs = new Array[Long](cnt)
  for (index <- 0 until cnt) {
    timesMs(index) = getTimeMs(
      assert(sumPrimes(10000) == 496165411))
  }
  Sorting.quickSort(timesMs)

  val slowest = timesMs.last
  val average = timesMs.slice(1, timesMs.length - 1).sum.toDouble / (cnt - 2)
  printf(" %-13s| %.1fms\n", slowest.toString + "ms", average)
}