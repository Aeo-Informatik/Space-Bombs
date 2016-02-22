package Data;

import java.util.ArrayList;

/**
 *
 * @author phinix
 */
public class PlayerClass implements Player{
    private int life;//the life of the player
    private int[] coordinates;//coordinates of the player
    private int team ;//the team of the player
    private int coins ;//coins of the player
    private boolean immortality ;//if the player is immortal
    private ArrayList<int[]> spawnpoint =new ArrayList();//places where the player can respawn
    
    /**
     * Constructor
     * @param life the life of the player
     * @param team the team of the player
     * @param coins coins of the player
     */
    public PlayerClass(int life, int team, int coins) {
        this.life = life;
        this.team = team;
        this.coins = coins;
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
    

    public boolean setImmortality() {
        return immortality;
    }

    public void getImmortality(boolean immortality) {
        this.immortality = immortality;
    }

    public ArrayList<int[]> getSpawnpoint() {
        return spawnpoint;
    }

    public void setSpawnpoint(ArrayList<int[]> spawnpoint) {
        this.spawnpoint = spawnpoint;
    }
    
    public int[] respawn(){
        return coordinates;
        
    }
   
}
