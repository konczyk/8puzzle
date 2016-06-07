import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Visualizer extends JFrame {

    private static final int MOVE_DELAY = 750;
    private static final int SOLVE_TIMEOUT = 60;

    private static final String TITLE = "8puzzle";
    private static final String SEARCH_INFO = "Searching for a solution...";
    private static final String UNSOLVABLE_INFO = "Puzzle is unsolvable";
    private static final String MOVE_INFO = "Moving puzzle %d/%d";
    private static final String CANCEL_INFO = "Searching cancelled after "
                                              + "%ds timeout expired";

    private final List<JLabel> puzzles = new ArrayList<>();
    private final JLabel statusLabel = new JLabel();

    private final int gridWidth;
    private final Board initialBoard;

    public Visualizer(Board initial) {
        gridWidth = initial.dimension();
        initialBoard = initial;

        setTitle(TITLE);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(makeGrid(), BorderLayout.CENTER);
        add(makeStatus(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        updateGrid(extractBlocks(initialBoard));
        new TimedSolverTask().execute();
    }

    private JPanel makeGrid() {
        JPanel gridPanel = new JPanel();
        gridPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        gridPanel.setPreferredSize(new Dimension(600, 600));
        gridPanel.setLayout(new GridLayout(gridWidth, gridWidth, -3, -3));

        for (int i = 0; i < gridWidth * gridWidth; i++) {
            JLabel label = new JLabel();
            label.setOpaque(true);
            label.setBackground(Color.WHITE);
            label.setForeground(Color.WHITE);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            label.setFont(label.getFont().deriveFont(600/gridWidth/3.0f));
            gridPanel.add(label);
            puzzles.add(label);
        }

        return gridPanel;
    }

    private JPanel makeStatus() {
        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setText(SEARCH_INFO);
        statusPanel.add(statusLabel);

        return statusPanel;
    }

    private class TimedSolverTask extends SwingWorker<Solver, Void> {
        @Override
        public Solver doInBackground()
            throws TimeoutException, InterruptedException, ExecutionException {

            SwingWorker<Solver, Void> worker = new SolverTask();
            worker.execute();

            try {
                return worker.get(SOLVE_TIMEOUT, TimeUnit.SECONDS);
            } catch (Exception e) {
                worker.cancel(true);
                throw e;
            }
        }
    }

    private class SolverTask extends SwingWorker<Solver, Void> {
        @Override
        public Solver doInBackground() {
            return new Solver(initialBoard);
        }

        @Override
        protected void done() {
            try {
                draw(get());
            } catch (CancellationException e) {
                statusLabel.setText(String.format(CANCEL_INFO, SOLVE_TIMEOUT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void draw(final Solver solver) {

        if (!solver.isSolvable()) {
            statusLabel.setText(UNSOLVABLE_INFO);
        } else {
            final Iterator<Board> it = solver.solution().iterator();
            it.next();
            Timer timer = new Timer(MOVE_DELAY, new ActionListener() {
                private int move = 1;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!it.hasNext()) {
                        ((Timer) e.getSource()).stop();
                    } else {
                        Board board = it.next();
                        updateGrid(extractBlocks(board));
                        Toolkit.getDefaultToolkit().sync();
                        statusLabel.setText(
                            String.format(MOVE_INFO, move, solver.moves()));
                        move++;
                    }
                }
            });
            timer.setInitialDelay(MOVE_DELAY);
            timer.start();
        }
    }

    private void updateGrid(List<String> blocks) {
        for (int i = 0; i < puzzles.size(); i++) {
            if (!blocks.get(i).equals("0")) {
                puzzles.get(i).setBackground(Color.BLUE);
                puzzles.get(i).setText(blocks.get(i));
            } else {
                puzzles.get(i).setBackground(Color.WHITE);
                puzzles.get(i).setText("");
            }
        }
    }

    private static List<String> extractBlocks(Board board) {
        String[] arr = board.toString().split("\\s+");
        List<String> blocks = new ArrayList<>(Arrays.asList(arr));
        blocks.remove(0);

        return blocks;
    }

}
