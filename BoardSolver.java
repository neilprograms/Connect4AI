
import java.util.Objects;

/**
 * Solves a Connect-4 Position using the negamax algorithm with alpha beta pruning and transposition tables
 *
 *  @author Neil Kakhandiki
 *  @version May 13, 2021
 */
public class BoardSolver
{
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final int[] MOVE_ORDER = {3, 4, 2, 5, 1, 6, 0};
    public static final int EXACT = 1;
    public static final int LOWERBOUND = -(ROWS * COLS) / 2 + 3;
    public static final int UPPERBOUND = (ROWS * COLS + 1) / 2 - 3;

    private TranspositionTable transTable;

    /**
     * Create a new BoardSolver object with a transposition table
     */
    public BoardSolver()
    {
        transTable = new TranspositionTable();
    }

    /**
     * Returns the score of a given board position using the negamax algorithm with alpha beta pruning and transposition tables
     * @param board position
     * @param alpha lower bound
     * @param beta upper bound
     * @return the score of a board position
     */
    public int negamax(BitBoard board, int alpha, int beta)
    {
        int alphaOrig = alpha;
        TTEntry ttEntry = transTable.getEntry(board.getKey()); // gets the ttEntry from the key of position
        if (!Objects.isNull(ttEntry))
        {
            // sets alpha or beta to the lower bound or upper bound, or returns the value
            if (ttEntry.getFlag() == EXACT)
            {
                return ttEntry.getValue();
            }
            else if (ttEntry.getFlag() == LOWERBOUND)
            {
                alpha = Math.max(alpha, ttEntry.getValue());
            }
            else if (ttEntry.getFlag() == UPPERBOUND)
            {
                beta = Math.min(beta, ttEntry.getValue());
            }

            if (alpha >= beta) // pruning
            {
                return ttEntry.getValue();
            }
        }

        // checks for draw game
        if (board.isFull())
        {
            return 0;
        }

        // checks if a player can win on next move
        int turn = 0;
        for (int col = 0; col < COLS; col++)
        {
            turn = board.getTurn() - 1;
            if (board.canMove(col))
            {
                board.makeMove(col);
                if (board.isWin(board.getBoard(turn)))
                {
                    board.undoMove();
                    return (ROWS * COLS +  1 - board.getCounter()) / 2; // 22 -  number of pieces played by the winning player
                }
                board.undoMove();
            }
        }

        int score = Integer.MIN_VALUE;
        // compute the score of each position
        for (int col : MOVE_ORDER)
        {
            if (board.canMove(col))
            {
                board.makeMove(col);
                score = Math.max(score, -negamax(board, -beta, -alpha));
                board.undoMove();
                alpha = Math.max(alpha, score);
                if (alpha >= beta) // pruning
                {
                    break;
                }
            }
        }

        // checks if the flag is an upper bound, lower bound, or exact value
        int flag = 0;
        if (score <= alphaOrig)
        {
            flag = UPPERBOUND;
        }
        else if (score >= beta)
        {
            flag = LOWERBOUND;
        }
        else
        {
            flag = EXACT;
        }

        transTable.put(board.getKey(), new TTEntry(flag, score)); // adds entry to hashtable
        return score;
    }

    /**
     * Returns the best column to play, by calling negamax() to get the score of each possible move played
     * @param board position
     * @return bestCol of position
     */
    public int solve(BitBoard board)
    {
        System.out.println("----------------------");
        int bestScore = Integer.MIN_VALUE;
        int bestCol = 3;
        int turn = 0;
        for (int col : MOVE_ORDER)
        {
            turn = board.getTurn() - 1;
            if (board.canMove(col))
            {
                board.makeMove(col);
                if (board.isWin(board.getBoard(turn)))
                {
                    board.undoMove();
                    return col;
                }
                int score = -negamax(board, -UPPERBOUND, -LOWERBOUND);
                System.out.println("Column " + (col + 1) + " score: " + score);
                if (score > bestScore)
                {
                    bestScore = score;
                    bestCol = col;
                }
                board.undoMove();
            }
        }
        System.out.println("----------------------");
        return bestCol;
    }
}
