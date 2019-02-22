# Primes Benchmark

Comparing the performance of programming languages by computing sum of the first 10000 primes.

## Depedencies

* **TypeScript** `npm` 6.5.0, `tsc` 3.0.1.
* **Rust** `cargo` 1.32.0, `rustc` 1.32.0.
* **Java** JDK 8.
* **Scala** `sbt` 1.2.8.

## Run Benchmark

Run benchmark by running script `./benchmark`.

### Parameter

* `--typescript` includes TypeScript.
* `--rust` includes Rust.
* `--scala` includes Scala.
* `--java` includes Java.
* `--all` includes all languages above.

## Benchmark stratege

### Benchmark Speicification

The benchmark programs will implement a function that caculating the sum of the first 10000 primes. It will use the following algorithm.

1. Use a array-based dynamic-length list `primes` to stored the primes. It is initialized to empty.
2. Use a variable `prime` for the current prime, initialized to `2`.
3. Use a variable `sum` for the sum of the primes, initialized to `0`.
4. If for all `p` in `primes`, `prime % p != 0`, goto `5`, else goto `8`.
5. Add `prime` into `primes`.
6. Update `sum` with `sum + prime`.
7. If the length of `primes` equals `10000`, return `sum`.
8. Update `prime` with `prime + 1`.
9. goto 4

The implementation of the algorithm above is basically imperative. But for step 4, the predication is implemented in a functional declarative expression intentionally, e.g. `Array.every` method of TypeScript.

### Time computation

The rest of the program are about measuring the time. The programming will execute the function for 12 times, checking whether the result is right, and collecting the time each execution spends. There're two indicators: slowest time and average time. Slowest time is the time it spends at the slowest execution. Average time is the average of the time except the slowest and fastest execution.

## Suprising Result

When I run the benchmark program on my computer, I got the following output.

```
Language   | Slowest time(ms) | Average time(ms)
------------------------------------------------
TypeScript | 406              | 331.1
Rust       | 594              | 551.2
Scala      | 1135             | 787.900000
Java       | 1768             | 1586.800000
```

It suprises me that TypeScript, the only dynamic language, is the fastest one, even faster than Rust. It is also out of my expectation that Scala is faster than Java.