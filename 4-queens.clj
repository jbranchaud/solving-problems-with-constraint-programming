; 4 Queens with Loco

(use 'loco.core)
(use 'loco.constraints)

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
(defn create-relation
  [variables value-pairs]
  (let [[x y] variables]
    ($or
      (map
        (fn [[x-value y-value]] ($and
                                  ($= x x-value)
                                  ($= y y-value)))
        value-pairs))))

(def relations
  [
    (create-relation [:x1 :x2] [[1 3] [1 4] [2 4] [3 1] [4 1] [4 2]])
    (create-relation [:x1 :x3] [[1 2] [1 4] [2 1] [2 3] [3 2] [3 4] [4 1] [4 3]])
    (create-relation [:x1 :x4] [[1 2] [1 3] [2 1] [2 3] [2 4] [3 1] [3 2] [3 4] [4 2] [4 3]])
    (create-relation [:x2 :x3] [[1 3] [1 4] [2 4] [3 1] [4 1] [4 2]])
    (create-relation [:x2 :x4] [[1 2] [1 4] [2 1] [2 3] [3 2] [3 4] [4 1] [4 3]])
    (create-relation [:x3 :x4] [[1 3] [1 4] [2 4] [3 1] [4 1] [4 2]])
  ])


; Associate variables with domains
(def variable-domains (vec (map #($in % domains) variables)))

; Define the model for solving
(def model (flatten [variable-domains relations]))
