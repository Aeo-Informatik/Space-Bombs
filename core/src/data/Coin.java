package data;

/**
 *
 * @author phinix
 */
public interface Coin {
    int value=1;//how many coins are laying on the field
    int [] coordinates= new int[1];//the coordinates of the field
    
    /**
     * set the value of the coin 
     * @param value the value of the coin
     */
    public void setValue(int value);
    
    /**
     * set the coordinates of the field
     * @param x the x coordinate of the field
     * @param y the y coordinate of the field
     */
    public void setCoordinates(int x, int y);
    
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
