; Solving Sudoku with Loco

; http://www.websudoku.com/?level=1&set_id=4643797597
; _ _ 1  7 _ _  8 3 _
; _ 3 _  4 _ _  _ _ _
; _ _ 5  8 2 _  7 1 _
;
; _ _ 2  _ _ 7  _ _ _
; 3 4 9  2 _ 6  5 7 8
; _ _ _  5 _ _  3 _ _
;
; _ 5 6  _ 8 4  9 _ _
; _ _ _  _ _ 2  _ 4 _
; _ 2 4  _ _ 5  6 _ _

(use 'loco.core)
(use 'loco.constraints)

(def board
  {:a0 nil :a1 nil :a2 1   :a3 7   :a4 nil :a5 nil :a6 8   :a7 3   :a8 nil
   :b0 nil :b1 3   :b2 nil :b3 4   :b4 nil :b5 nil :b6 nil :b7 nil :b8 nil
   :c0 nil :c1 nil :c2 5   :c3 8   :c4 2   :c5 nil :c6 7   :c7 1   :c8 nil
   :d0 nil :d1 nil :d2 2   :d3 nil :d4 nil :d5 7   :d6 nil :d7 nil :d8 nil
   :e0 3   :e1 4   :e2 9   :e3 2   :e4 nil :e5 6   :e6 5   :e7 7   :e8 8
   :f0 nil :f1 nil :f2 nil :f3 5   :f4 nil :f5 nil :f6 3   :f7 nil :f8 nil
   :g0 nil :g1 5   :g2 6   :g3 nil :g4 8   :g5 4   :g6 9   :g7 nil :g8 nil
   :h0 nil :h1 nil :h2 nil :h3 nil :h4 nil :h5 2   :h6 nil :h7 4   :h8 nil
   :i0 nil :i1 2   :i2 4   :i3 nil :i4 nil :i5 5   :i6 6   :i7 nil :i8 nil})

(def letters '("a" "b" "c" "d" "e" "f" "g" "h" "i"))
(def numbers (range 0 9))

(defn some_positions
  [set-x set-y]
  (for [x set-x y set-y]
    (keyword (str x y))))

(def all_positions
  (some_positions letters numbers))

(defn pair-positions
  [positions]
  (filter
    (fn [[x y]] (not (= x y)))
    (for [x positions
          y positions]
      (vector x y))))

(defn position_pairs
  [set-x set-y]
  (pair-positions (some_positions set-x set-y)))

(def all_position_pairs (position_pairs letters numbers))
(def box-positions
  (for [x (partition 3 letters)
        y (partition 3 numbers)]
    (for [m x
          n y]
      (keyword (str m n)))))

(def all_row_position_pairs (mapcat (fn [x] (position_pairs [x] numbers)) letters))
(def all_column_position_pairs (mapcat (fn [x] (position_pairs letters [x])) numbers))
(def all_box_position_pairs (mapcat (fn [x] (pair-positions x)) box-positions))

(def row_constraints
  (map
    (fn [[x y]] ($!= x y))
    all_row_position_pairs))

(def column_constraints
  (map
    (fn [[x y]] ($!= x y))
    all_column_position_pairs))

(def box-constraints
  (map
    (fn [[x y]] ($!= x y))
    all_box_position_pairs))

(defn bound-all-positions
  [board]
  (map
    (fn [[position value]]
      (if (nil? value)
        ($in position 1 9)
        ($in position [value])))
    board))

(def model
  (reduce
    concat
    []
    [(bound-all-positions board)
     row_constraints
     column_constraints
     box-constraints]))

(def expected-result
  {:a0 2, :a1 6, :a2 1, :a3 7, :a4 5, :a5 9, :a6 8, :a7 3, :a8 4, :b0 7, :b1 3, :b2 8, :b3 4, :b4 6, :b5 1, :b6 2, :b7 5, :b8 9, :c0 4, :c1 9, :c2 5, :c3 8, :c4 2, :c5 3, :c6 7, :c7 1, :c8 6, :d0 5, :d1 8, :d2 2, :d3 9, :d4 3, :d5 7, :d6 4, :d7 6, :d8 1, :e0 3, :e1 4, :e2 9, :e3 2, :e4 1, :e5 6, :e6 5, :e7 7, :e8 8, :f0 6, :f1 1, :f2 7, :f3 5, :f4 4, :f5 8, :f6 3, :f7 9, :f8 2, :g0 1, :g1 5, :g2 6, :g3 3, :g4 8, :g5 4, :g6 9, :g7 2, :g8 7, :h0 8, :h1 7, :h2 3, :h3 6, :h4 9, :h5 2, :h6 1, :h7 4, :h8 5, :i0 9, :i1 2, :i2 4, :i3 1, :i4 7, :i5 5, :i6 6, :i7 8, :i8 3})

(defn print-board
  [board]
  (doseq [row (partition 9 (into (sorted-map) board))]
    (prn (clojure.string/join " " (map (fn [x] (or x " ")) (vals row))))))

(assert (= (into (sorted-map) (solutions model)) expected-result))
