import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Board data type
 */
public class Board {

    private final char[] board;
    private final int dimension;

    private int manhattanCache = -1;
    private int hammingCache = -1;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException("blocks are null");
        }

        if (blocks.length < 2 || blocks.length > 128) {
            throw new IllegalArgumentException(
                "blocks dimension should be between 2 and 128 inclusive");
        }

        for (int[] row: blocks) {
            if (row.length != blocks.length) {
                throw new IllegalArgumentException(
                    "blocks must have the same number of rows and columns");
            }
        }

        dimension = blocks.length;
        board = fillBlocks(blocks);

        manhattanCache = manhattan();
        hammingCache = hamming();
    }

    private Board(char[] blocks, int swapSrc, int swapDst) {
        dimension = (int) Math.sqrt(blocks.length + 1);
        board = blocks.clone();

        board[swapSrc] = blocks[swapDst];
        board[swapDst] = blocks[swapSrc];
    }

    private char[] fillBlocks(int[][] blocks2D) {
        char[] blocksSeq = new char[dimension() * dimension()];
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                int idx = i * dimension() + j;
                blocksSeq[idx] = (char) blocks2D[i][j];
            }
        }

        return blocksSeq;
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        if (hammingCache < 0) {
            hammingCache = 0;
            for (int i = 0; i < board.length; i++) {
                if (!pieceInPlace(i)) {
                    hammingCache++;
                }
            }
        }

        return hammingCache;
    }

    public int manhattan() {
        if (manhattanCache < 0) {
            manhattanCache = 0;
            for (int i = 0; i < board.length; i++) {
                if (!pieceInPlace(i)) {
                    manhattanCache += calculateDistance(i);
                }
            }
        }

        return manhattanCache;
    }

    private int calculateDistance(int idx) {
        int currentRow = idx / dimension();
        int currentCol = idx - currentRow * dimension();
        int finalRow = (board[idx] - 1) / dimension();
        int finalCol = (board[idx] - 1) - finalRow * dimension();

        return Math.abs(finalRow - currentRow) + Math.abs(finalCol - currentCol);
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    private boolean pieceInPlace(int idx) {
        return board[idx] == 0 || board[idx] == idx + 1;
    }

    public Board twin() {
        if (board[0] != 0 && board[1] != 0) {
            return new Board(this.board, 0, 1);
        } else {
            return new Board(this.board, dimension(), dimension() + 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return Arrays.equals(board, ((Board) o).board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>();

        int blank = findBlank();

        Board top = getTopNeighbor(blank);
        if (top != null) {
            neighbors.add(top);
        }
        Board bottom = getBottomNeighbor(blank);
        if (bottom != null) {
            neighbors.add(bottom);
        }
        Board left = getLeftNeighbor(blank);
        if (left != null) {
            neighbors.add(left);
        }
        Board right = getRightNeighbor(blank);
        if (right != null) {
            neighbors.add(right);
        }

        return neighbors;
    }

    private int findBlank() {
        int blank = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                blank = i;
                break;
            }
        }

        return blank;
    }

    private Board getTopNeighbor(int blank) {
        if (blank < dimension()) {
            return null;
        } else {
            return new Board(this.board, blank - dimension(), blank);
        }
    }

    private Board getBottomNeighbor(int blank) {
        if (blank / dimension() == dimension() - 1) {
            return null;
        } else {
            return new Board(this.board, blank + dimension(), blank);
        }
    }

    private Board getLeftNeighbor(int blank) {
        if (blank % dimension() == 0) {
            return null;
        } else {
            return new Board(this.board, blank - 1, blank);
        }
    }

    private Board getRightNeighbor(int blank) {
        if (blank % dimension() == dimension() - 1) {
            return null;
        } else {
            return new Board(this.board, blank + 1, blank);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        sb.append("\n");
        for (int i = 0; i < board.length; i++) {
            sb.append(String.format("%2d ", (int) board[i]));
            if ((i + 1) % dimension() == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

}
