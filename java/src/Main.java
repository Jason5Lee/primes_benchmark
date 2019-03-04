import java.util.*;
import java.util.function.IntToLongFunction;

public class Main {
    private static long sumPrimesLong(int n) {
        List<Long> primes = new ArrayList<>();
        long current = 2;
        long sum = 0;

        while (primes.size() < n) {
            final long judge = current;
            if (primes.stream().allMatch(p -> judge % p != 0)) {
                sum += current;
                primes.add(current);
            }
            ++current;
        }

        return sum;
    }

    private static int sumPrimesInt(int n) {
        List<Integer> primes = new ArrayList<>();
        int current = 2;
        int sum = 0;

        while (primes.size() < n) {
            final long judge = current;
            if (primes.stream().allMatch(p -> judge % p != 0)) {
                sum += current;
                primes.add(current);
            }
            ++current;
        }

        return sum;
    }

    private static long getTimeMs(Runnable block) {
        long start = new Date().getTime();
        block.run();
        return new Date().getTime() - start;
    }

    public static void main(String[] args) {
        final IntToLongFunction sumPrimes;

        if (args.length != 1) {
            throw new IllegalArgumentException("Expect 1 argument.");
        }
        if (args[0].equals("--uint32")) {
            sumPrimes = n -> sumPrimesInt(n);
        } else if (args[0].equals("--uint64")) {
            sumPrimes = n -> sumPrimesLong(n);
        } else {
            throw new IllegalArgumentException("Unknown argument: " + args[0] + ".");
        }
        
        final int cnt = 12;
        long[] timesMs = new long[cnt];
        for (int index = 0; index < 12; ++index) {
            timesMs[index] = getTimeMs(() -> {
                if (sumPrimes.applyAsLong(10000) != 496165411) {
                    throw new AssertionError();
                }
            });
        }
        Arrays.sort(timesMs);

        long slowest = timesMs[timesMs.length - 1];
        double average = (double)Arrays.stream(timesMs)
                .skip(1)
                .limit(cnt - 2)
                .sum() / (cnt - 2);
        System.out.printf(" %-13s| %.1fms\n", Long.toString(slowest) + "ms", average);
    }
}
