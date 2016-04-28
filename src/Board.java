import java.util.Arrays;

/**
 * Board data type
 */
public class Board {

    private final char[] board;
    private final int dimension;

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
        int displaced = 0;
        for (int i = 0; i < board.length; i++) {
            if (!pieceInPlace(i)) {
                displaced++;
            }
        }

        return displaced;
    }

    public int manhattan() {
        int distances = 0;
        for (int i = 0; i < board.length; i++) {
            if (!pieceInPlace(i)) {
                distances += calculateDistance(i);
            }
        }

        return distances;
    }

    private int calculateDistance(int idx) {
        int currentRow = idx / dimension();
        int currentCol = idx - currentRow * dimension();
        int finalRow = (board[idx] - 1) / dimension();
        int finalCol = (board[idx]  - 1) - finalRow * dimension();

        return Math.abs(finalRow - currentRow) + Math.abs(finalCol - currentCol);
    }

    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            if (!pieceInPlace(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean pieceInPlace(int idx) {
        return board[idx] == 0 || board[idx] == idx + 1;
    }

    public Board twin() {
        return null;
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
        return null;
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
