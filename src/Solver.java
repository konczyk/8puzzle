import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    private final MinPQ<SearchNode> searchQueue = new MinPQ<>();
    private final Board initialBoard;
    private final Board twinBoard;

    private SearchNode solutionNode;

    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("initial is null");
        }

        this.initialBoard = initial;
        this.twinBoard = initial.twin();
        search();
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int moves;

        private SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }

        private Board prevBoard() {
            if (prev == null) {
                return null;
            } else {
                return prev.board;
            }
        }

        private int priority() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode otherNode) {
            if (this == otherNode) {
                return 0;
            }

            if (otherNode == null) {
                return 1;
            }

            int thisPriority = priority();
            int otherPriority = otherNode.priority();

            if (thisPriority != otherPriority) {
                return thisPriority - otherPriority;
            } else {
                return board.hamming() - otherNode.board.hamming();
            }
        }
    }

    private void search() {

        searchQueue.add(new SearchNode(initialBoard, null, 0));
        searchQueue.add(new SearchNode(twinBoard, null, 0));

        while (solutionNode == null) {
            SearchNode min = searchQueue.poll();
            if (min.board.isGoal()) {
                solutionNode = min;
                break;
            }
            for (Board board: min.board.neighbors()) {
                if (!board.equals(min.prevBoard())) {
                    searchQueue.add(new SearchNode(board, min, min.moves+1));
                }
            }
        }
    }

    public boolean isSolvable() {
        return solution() != null;
    }

    public int moves() {
        if (isSolvable()) {
            return solutionNode.moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        List<Board> boards = new ArrayList<>();
        SearchNode ptr = solutionNode;
        while (ptr != null) {
            boards.add(ptr.board);
            ptr = ptr.prev;
        }
        Collections.reverse(boards);
        if (boards.get(0).equals(twinBoard)) {
            return null;
        } else {
            return boards;
        }
    }

}
