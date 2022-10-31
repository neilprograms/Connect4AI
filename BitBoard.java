
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Represents a Connect-4 board as a long in binary using bitwise operations to be more efficient
 *
 *  @author Neil Kakhandiki
 *  @version May 9, 2021
 */
public class BitBoard extends JPanel
{
    public static final int DIAMETER = 55;
    public static final int WIDTH = DIAMETER * 14;
    public static final int HEIGHT = DIAMETER * 13;
    public static final Color BLUE = new Color(54, 101, 255);
    public static final Color RED = new Color(232, 67, 67);
    public static final Color YELLOW = new Color(255, 218, 54);

    public static final int ROWS = 6;
    public static final int COLS = 7;

    private long[] xoboard;
    private int[] height;
    private int counter;
    private int[] moves;

    /**
     * Creates a new BitBoard object with the following indices for a 64 bit long
     *
     *  6 13  20 27 34 41 48   55 62
     * +---------------------+
     * | 5 12 19 26 33 40 47 | 54 61
     * | 4 11 18 25 32 39 46 | 53 60
     * | 3 10 17 24 31 38 45 | 52 59
     * | 2  9  16 23 30 37 44 | 51 58
     * | 1  8  15 22 29 36 43 | 50 57 64
     * | 0  7  14 21 28 35 42 | 49 56 63
     * +---------------------+
     *
     * xoboard holds two long numbers which contain the position of all the x's and o's respectively
     * height holds the index of each column where a piece can be played {0, 7, 14, 21, 28, 35, 42} initially
     * counter is the curernt player
     * moves holds the current moves as column in order
     */
    public BitBoard()
    {
        xoboard = new long[2];
        height = new int[COLS];
        for (int col = 0; col < COLS; col++)
        {
            height[col] = col * COLS;
        }
        counter = 0;
        moves = new int[42];
    }

    /**
     * Resets the bitboard to a blank board
     */
    public void reset()
    {
        xoboard[0] = 0B0L;
        xoboard[1] = 0B0L;
        for (int col = 0; col < COLS; col++)
        {
            height[col] = col * COLS;
        }
        counter = 0;
        for (int i = 0; i < moves.length; i++)
        {
            moves[i] = 0;
        }
    }

    /**
     * Gets the "x" or "o" board
     * @param x - 0 to get the "x" board or 1 to get "o" board
     * @return the long representing the x's or o's from xoboard
     */
    public long getBoard(int x)
    {
        return xoboard[x];
    }

    /**
     * Gets the turn from the counter
     * @return the turn of player
     */
    public int getTurn()
    {
        if (counter % 2 == 0)
        {
            return 1;
        }
        return 2;
    }

    /**
     * Gets the counter which is the number of moves played
     * @return counter which counts the player's turn
     */
    public int getCounter()
    {
        return counter;
    }

    /**
     * Returns a boolean value if a column is playable
     * @param col to check if someone can move
     * @return boolean if a column is playable
     */
    public boolean canMove(int col)
    {
        return (height[col] < col * COLS + ROWS);
    }

    /**
     * Adds the current board + a mask of the board using XOR
     * @return key for a hashtable with the board position
     */
    public long getKey()
    {
        return getBoard(counter & 1) + (getBoard(0) ^ getBoard(1));
    }

    /**
     * Makes a move in the specific column and turn
     * @param col to drop a piece inf
     */
    public void makeMove(int col)
    {
        long move = 0B1L << height[col]; // shifts 1 bit by the height[col] to place a piece
        xoboard[counter & 1] ^= move; // places the piece using XOR on the correct bitboard
        // adds one to the height to update the empty space
        height[col]++;
        // adds the col to the list of moves
        moves[counter] = col;
        // updates the counter and turn
        counter++;
    }

    /**
     * Undoes the previous move
     */
    public void undoMove()
    {
        // undoes each step in makeMove()
        counter--;
        int col = moves[counter];
        height[col]--;
        long move = 0B1L << height[col];
        xoboard[counter & 1] ^= move;
    }

    /**
     * Checks if there is a four in a row on an "x" or "o" bitboard
     * @param board - to check if there is a win
     * @return boolean if a player has won
     */
    public boolean isWin(long board)
    {
        // these directions represent the steps to be diagonal, horizontal, and vertical
        int[] directions = {1, ROWS, ROWS + 1, ROWS + 2};
        long tempBoard;
        for (int direction : directions)
        {
            // shifts the board and overlays the board using &
            tempBoard = board & (board >> direction);
            if ((tempBoard & (tempBoard >> 2 * direction)) != 0)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the board is full if the number of moves is greater than the board area
     * @return boolean if the board is full
     */
    public boolean isFull()
    {
        return (counter >= ROWS * COLS);
    }

    /**
     * Returns the string representation of the board by iterating through the "x" and "o" board
     * @return a string representation of the connect-4 board
     */
    public String toString()
    {
        String xBoard = Long.toBinaryString(getBoard(0));
        String oBoard = Long.toBinaryString(getBoard(1));
        xBoard = "0".repeat(64 - xBoard.length()) + xBoard;
        oBoard = "0".repeat(64 - oBoard.length()) + oBoard;
        String result = "";
        for (int row = ROWS - 1; row >= 0; row--)
        {
            for (int col = 0; col < COLS; col++)
            {
                int index = 64 - 1 - row - (col * COLS);
                if (xBoard.substring(index, index + 1).equals("1"))
                {
                    result += "x ";
                }
                else if (oBoard.substring(index, index + 1).equals("1"))
                {
                    result += "o ";
                }
                else
                {
                    result += "_ ";
                }
            }
            result += "\n";
        }
        return result;
    }

    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        String xBoard = Long.toBinaryString(getBoard(0));
        String oBoard = Long.toBinaryString(getBoard(1));
        xBoard = "0".repeat(64 - xBoard.length()) + xBoard;
        oBoard = "0".repeat(64 - oBoard.length()) + oBoard;

        graphics.setColor(BLUE);
        graphics.fillRect(DIAMETER, DIAMETER, DIAMETER * 12, DIAMETER * 21 / 2);

        for (int row = ROWS - 1; row >= 0; row--)
        {
            for (int col = 0; col < COLS; col++)
            {
                int index = 64 - 1 - row - (col * COLS);
                if (xBoard.substring(index, index + 1).equals("1"))
                {
                    graphics.setColor(RED);
                }
                else if (oBoard.substring(index, index + 1).equals("1"))
                {
                    graphics.setColor(YELLOW);
                }
                else
                {
                    graphics.setColor(Color.WHITE);
                }
                graphics.fillOval(DIAMETER * 2 + (DIAMETER * 3 / 2 * col), DIAMETER * 2 + (DIAMETER * 3 / 2 * (ROWS - row - 1)), DIAMETER, DIAMETER);
            }
        }
    }
}
