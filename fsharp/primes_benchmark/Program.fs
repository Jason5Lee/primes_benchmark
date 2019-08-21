open System
open System.Diagnostics

let length = 10000
let expectSum = 496165411
let cnt = 12

let getTimeMs block =
    let stopwatch = Stopwatch()
    stopwatch.Start()
    block()
    stopwatch.Stop()
    stopwatch.Elapsed.Milliseconds

let sumPrimesLong n =
    let mutable primes = ResizeArray()
    let mutable current = 2UL
    let mutable sum = 0UL

    while primes.Count < n do
        // if (primes |> Seq.forall (fun p -> current % p <> 0UL))
        if (primes.TrueForAll (fun p -> current % p <> 0UL))
        then 
            sum <- sum + current
            primes.Add(current)

        current <- current + 1UL

    sum

let sumPrimesInt n =
    let mutable primes = ResizeArray()
    let mutable current = 2
    let mutable sum = 0

    while primes.Count < n do
        // if (primes |> Seq.forall (fun p -> current % p <> 0))
        if (primes.TrueForAll (fun p -> current % p <> 0))
        then 
            sum <- sum + current
            primes.Add(current)

        current <- current + 1

    sum

let assertLong() =
    if sumPrimesLong length <> (uint64 expectSum) then
        raise (Exception "Wrong Answer")

let assertInt() =
    if sumPrimesInt length <> expectSum then
        raise (Exception "Wrong Answer")

let padTimeMs ms =
    ms.ToString() + "ms"
    |> (fun s -> s.PadRight(13))

[<EntryPoint>]
let main argv =
    if argv.Length <> 1 then
        raise (ArgumentException "Expect 1 argument.")

    let body = 
        if argv.[0] = "--uint64"
        then assertLong
        else if argv.[0] = "--uint32"
        then assertInt
        else raise (ArgumentException "Unknown argument {args[0]}")
    
    let sortedTimesMs = List.init cnt (fun _ -> getTimeMs body)
                        |> List.sort
    let lowest = List.last sortedTimesMs
    let average =
        List.skip 1 sortedTimesMs
        |> List.take (cnt - 2)
        |> List.sum
        |> (fun sum -> (double sum) / (double (cnt - 2)))
    printfn " %s| %s|" (padTimeMs lowest) (padTimeMs average)
    0 // return an integer exit code
