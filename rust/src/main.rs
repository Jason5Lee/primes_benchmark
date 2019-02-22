use std::time::Instant;

fn sum_primes(n: usize) -> u64 {
    let mut primes = Vec::new();
    let mut current: u64 = 2;
    let mut sum: u64 = 0;

    while primes.len() < n {
        if primes.iter().all(|p| current % p != 0) {
            sum += current;
            primes.push(current);
        }
        current += 1;
    }
    sum
}

fn get_time_ms(block: impl Fn()) -> u64 {
    let start = Instant::now();
    block();
    let elapsed = Instant::now() - start;
    (elapsed.as_secs() * 1_000) + (elapsed.subsec_nanos() / 1_000_000) as u64
}

fn main() {
    const CNT: usize = 12;
    let mut times_ms = [0u64; CNT];
    for index in 0..CNT {
        times_ms[index] = get_time_ms(||
            assert!(sum_primes(10000) == 496165411));
    }
    times_ms.sort();

    let slowest_ms = times_ms.last().unwrap();
    let average_ms = (&times_ms[1..times_ms.len() - 1]).iter()
        .sum::<u64>() as f64 / (CNT - 2) as f64;
    println!("{}\t{}", slowest_ms, average_ms);
}
