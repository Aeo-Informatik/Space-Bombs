package Data;

/**
 *
 * @author cb0703
 */
public interface Coin {
    int value=1;//how many coins are laying on the field
    int [] coordinates= []];//the coordinates of the field
    
    /**
     * set the value of the coin 
     * @param value the value of the coin
     */
    public void setValue(int value);
    
    /**
     * set the coordinates of the field
     * @param tuple  the coordinates of the field
     */
    public void setCoordinates(int[] coordinates);
    
    /**
     * get the value of the coin 
     * @return  the value of the coin 
     */
    public int getValue();
    
    /**
     * get the coordinates of the field
     * @return  the coordinates of the field
     */
    public int[] getCoordinates();
    
    
    
}
