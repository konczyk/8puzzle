import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class BoardTest {

    @Test(expected = NullPointerException.class)
    public void throwsExceptionOnNullBlocks() {
        new Board(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionOnTooSmallBlocksArray() {
        new Board(new int[1][1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionOnTooBigBlocksArray() {
        new Board(new int[129][129]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionOnInvalidBlocksStructure() {
        new Board(new int[10][5]);
    }
    
    @Test
    public void doesNotExposeInternalRepresentation() {
        int[][] blocks = new int[][]{
            new int[]{1, 2},
            new int[]{3, 0}};
        Board board = new Board(blocks);

        blocks[0][0] = 3;
        blocks[1][0] = 1;

        assertThat(board.hamming(), is(0));
    }

    @Test
    public void dimension() {
        Board board = new Board(new int[3][3]);

        assertThat(board.dimension(), is(3));
    }

    @Test
    public void hammingWithBlocksInWrongPosition() {
        int[][] blocks = new int[][]{
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(board.hamming(), is(5));
    }

    @Test
    public void hammingWithOrderedBlocks() {
        int[][] blocks = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(board.hamming(), is(0));
    }

    @Test
    public void manhattanWithBlocksInWrongPosition() {
        int[][] blocks = new int[][]{
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(board.manhattan(), is(10));
    }

    @Test
    public void manhattanWithOrderedBlocks() {
        int[][] blocks = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(board.manhattan(), is(0));
    }

    @Test
    public void isGoalWithBlocksInWrongPosition() {
        int[][] blocks = new int[][]{
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(board.isGoal(), is(false));
    }

    @Test
    public void isGoalWithOrderedBlocks() {
        int[][] blocks = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(board.isGoal(), is(true));
    }

    @Test
    public void equals() {
        int[][] blocks1 = new int[][]{
            new int[]{1, 2},
            new int[]{3, 0}};
        Board board1 = new Board(blocks1);

        int[][] blocks2 = new int[][]{
            new int[]{1, 2},
            new int[]{3, 0}};
        Board board2 = new Board(blocks2);

        assertThat(board1.equals(board1), is(true));
        assertThat(board1.equals(board2), is(true));
        assertThat(board2.equals(board1), is(true));
    }

    @Test
    public void notEquals() {
        int[][] blocks1 = new int[][]{
            new int[]{1, 2},
            new int[]{3, 0}};
        Board board1 = new Board(blocks1);

        int[][] blocks2 = new int[][]{
            new int[]{1, 0},
            new int[]{3, 2}};
        Board board2 = new Board(blocks2);

        assertThat(board1.equals(null), is(false));
        assertThat(board1.equals(board2), is(false));
        assertThat(board2.equals(board1), is(false));
    }

    @Test
    public void boardAsString() {
        int[][] blocks = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(
            board.toString(),
            is("3\n 1  2  3 \n 4  5  6 \n 7  8  0 \n"));
    }

}
