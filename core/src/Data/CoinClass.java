package Data;

/**
 *
 * @author phinix
 */
public class CoinClass implements Coin{ 
    private int value;
    private int [] coordinates;

    /**
     * Construktor
     * @param value of the coin
     * @param coordinates of the coin
     */
    public CoinClass(int value, int[] coordinates) {
        this.value = value;
        this.coordinates = coordinates;
    }
    /**
     * get the value of the coin 
     * @return  the value of the coin 
     */
    public int getValue() {
        System.out.println(value);
        return value;
    }
    /**
     * set the value of the coin 
     * @param value the value of the coin
     */ 
    public void setValue(int value) {
        this.value = value;
    }
    /**
     * get the coordinates of the field
     * @return  the coordinates of the field
     */
    public int[] getCoordinates() {
        System.out.println(coordinates);
        return coordinates;
    }
    /**
     * set the coordinates of the field
     * @param coordinates  the coordinates of the field
     */
    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }
    
}
