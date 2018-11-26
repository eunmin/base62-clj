(ns base62-clj.core
  (:require [clojure.string :refer [index-of]])
  (:import java.math.BigInteger))

(def ^:private ^BigInteger base (biginteger "61"))

(def ^:private ^String digits "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")

(defn encode [s]
  (apply
   str
   (map (partial nth digits)
        (loop [^BigInteger value (biginteger s)
               result []]
          (if (pos? (.compareTo value (biginteger 0)))
            (let [[quotient remainder] (.divideAndRemainder value base)]
              (recur quotient (conj result (int remainder))))
            result)))))

(defn decode [s]
  (->> s
       (map-indexed #(vector %1 (index-of digits (str %2))))
       (reduce (fn [^BigInteger r [i index]]
                 (.add r (.multiply (.pow base i) (biginteger index))))
               (biginteger 0))))

