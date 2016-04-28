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

        assertThat(0, is(board.hamming()));
    }

    @Test
    public void dimension() {
        Board board = new Board(new int[3][3]);

        assertThat(3, is(board.dimension()));
    }

    @Test
    public void hammingWithBlocksInWrongPosition() {
        int[][] blocks = new int[][]{
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(5, is(board.hamming()));
    }

    @Test
    public void hammingWithOrderedBlocks() {
        int[][] blocks = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(0, is(board.hamming()));
    }

    @Test
    public void manhattanWithBlocksInWrongPosition() {
        int[][] blocks = new int[][]{
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(10, is(board.manhattan()));
    }

    @Test
    public void manhattanWithOrderedBlocks() {
        int[][] blocks = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(0, is(board.manhattan()));
    }

    @Test
    public void isGoalWithBlocksInWrongPosition() {
        int[][] blocks = new int[][]{
            new int[]{8, 1, 3},
            new int[]{4, 0, 2},
            new int[]{7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(false, is(board.isGoal()));
    }

    @Test
    public void isGoalWithOrderedBlocks() {
        int[][] blocks = new int[][]{
            new int[]{1, 2, 3},
            new int[]{4, 5, 6},
            new int[]{7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(true, is(board.isGoal()));
    }

}
