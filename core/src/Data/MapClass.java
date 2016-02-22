package Data;

/**
 *
 * @author cb0703
 */
public class MapClass {
    private int width;
    private int height;
    private int[][] passable ;
    private int[][] blockposition;
    /**
     * 
     * @param width
     * @param height
     * @param passable
     * @param blockposition 
     */
    public MapClass(int width, int height, int[][] passable, int[][] blockposition) {
        this.width = width;
        this.height = height;
        this.passable = passable;
        this.blockposition = blockposition;
    }
    /**
     * 
     * @return 
     */
    public int getWidth() {
        return width;
    }
    /**
     * 
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * 
     * @return 
     */
    public int getHeight() {
        return height;
    }
    /**
     * 
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * 
     * @return 
     */
    public int[][] getPassable() {
        return passable;
    }
    /**
     * 
     * @param passable 
     */
    public void setPassable(int[][] passable) {
        this.passable = passable;
    }
    /**
     * 
     * @return 
     */
    public int[][] getBlockposition() {
        return blockposition;
    }
    /**
     * 
     * @param blockposition 
     */
    public void setBlockposition(int[][] blockposition) {
        this.blockposition = blockposition;
    }
    
    

}
