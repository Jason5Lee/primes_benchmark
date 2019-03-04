use std::time::{Instant, Duration};

fn sum_primes_u64(n: usize) -> u64 {
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

fn sum_primes_u32(n: usize) -> u32 {
    let mut primes = Vec::new();
    let mut current: u32 = 2;
    let mut sum: u32 = 0;

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

const LENGTH: usize = 10000;
const EXPECT_SUM: u32 = 496165411;
const CNT: usize = 12;

fn assert_u32() {
    assert!(sum_primes_u32(LENGTH) == EXPECT_SUM)
}

fn assert_u64() {
    assert!(sum_primes_u64(LENGTH) == EXPECT_SUM as u64)
}
fn main() {
    let mut args = std::env::args();
    args.next(); // Skip file path.
    let arg = args.next().expect("Error: Expect 1 argument.");
    if args.next().is_some() {
        panic!("Error: Expect 1 argument.");
    }
    let body: fn() = if arg == "--uint32" {
        assert_u32
    } else if arg == "--uint64" {
        assert_u64
    } else {
        panic!(format!("Unknown argument: {}.", arg))
    };

    let mut times: Vec<Duration> = Vec::new();
    times.reserve(CNT);
    for _ in 0..CNT {
        times.push(get_duration(body))
    }
    times.sort();

    let slowest = *times.last().unwrap();
    let average = (&times[1..times.len() - 1]).iter()
        .sum::<Duration>() / (CNT - 2) as u32;
    println!(" {}| {:?}", pad_slowest(slowest), average);
}
