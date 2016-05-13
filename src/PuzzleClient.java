import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class PuzzleClient {

    @Parameter(
        names = {"--size", "-s"},
        description = "Random puzzle size (2 - 127)",
        validateWith = SizeValidator.class)
    private int size;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    private boolean help = false;

    @Parameter(
        names = {"--stdin", "-"},
        description = "Read data from stdin "
            + "(board size first, followed by block rows)")
    private boolean stdin = false;

    @Parameter(
        names = {"--gui", "-g"},
        description = "Run GUI Visualizer")
    private boolean gui = false;

    public static class SizeValidator implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            String msg = "Parameter --size should be a positive integer "
                + "between 2 and 127 (found " + value + ")";
            try {
                int n = Integer.parseInt(value);
                if (n < 2 || n > 127) {
                    throw new ParameterException(msg);
                }
            } catch (Exception e) {
                throw new ParameterException(msg);
            }
        }
    }

    public static void main(String[] args) {
        PuzzleClient client = new PuzzleClient();
        JCommander jc = new JCommander(client);
        try {
            jc.parse(args);
            client.validate();
            if (client.help || args.length == 0) {
                jc.usage();
                return;
            }
            client.run();
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
        }
    }

    private void validate() throws ParameterException {
        if (stdin && size > 0) {
            throw new ParameterException(
                "Parameters --stdin and --size are mutually exclusive");
        }
    }

    private void run() {
        final Board initial;
        if (stdin) {
            initial = new Board(loadBoardFromStdIn());
        } else {
            initial = new Board(loadBoardFromRandom());
        }

        if (gui) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new Visualizer(initial);
                }
            });
        } else {
            Solver solver = new Solver(initial);
            if (!solver.isSolvable()) {
                System.out.println("Puzzle is unsolvable");
                System.out.println(initial + "\n");
            } else {
                System.out.println("Minimum number of moves: "
                    + solver.moves() + "\n");
                for (Board board : solver.solution()) {
                    System.out.println(board);
                }
            }
        }

    }

    private int[][] loadBoardFromStdIn() {
        int[][] blocks;
        try (Scanner scanner = new Scanner(System.in)) {
            int dim = scanner.nextInt();
            blocks = new int[dim][dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    blocks[i][j] = scanner.nextInt();
                }
            }
        }

        return blocks;
    }

    private int[][] loadBoardFromRandom() {
        int[][] blocks = new int[size][size];
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < size*size-1; i++) {
            items.add(i+1);
        }
        items.add(0);

        Collections.shuffle(items);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                blocks[i][j] = items.get(i * size + j);
            }
        }

        return blocks;
    }

}
