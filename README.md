# Primes Benchmark

Comparing the performance of programming languages by computing sum of the first 10000 primes.

## Benchmark

Run `./benchmark` with the arguments that specify the benchmark languages to start. All languages, requirements and corresponding arguments are listed below.

| Language   | Requirement | Argument       |
| ---------- | ----------- | -------------- |
| JavaScript | node        | `--javascript` |
| Rust       | cargo       | `--rust`       |
| Java       | JDK8+       | `--java`       |
| Scala      | sbt         | `--scala`      |
| Clojure    | Deps CLI    | `--clj`        |

You can also use `--all` argument to benchmark all languages.

Unsigned 64-bit integer are the default number type if possible. You can add `--uint32` flag to use unsigned 32-bit integer.

## Stratege

The benchmark programs will implement a function that caculating the sum of the first 10000 primes. It will use the following algorithm.

1. Use a array-based dynamic-length list `primes` to stored the primes. It is initialized to an empty list.
2. Use a variable `prime` for the current prime, initialized to `2`.
3. Use a variable `sum` for the sum of the primes, initialized to `0`.
4. If for all `p` in `primes`, `prime % p != 0`, goto 5, else goto 8.
5. Add `prime` into `primes`.
6. Update `sum` with `sum + prime`.
7. If the length of `primes` equals `10000`, return `sum`.
8. Update `prime` with `prime + 1`.
9. goto 4

The implementation of the algorithm above is basically imperative. But for step 4, the predication is implemented in a functional declarative expression intentionally, e.g. `Array.every` method for JavaScript.

### Result Analysis

The function will be executed for 12 times, checking whether the result is correct, and collecting the times each execution spends. There're two indicators: slowest time and average time. The average time is the average except the slowest and fastest execution.

## Suprising Result

When I run the benchmark program on my computer, I got the following output.

| Language   | Slowest time | Average time |
| ---------- | ------------ | ------------ |
| JavaScript | 185ms        | 154.5ms      |
| Rust       | 324.539236ms | 318.255628ms |
| Scala      | 513ms        | 396.9ms      |
| Java       | 792ms        | 777.1ms      |

It suprises me that JavaScript, the only dynamic language, is the fastest one, even faster than Rust.

**Update:** According to the answers on [stackoverflow](https://stackoverflow.com/questions/54828815/why-is-typescript-on-nodejs-faster-than-rust-in-computing-the-sum-of-the-primes), [zhihu](https://www.zhihu.com/question/313287251), and [#1](https://github.com/Jason5Lee/primes_benchmark/issues/1), this is because that Node uses 32-bit integer, and it can be "fixed" by making every programs using 32-bit integer. But I think if this is the reason why Node is fast, Node deserves it. Node can perform number type optimization while others cannot. Still, I add an optional flag allowing to run the benchmark with 32-bit integer, and in that case, Rust is the fastest as the expectation.
