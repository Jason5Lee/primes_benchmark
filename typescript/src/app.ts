function sumPrimes(n: number): number {
    let primes: number[] = []
    let current: number = 2
    let sum: number = 0

    while (primes.length < n) {
        if (primes.every(p => current % p != 0)) {
            sum += current
            primes.push(current)
        }
        ++current
    }

    return sum
}

function getTimeMs(block: () => void): number {
    let start = new Date().getTime()
    block()
    return new Date().getTime() - start
}

function padSlowest(n: number): string {
    const padString = "ms           "
    return (String(n) + padString).slice(0, padString.length)
}

const cnt = 12
let timesMs = Array(cnt)
for (let index = 0; index < cnt; ++index) {
    timesMs[index] = getTimeMs(() =>
        console.assert(sumPrimes(10000) == 496165411))
}
timesMs.sort()

let slowestMs = timesMs[timesMs.length - 1]
let averageMs = timesMs.slice(1, -1)
    .reduce((a, b) => a + b) / (cnt - 2)
console.log(` ${padSlowest(slowestMs)}| ${averageMs}ms`)
