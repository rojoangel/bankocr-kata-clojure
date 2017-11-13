(ns bankocr.core-test
  (:require [clojure.test :refer :all]
            [bankocr.core :refer :all]))

(def one ["   "
          "  |"
          "  |"])

(def two [" _ "
          " _|"
          "|_ "])

(def three [" _ "
            " _|"
            " _|"])

(def four ["   "
           "|_|"
           "  |"
           ])

(def five [" _ "
           "|_ "
           " _|"
           ])

(def six [" _ "
          "|_ "
          "|_|"
          ])

(def seven [" _ "
            "  |"
            "  |"
            ])

(def eight [" _ "
            "|_|"
            "|_|"
            ])

(def nine [" _ "
           "|_|"
           " _|"
           ])

(def zero [" _ "
           "| |"
           "|_|"
           ])

(def lcd-digits
  {one   \1
   two   \2
   three \3
   four  \4
   five  \5
   six   \6
   seven \7
   eight \8
   nine  \9
   zero  \0})

(defn parse-number [number]
  (get lcd-digits number \?))

(deftest individual-numbers-reading
  (testing "outputs ? when digit is not recognized"
    (is (= \? (parse-number ["  |"
                             "  |"
                             "  |"]))))
  (testing "one is recognized"
    (is (= \1 (parse-number ["   "
                             "  |"
                             "  |"]))))
  (testing "two is recognized"
    (is (= \2 (parse-number [" _ "
                             " _|"
                             "|_ "]))))
  (testing "three is recognized"
    (is (= \3 (parse-number [" _ "
                             " _|"
                             " _|"]))))
  (testing "four is recognized"
    (is (= \4 (parse-number ["   "
                             "|_|"
                             "  |"]))))
  (testing "five is recognized"
    (is (= \5 (parse-number [" _ "
                             "|_ "
                             " _|"]))))
  (testing "six is recognized"
    (is (= \6 (parse-number [" _ "
                             "|_ "
                             "|_|"]))))
  (testing "seven is recognized"
    (is (= \7 (parse-number [" _ "
                             "  |"
                             "  |"]))))
  (testing "eight is recognized"
    (is (= \8 (parse-number [" _ "
                             "|_|"
                             "|_|"]))))
  (testing "nine is recognized"
    (is (= \9 (parse-number [" _ "
                             "|_|"
                             " _|"]))))
  (testing "zero is recognized"
    (is (= \0 (parse-number [" _ "
                            "| |"
                            "|_|"])))))

(defn- head-digit [scanned-account]
  (conj []
        (apply str (take 3 (first scanned-account)))
        (apply str (take 3 (second scanned-account)))
        (apply str (take 3 (last scanned-account)))))

(defn- tail-digits [scanned-account]
  (conj []
        (drop 3 (first scanned-account))
        (drop 3 (second scanned-account))
        (drop 3 (last scanned-account))))

(defn parse-account [scanned-account]
  (loop [account-digits [] scanned-account scanned-account]
    (if (every? empty? scanned-account)
      (apply str account-digits)
      (recur (conj account-digits (parse-number (head-digit scanned-account)))
             (tail-digits scanned-account)))))

(deftest account-number-reading
  (testing "reads an account number"
    (is (= "123456789" (parse-account ["    _  _     _  _  _  _  _ "
                                       "  | _| _||_||_ |_   ||_||_|"
                                       "  ||_  _|  | _||_|  ||_| _|"])))))

