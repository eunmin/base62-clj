(ns base62-clj.core-test
  (:require [clojure.test :refer :all]
            [base62-clj.core :refer :all]))

(deftest encode-decode-test
  (let [value 1234567890]
    (is (= value (decode (encode value))))))
