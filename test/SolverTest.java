import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SolverTest {

    private final int[][] solvableBlocks = new int[][]{
        new int[]{0, 1},
        new int[]{3, 2}};

    private final int[][] unsolvableBlocks = new int[][]{
        new int[]{0, 1},
        new int[]{2, 3}};

    @Test(expected = NullPointerException.class)
    public void throwExceptionOnNullInitialBoard() {
        new Solver(null);
    }

    @Test
    public void isSolvable() {
        Solver solver = new Solver(new Board(solvableBlocks));

        assertThat(solver.isSolvable(), is(true));
    }

    @Test
    public void movesForSolvableBoard() {
        Solver solver = new Solver(new Board(solvableBlocks));

        assertThat(solver.moves(), is(2));
    }

    @Test
    public void solutionForSolvableBoard() {
        Solver solver = new Solver(new Board(solvableBlocks));

        int[][] move1 = new int[][]{
            new int[]{1, 0},
            new int[]{3, 2}};

        int[][] move2 = new int[][]{
            new int[]{1, 2},
            new int[]{3, 0}};

        Iterator<Board> it = solver.solution().iterator();
        assertThat(it.next(), equalTo(new Board(solvableBlocks)));
        assertThat(it.next(), equalTo(new Board(move1)));
        assertThat(it.next(), equalTo(new Board(move2)));
    }

    @Test
    public void isUnsolvable() {
        Solver solver = new Solver(new Board(unsolvableBlocks));

        assertThat(solver.isSolvable(), is(false));
    }

    @Test
    public void movesForUnsolvableBoard() {
        Solver solver = new Solver(new Board(unsolvableBlocks));

        assertThat(solver.moves(), is(-1));
    }

    @Test
    public void solutionForUnsolvableBoard() {
        Solver solver = new Solver(new Board(unsolvableBlocks));

        assertThat(solver.solution(), is(nullValue()));
    }

}
