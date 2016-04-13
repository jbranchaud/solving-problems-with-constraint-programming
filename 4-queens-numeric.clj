; 4 Queens (Numeric) with Loco

(use 'loco.core)
(use 'loco.constraints)
(use '[clojure.math.combinatorics :as combo])

;;;
;;; Variables
;;;
(def variables [:x1 :x2 :x3 :x4])


;;;
;;; Domains
;;;
(def domains [1 2 3 4])


;;;
;;; Constraints
;;;
(defn kwify [x] (keyword (str "x" x)))
(def variable-pairings (filter (fn [[a b]] (not (= a b))) (combo/selections domains 2)))

(def constraints
  (map
    (fn [[x y]]
      (let [x-var (kwify x)
            y-var (kwify y)]
        ($and ($!= x-var y-var)
              ($!= ($abs ($- x-var y-var)) ($abs (- x y))))))
    variable-pairings))


; Associate variables with domains
(def variable-domains (vec (map #($in % domains) variables)))

; Define the model for solving
(def model (flatten [variable-domains constraints]))
