
/**
 *  The Entry in a Transposition Table
 *
 *  @author Neil Kakhandiki
 *  @version May 19, 2021
 */
public class TTEntry
{
    private int flag;
    private int value;

    /**
     * Create a new TTEntry object.
     * @param flag type of entry
     * @param value is the value of a position
     */
    public TTEntry(int flag, int value)
    {
        this.flag = flag;
        this.value = value;
    }

    /**
     * Get the current value of flag.
     * @return The value of flag for this object.
     */
    public int getFlag()
    {
        return flag;
    }

    /**
     * Get the current value of value.
     * @return The value of value for this object.
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Set the value of flag for this object.
     * @param flag The new value for flag.
     */
    public void setFlag(int flag)
    {
        this.flag = flag;
    }

    /**
     * Set the value of value for this object.
     * @param value The new value for value.
     */
    public void setValue(int value)
    {
        this.value = value;
    }
}
