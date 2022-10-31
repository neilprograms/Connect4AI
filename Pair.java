
/**
 *  A pair object for a hashtable with a key and value pair
 *
 *  @author Neil Kakhandiki
 *  @version May 16, 2021
 */
public class Pair
{
    private long key;
    private TTEntry entry;

    /**
     * Create a new Pair object with key and a value
     * @param key is the position code
     * @param entry is the TTEntry
     */
    public Pair(long key, TTEntry entry)
    {
        this.key = key;
        this.entry = entry;
    }

    /**
     * Get the current value of key
     * @return The value of key for this object
     */
    public long getKey()
    {
        return key;
    }

    /**
     * Get the current value of entry
     * @return The value of entry for this object
     */
    public TTEntry getEntry()
    {
        return entry;
    }

    /**
     * Set the value of key for this object
     * @param key The new value for key
     */
    public void setKey(long key)
    {
        this.key = key;
    }

    /**
     * Set the value of entry for this object
     * @param entry The new value for entry
     */
    public void setValue(TTEntry entry)
    {
        this.entry = entry;
    }
}
