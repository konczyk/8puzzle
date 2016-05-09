# 8 Puzzle

Solve the 8-puzzle problem using the A* search algorithm.

## Examples

Build project:

    $ ./gradlew assemble

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
