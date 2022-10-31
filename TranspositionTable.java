
import java.util.Objects;

/**
 *  Makes a hash table to store board position and their scores to reduce computation.
 *  No collision optimization, just replaces the current Pair
 *
 *  @author Neil Kakhandiki
 *  @version May 13, 2021
 */
public class TranspositionTable
{
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final int SIZE = 8388593;

    private Pair[] hashTable;

    /**
     * Create a new TranspositionTable object with SIZE
     */
    public TranspositionTable()
    {
        hashTable = new Pair[SIZE];
    }

    /**
     * Hash function to return index using modulus division
     * @param key to find
     * @return index of the array the key belongs
     */
    public int getIndex(long key)
    {
        return Math.abs((int)key % SIZE);
    }

    /**
     * Puts a key, value pair in the hash table or sets the current pair to a new pair
     * @param key to put
     * @param value to put
     */
    public void put(long key, TTEntry value)
    {
        int index = getIndex(key);
        if (Objects.isNull(hashTable[index]))
        {
            hashTable[index] =  new Pair(key, value);
        }
        else
        {
            hashTable[index].setKey(key);
            hashTable[index].setValue(value);
        }
    }

    /**
     * Returns the TTEntry with a given key
     * @param key to get Entry
     * @return the Entry or null if there is an Entry
     */
    public TTEntry getEntry(long key)
    {
        int index = getIndex(key);
        if (!Objects.isNull(hashTable[index]))
        {
            if (hashTable[index].getKey() == key)
            {
                return hashTable[index].getEntry();
            }
        }
        return null;
    }
}
