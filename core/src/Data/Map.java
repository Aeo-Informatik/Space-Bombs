package Data;

import java.util.ArrayList;
/**
 *
 * @author phinix
 */
public interface Map {
    int width=31;//width of the map
    int height=21;//height of the map
    int[][] passable =new int[0][1];//if the position is passaple
    int[][] blockposition=new int [1][0];//if ther is a block on this position
    public void setHeight(int height);
    public int getHeight();
    public int getWidth();
    public void setWidth(int width);
    public boolean getPassable(int x, int y);
    public void setPassable(int x,int y,int passable);
    public boolean getBlockposition(int x , int y);
    public void setBlockposition(int x,int y,int blockposition);
}
