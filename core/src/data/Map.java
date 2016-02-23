package data;

/**
 *
 * @author phinix
 */
public interface Map {
    int width=31;//width of the map
    int height=21;//height of the map
    int[][] passable =new int[0][1];//if the position is passaple
    int[][] blockposition=new int [1][0];//if ther is a block on this position
    
    /**
     * set height of the map
     * @param height of the map
     */    
    public void setHeight(int height);
    
    /**
     * get height of the map
     * @return height of the map
     */
    public int getHeight();
    
    /**
     * get width of the map
     * @return width of the map
     */
    public int getWidth();
    
    /**
     * set width of the map
     * @param width of the map
     */
    public void setWidth(int width);
    
    /**
     * get if the possition is passable
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return if th epossition is passable
     */
    public boolean getPassable(int x, int y);
    
    /**
     * set if the possition is passible
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param passable if the possition is passable
     */
    public void setPassable(int x,int y,int passable);
    
    /**
     * get if there is a block on that position
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return if there is a block on that position
     */
    public boolean getBlockposition(int x , int y);
    
    /**
     * set if there is a block on that position
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param blockposition if there is a block on that position
     */
    public void setBlockposition(int x,int y,int blockposition);
}
