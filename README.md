# Solving Puzzles with Constraint Programming

To work with any of these puzzle solvers, you'll want to boot up a lein
repl:

```bash
$ lein repl
```

## Sudoku

Load the formulation of the sudoku puzzle

```clojure
(load-file "./sudoku.clj")
```

Get a solution with

```clojure
(solution model)
```

## 4 Queens

Load the formulation of the 4 Queens puzzle

```clojure
(load-file "./4-queens.clj")
```

No solution at the moment.
