(ns bankocr.core)

(def one
  ["   "
   "  |"
   "  |"])

(def two
  [" _ "
   " _|"
   "|_ "])

(def three
  [" _ "
   " _|"
   " _|"])

(def four
  ["   "
   "|_|"
   "  |"])

(def five
  [" _ "
   "|_ "
   " _|"])

(def six
  [" _ "
   "|_ "
   "|_|"])

(def seven
  [" _ "
   "  |"
   "  |"])

(def eight
  [" _ "
   "|_|"
   "|_|"])

(def nine
  [" _ "
   "|_|"
   " _|"])

(def zero
  [" _ "
   "| |"
   "|_|"])

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
