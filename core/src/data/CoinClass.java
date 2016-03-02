package data;

/**
 *
 * @author phinix
 */
public class CoinClass implements GameObjects{ 
    private int value;//how many coins are laying on the field
    private int [] coordinates;//the coordinates of the field

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
        System.out.println(coordinates[0]);
        System.out.println(coordinates[1]);
        return coordinates;
    }
    /**
     * set the coordinates of the field
     * @param x the x coordinate of the field
     * @param y the y coordinate of the field
     */
    public void setCoordinates(int x, int y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }    
}
