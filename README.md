# 8 Puzzle

Solve the 8-puzzle problem using the A* search algorithm.

## Goal

Rearrange the blocks so that they are in order, using as few moves as possible.  
Blocks can be slided horizontally or vertically into the blank square.

## Implementation constraints
- Fixed public API for `Board` and `Solver`
- `Board` and `Solver` should not call library functions except those in
`java.lang` and `java.util`
- Priority Queue to implement A* algorithm

## Performance requirements
- All `Board` methods should have a running time proportional to n<sup>2</sup>
in the worst case

## Enhancements
- Use only one PQ to run the A* algorithm on the initial board and its twin

## Sample client

Build a jar file:

    $ ./gradlew assemble

Client options:

    $ java -cp build/libs/8puzzle.jar PuzzleClient -h

Try to solve a solvable puzzle read from the standard input:

    $ cat data/puzzle3.txt | java -cp build/libs/8puzzle.jar PuzzleClient -
    Minimum number of moves: 3

    3
     1  2  3
     0  4  5
     7  8  6

    3
     1  2  3
     4  0  5
     7  8  6

    3
     1  2  3
     4  5  0
     7  8  6

    3
     1  2  3
     4  5  6
     7  8  0

Try to solve an unsolvable puzzle read from the standard input:

    $ cat data/puzzle3-unsolvable.txt | java -cp build/libs/8puzzle.jar PuzzleClient -
    Puzzle is unsolvable

Try to solve a randomly generated puzzle of size 4x4:

    $ java -cp build/libs/8puzzle.jar PuzzleClient -s 4
    Minimum number of moves: 45

    4
     7 14 11  0
     2  4  1  6
    10  3 15  8
     5 12  9 13

    4
     7 14  0 11
     2  4  1  6
    10  3 15  8
     5 12  9 13

     ...

     4
     1  2  3  4
     5  6  7  8
     9 10 11 12
    13 14 15  0

Try to solve a randomly generated puzzle of size 4x4 with a graphical output
([sample animation](data/visualizer.gif?raw=true)):

    $ java -cp build/libs/8puzzle.jar PuzzleClient -g -s 4
