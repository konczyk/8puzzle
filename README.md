# 8 Puzzle

Solve the 8-puzzle problem using the A* search algorithm.

## Examples

Build project:

    $ ./gradlew assemble

Try to solve a solvable puzzle read for a standard input:

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

Try to solve an unsolvable puzzle read for a standard input:

    $ cat data/puzzle3-unsolvable.txt | java -cp build/libs/8puzzle.jar PuzzleClient -
    Puzzle is unsolvable
