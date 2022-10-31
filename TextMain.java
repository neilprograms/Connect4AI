
import java.util.Scanner;

/**
 *  Runs a text version of Connect-4
 *
 *  @author Neil Kakhandiki
 *  @version May 12, 2021
 */
public class TextMain
{
    /**
     * Runs a text version of Connect-4
     * @param args main method
     */
    public static void main(String args[])
    {
        BitBoard board = new BitBoard();
        BoardSolver solver = new BoardSolver();
        System.out.println("Connect 4!");
        Scanner scan = new Scanner(System.in);
        int col = -1;
        boolean gameOver = false;
        int counter = 0;

        while (!gameOver)
        {
            System.out.println("Where would you like to go Player " + board.getTurn() + "?");
            System.out.println(board);
            col = scan.nextInt(); // assumes valid column from 1 - 7, and the column is not full
            int winningTurn = board.getTurn() - 1;
            board.makeMove(col - 1);
            counter++;
            System.out.println(board);
            if (board.isWin(board.getBoard(winningTurn)))
            {
                System.out.println(board);
                System.out.println("Player " + (winningTurn + 1) + " wins!");
                gameOver = true;
                scan.close();
            }
            else if (board.isFull())
            {
                System.out.println(board);
                System.out.println("Tie Game!");
                gameOver = true;
                scan.close();
            }
            if (counter >= 11) // what move the AI will start playing at
            {
                winningTurn = board.getTurn() - 1;
                board.makeMove(solver.solve(board));
                if (board.isWin(board.getBoard(winningTurn)))
                {
                    System.out.println(board);
                    System.out.println("Player " + (winningTurn + 1) + " wins!");
                    gameOver = true;
                    scan.close();
                }
                else if (board.isFull())
                {
                    System.out.println(board);
                    System.out.println("Tie Game!");
                    gameOver = true;
                    scan.close();
                }
            }
        }
    }
}
