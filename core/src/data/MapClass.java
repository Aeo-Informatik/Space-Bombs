package data;

import java.util.ArrayList;

/**
 *
 * @author phinix
 */
public class MapClass{
    private int width;//width of the map
    private int height;//height of the map
    private int[][] passable=new int [20][30];//if ther is a block on this position
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
     * get if this place is passable
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return if this place is passable
     */
    public boolean getPassable(int x , int y) {
        if(passable[y][x]==1){
            System.out.println("true");
            return true;
        }else{
            System.out.println("true");
            return false;
        }
    }
    /**
     * set if this place is passable
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param passable if this place is passable  
     */
    public void setBlockposition(int x,int y,int passable) {
        this.passable[y][x]=passable;
    }
    
    public int[] getSpawn (int team){
        int[]Coordinates=new int[1];
        Coordinates=Spawn.get(team);
        return Coordinates;
    }
}
