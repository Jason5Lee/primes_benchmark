use std::time::{Instant, Duration};

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

fn get_duration(block: impl Fn()) -> Duration {
    let start = Instant::now();
    block();
    Instant::now() - start
}

fn pad_slowest(slowest: Duration) -> String {
    let mut s = format!("{:?}", slowest);
    while s.len() < 13 {
        s.push(' ')
    }
    s
}

fn main() {
    const CNT: usize = 12;
    let mut times: Vec<Duration> = Vec::new();
    times.reserve(CNT);
    for _ in 0..CNT {
        times.push(get_duration(||
            assert!(sum_primes(10000) == 496165411)))
    }
    times.sort();

    let slowest = *times.last().unwrap();
    let average = (&times[1..times.len() - 1]).iter()
        .sum::<Duration>() / (CNT - 2) as u32;
    println!(" {}| {:?}", pad_slowest(slowest), average);
}
