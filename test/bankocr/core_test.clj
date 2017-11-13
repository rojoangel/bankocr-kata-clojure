(ns bankocr.core-test
  (:require [clojure.test :refer :all]
            [bankocr.core :refer :all]))

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

(deftest account-number-reading
  (testing "reads an account number"
    (is (= "123456789" (parse-account ["    _  _     _  _  _  _  _ "
                                       "  | _| _||_||_ |_   ||_||_|"
                                       "  ||_  _|  | _||_|  ||_| _|"])))))
