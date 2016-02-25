package data;

import java.util.ArrayList;

/**
 *
 * @author phinix
 */
public class PlayerClass implements Player, GameObjects{
    private int life;//the life of the player
    private int[] coordinates;//coordinates of the player
    private int team ;//the team of the player
    private int coins ;//coins of the player
    private boolean immortality ;//if the player is immortal
//    private ArrayList<int[]> spawnpoint =new ArrayList();//places where the player can respawn
    
    /**
     * Constructor
     * @param life the life of the player
     * @param team the team of the player
     * @param coins coins of the player
     */
    public PlayerClass(int life, int team, int coins, int[] coordinates) {
        this.life = life;
        this.team = team;
        this.coins = coins;
        this.coordinates=coordinates;
    }
    
    /**
     * get the life of the player
     * @return the life of the player
     */
    public int getLife() {
        return life;
        
    }
    
    /**
     * set the life of the player 
     * @param life the life of the player
     */
    public void setLife(int life) {
        this.life = life;
    }

     /**
     * get thde coordinates of the player
     * @return coordinates of the player 
     */
    public int[] getCoordinates() {
        return coordinates;
    }
    
    /**
     * set the coordinates of the player
     * @param coordinates the coordinates of the player
     */
    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * add coins to player`s coins
     * @param coins that will be added
     */
    public void addCoins(int coins){
        
    }
    
    /**
     * get how many coins the player has
     * @return how many coins the player has
     */
    public int getCoins() {
        return coins;
    }
    
    /**
     * set how many coins the player has
     * @param coins how many coins the player has
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }
    
    /**
     * get if the player is immortal
     * @return if the player is immortal
     */
    public boolean getImmortality() {
        return immortality;
    }

    public void setImmortality(boolean immortality) {//God mode
        this.immortality = immortality;
    }


    
    /**
     * the player returns to the spawnpoint and respawns there
     * @return the coordinates of the spawnpoint
     */
    public int[] respawn(){
        
        return coordinates;
    }
   
}
