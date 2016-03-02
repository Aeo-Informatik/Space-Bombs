package data;

import java.util.ArrayList;

/**
 *
 * @author phinix
 */
public class MapClass{
    private int width;//width of the map
    private int height;//height of the map
    private boolean[][] passable=new boolean [20][30] ;//if the position is passaple
    private int[][] blockposition=new int [20][30];//if ther is a block on this position
    private ArrayList<int[]>Spawn=new ArrayList();//the places where the different teams can spawn
    
    /**
     * Construktor
     * @param width of th map
     * @param height of the map
     */
    public MapClass(int width, int height) {
        this.width = width;
        this.height = height;
        int[] XY =new int[1];
        //fill the ArrayList with the Spawnpoints
        XY[0]=0;    XY[1]=0;
        Spawn.set(0, XY);
        XY[0]=width;XY[1]=0;
        Spawn.set(1, XY);
        XY[0]=0;    XY[1]=height;
        Spawn.set(2, XY);
        XY[0]=width;XY[1]=height;
        Spawn.set(3, XY);
        
        
    }
    /**
     * get width of the map
     * @return width of the map
     */
    public int getWidth() {
        return width;
    }
    /**
     * set width of the map
     * @param width of the map
     */
    public void setWidth(int width) {
        this.width = width;
    }
    /**
     * get height of the map
     * @return height of the map
     */
    public int getHeight() {
        return height;
    }
    /**
     * set height of the map
     * @param height of the map
     */
    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * get if the possition is passable
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return if th epossition is passable
     */
    public boolean getPassable(int x, int y) {
        if (passable[x][y]==true){
            System.out.println("true");
            return true;
        }else{
            System.out.println("false");
            return false;
        }
                
    }
    /**
     * set if the possition is passible
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param passable if the possition is passable
     */
    public void setPassable(int x,int y, boolean passable) {
        this.passable[y][x]=passable;
    }
    /**
     * get if there is a block on that position
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return if there is a block on that position
     */
    public boolean getBlockposition(int x , int y) {
        if(blockposition[y][x]==1){
            System.out.println("true");
            return true;
        }else{
            System.out.println("true");
            return false;
        }
    }
    /**
     * set if there is a block on that position
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param blockposition if there is a block on that position
     */
    public void setBlockposition(int x,int y,int blockposition) {
        this.blockposition[y][x]=blockposition;
    }
    
    public int[] getSpawn (int team){
        int[]Coordinates=new int[1];
        Coordinates=Spawn.get(team);
        return Coordinates;
    }
}
