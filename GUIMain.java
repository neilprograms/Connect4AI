
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *  Runs a GUI version of Connect-4
 *
 *  @author Neil Kakhandiki
 *  @version May 19, 2021
 */
public class GUIMain implements MouseListener, ActionListener
{
    public static final int DIAMETER = 55;
    public static final int WIDTH = DIAMETER * 14;
    public static final int HEIGHT = DIAMETER * 13;

    private JFrame frame;
    private BitBoard board;
    private BoardSolver solver;
    private int numMoves;
    private JButton reset;
    private JButton pvp; // player versus player
    private boolean pvpBool;

    /**
     * Create a new GUIMain object.
     */
    public GUIMain()
    {
        pvpBool = true;
        numMoves = 0;
        solver = new BoardSolver();
        board = new BitBoard();
        reset = new JButton("Reset");
        pvp = new JButton("Human vs. Human");
        frame = new JFrame("Connect 4");
        frame.addMouseListener(this);

        frame.add(reset);
        reset.setBounds(WIDTH / 2 - DIAMETER * 7 / 2, HEIGHT - DIAMETER, DIAMETER  * 3, DIAMETER);
        reset.addActionListener(this);

        frame.add(pvp);
        pvp.setBounds(WIDTH / 2 + DIAMETER / 2, HEIGHT - DIAMETER, DIAMETER  * 3, DIAMETER);
        pvp.addActionListener(this);

        frame.add(board);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT + DIAMETER);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Returns the board value
     * @return the value of the board
     */
    public BitBoard getBoard()
    {
        return board;
    }

    /**
     *
     */
    public void mouseClicked(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    /**
     *
     */
    public void mousePressed(MouseEvent e)
    {
        double x = e.getX();
        double y = e.getY();

        int initialY = DIAMETER;
        double initialX = DIAMETER * 7 / 4;
        double endX = initialX + DIAMETER * 3 / 2;
        double temp = 0.0;

        int winningTurn = 0;

        if (y > initialY && y < initialY + DIAMETER * 21 / 2)
        {
            for (int col = 0; col < 7; col++)
            {
                if (x > initialX && x < endX)
                {
                    winningTurn = board.getTurn() - 1;
                    board.makeMove(col);
                    numMoves++;
                    frame.repaint();
                    if (board.isWin(board.getBoard(winningTurn)))
                    {
                        JOptionPane.showMessageDialog(frame, "Player "+ (winningTurn + 1) + " Wins");
                        frame.removeMouseListener(this);
                        break;
                    }
                    else if (board.isFull())
                    {
                        JOptionPane.showMessageDialog(frame, "Tie Game");
                        frame.removeMouseListener(this);
                        break;
                    }
                    if (!pvpBool && numMoves >= 9)
                    {
                        winningTurn = board.getTurn() - 1;
                        board.makeMove(solver.solve(board));
                        numMoves++;
                        frame.repaint();
                        if (board.isWin(board.getBoard(winningTurn)))
                        {
                            JOptionPane.showMessageDialog(frame, "Player "+ (winningTurn + 1) + " Wins");
                            frame.removeMouseListener(this);
                            break;
                        }
                        else if (board.isFull())
                        {
                            JOptionPane.showMessageDialog(frame, "Tie Game");
                            frame.removeMouseListener(this);
                            break;
                        }
                    }
                }
                temp = endX;
                endX = temp + DIAMETER * 3 / 2;
                initialX = temp;
            }
        }
    }

    /**
     *
     */
    public void mouseReleased(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    /**
     *
     */
    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    /**
     *
     */
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub
    }

    /**
     * Creates GUIMain object to run GUI Connect 4
     * @param args main method
     */
    public static void main(String args[])
    {
        GUIMain GUI = new GUIMain();
    }

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == reset)
        {
            frame.removeMouseListener(this);
            board.reset();
            frame.repaint();
            frame.addMouseListener(this);
            numMoves = 0;
        }
        if (e.getSource() == pvp)
        {
            frame.removeMouseListener(this);
            board.reset();
            frame.repaint();
            frame.addMouseListener(this);
            numMoves = 0;
            pvpBool = !pvpBool;
            if (pvpBool)
            {
                pvp.setText("Human vs. Human");
            }
            else
            {
                pvp.setText("Computer vs. Human");
            }
        }
    }
}
