package Data;

import java.util.ArrayList;
/**
 *
 * @author cb0703
 */
public interface Map {
    int width=31;//width of the map
    int height=21;//height of the map
    int[][] passable =new int[0][1];//if the position is passaple
    int[][] blockposition=new int [1][0];//if ther is a block on this position
}
