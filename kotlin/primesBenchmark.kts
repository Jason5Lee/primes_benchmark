val length = 10000
val expectSum = 496165411
val cnt = 12

fun getTimeMs(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

fun sumPrimesLong(n: Int): Long {
    val primes = mutableListOf<Long>()
    var current: Long = 2
    var sum: Long = 0

    while (primes.size < n) {
        if (primes.all { current % it != 0.toLong() }) {
            sum += current
            primes.add(current)
        }
        current += 1
    }
    return sum
}

fun sumPrimesInt(n: Int): Int {
    val primes = mutableListOf<Int>()
    var current: Int = 2
    var sum: Int = 0

    while (primes.size < n) {
        if (primes.all { current % it != 0 }) {
            sum += current
            primes.add(current)
        }
        current += 1
    }
    return sum
}

fun assertInt() {
    if (sumPrimesInt(length) != expectSum) {
        throw AssertionError("Wrong answer")
    }
}

fun assertLong() {
    if (sumPrimesLong(length) != expectSum.toLong()) {
        throw AssertionError("Wrong answer")
    }
}

if (args.size != 1) {
    throw IllegalArgumentException("Expect 1 argument.")
}

val body: () -> Unit = when {
    args[0] == "--uint64" -> {
        { assertLong() }
    }
    args[0] == "--uint32" -> {
        { assertInt() }
    }
    else -> throw IllegalArgumentException("Unknown arguments: ${args[0]}.")
}

val timesMs = mutableListOf<Long>()
for (i in 1..cnt) {
    timesMs.add(getTimeMs(body))
}
timesMs.sort()
val slowest = timesMs.last()
val average = timesMs.subList(1, timesMs.size-1).sum().toDouble() / (cnt - 2)
println(" %-13s| %-13s|".format(slowest.toString() + "ms", average.toString() + "ms"))
