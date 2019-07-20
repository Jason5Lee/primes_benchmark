(ns core
  (:import [java.util Date])
  (:gen-class))

(defn sum-primes [n]
  (loop
   [primes []
    current 2
    sum 0]
    (if (< (count primes) n)
      (if (every?
           (fn [p] (not= (mod current p) 0))
           primes)
        (recur (conj primes current) (inc current) (+ sum current))
        (recur primes (inc current) sum))
      sum)))

(defn get-time-ms [b]
  (let [start (.getTime (Date.))]
    (b)
    (- (.getTime (Date.)) start)))

(defn -main []
  (let 
   [sorted-time
    (->> (repeatedly 12
                     #(get-time-ms
                       (fn [] (assert (= (sum-primes 10000) 496165411)))))
         sort)
    slowest (last sorted-time)
    sum-of-10 (->> sorted-time
                   (drop 1)
                   (take 10)
                   (apply +))
    average (double (/ sum-of-10 10))]
    (printf " %-13s| %-13s|\n"
            (str slowest "ms")
            (str average "ms"))))
