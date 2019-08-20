using System;
using System.Linq;
using System.Diagnostics;
using System.Collections.Generic;

namespace primes_benchmark
{
    class Program
    {
        const int length = 10000;
        const uint expectSum = 496165411;
        const int cnt = 12;

        static TimeSpan GetTime(Action block)
        {
            var stopWatch = new Stopwatch();
            stopWatch.Start();
            block();
            stopWatch.Stop();
            return stopWatch.Elapsed;
        }

        static ulong SumPrimesLong(int n)
        {
            var primes = new List<ulong>();
            ulong current = 2;
            ulong sum = 0;

            while (primes.Count < n)
            {
                if (primes.TrueForAll(p => current % p != 0))
                {
                    sum += current;
                    primes.Add(current);
                }
                ++current;
            }
            return sum;
        }

        static uint SumPrimesInt(int n)
        {
            var primes = new List<uint>();
            uint current = 2;
            uint sum = 0;

            while (primes.Count < n)
            {
                if (primes.TrueForAll(p => current % p != 0))
                {
                    sum += current;
                    primes.Add(current);
                }
                ++current;
            }
            return sum;
        }

        static void AssertInt()
        {
            if (SumPrimesInt(length) != expectSum)
            {
                throw new Exception("Wrong answer");
            }
        }

        static void AssertLong()
        {
            if (SumPrimesLong(length) != expectSum)
            {
                throw new Exception("Wrong answer");
            }
        }

        static string PadTime(TimeSpan time)
        {
            return (time.Milliseconds.ToString() + "ms").PadRight(13);
        }

        static void Main(string[] args)
        {
            if (args.Length != 1)
            {
                throw new ArgumentException("Expect 1 argument.");
            }
            Action body;
            if (args[0] == "--uint64")
            {
                body = AssertLong;
            } else if (args[0] == "--uint32")
            {
                body = AssertInt;
            } else
            {
                throw new ArgumentException($"Unknown argument {args[0]}.");
            }

            var times = new TimeSpan[cnt];
            for (int i = 0; i < cnt; ++i)
            {
                times[i] = GetTime(body);
            }
            Array.Sort(times);

            var lowest = times[times.Length - 1];
            var average = times.Skip(1).SkipLast(1)
                .Aggregate((t1, t2) => t1 + t2) / (cnt - 2);
            Console.WriteLine($" {PadTime(lowest)}| {PadTime(average)}|");
        }
    }
}
