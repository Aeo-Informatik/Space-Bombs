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

    public MapClass(int width, int height, int[][] passable, int[][] blockposition) {
        this.width = width;
        this.height = height;
        this.passable = passable;
        this.blockposition = blockposition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getPassable() {
        return passable;
    }

    public void setPassable(int[][] passable) {
        this.passable = passable;
    }

    public int[][] getBlockposition() {
        return blockposition;
    }

    public void setBlockposition(int[][] blockposition) {
        this.blockposition = blockposition;
    }
    
    

}
