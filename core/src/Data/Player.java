package Data;

import java.util.ArrayList;
/**
 *
 * @author phinix
 */

public interface Player {
   
    int life =0;//the life of the player
    int[] coordinates=new int[1];//coordinates of the player
    int team =1;//the team of the player
    int coins =100;//coins of the player
    boolean immortality =true;//if the player is immortal
    ArrayList<int[]> spawnpoint =new ArrayList();//places where the player can respawn
    
    /**
     * get thde coordinates of the player
     * @return coordinates of the player 
     */
    public int[] getCoordinates();
    
    /**
     * get if the player is immortal
     * @return if the player is immortal
     */
    public boolean getImmortality();
    
    /**
     * get the life of the player
     * @return the life of the player
     */
    public int getLife();
    
    /**
     * set the life of the player 
     * @param life the life of the player
     */
    public void setLife(int life);
    
    /**
     * set the coordinates of the player
     * @param coordinates the coordinates of the player
     */
    public void setCoordinates(int[] coordinates);
    
    /**
     * add coins to player`s coins
     * @param coins that will be added
     */
    public void addCoins(int coins);
    
    /**
     * get how many coins the player has
     * @return how many coins the player has
     */
    public int getCoins();
    
    /**
     * set how many coins the player has
     * @param coins how many coins the player has
     */
    public void setCoins(int coins);
    
    /**
     * the player returns to the spawnpoint and respawns there
     * @return the coordinates of the spawnpoint
     */
    public int[] respawn();
    
}
