function sum_primes(n: number): number {
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

function get_time_ms(block: () => void): number {
    let start = new Date().getTime()
    block()
    return new Date().getTime() - start
}

const cnt = 12

let times_ms = Array(cnt)
for (let index = 0; index < cnt; ++index) {
    times_ms[index] = get_time_ms(() =>
        console.assert(sum_primes(10000) == 496165411))
}
times_ms.sort()

let slowest_ms = times_ms[times_ms.length - 1]
let average_ms = times_ms.slice(1, -1)
    .reduce((a, b) => a + b) / (cnt - 2)
console.log(`${slowest_ms}\t${average_ms}`)
