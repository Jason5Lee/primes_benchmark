import java.util.*;

public class Main {
    private static long sumPrimes(int n) {
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

    private static long getTimeMilliseconds(Runnable block) {
        long start = new Date().getTime();
        block.run();
        return new Date().getTime() - start;
    }

    public static void main(String[] args) {
        final int cnt = 12;
        long[] times = new long[cnt];
        for (int index = 0; index < 12; ++index) {
            times[index] = getTimeMilliseconds(() -> {
                if (sumPrimes(10000) != 496165411) {
                    throw new AssertionError();
                }
            });
        }
        Arrays.sort(times);

        long slowest = times[times.length - 1];
        long average = Arrays.stream(times)
                .skip(1)
                .limit(cnt - 2)
                .sum() / (cnt - 2);
        System.out.printf("%d\t%d\n", slowest, average);
    }
}
