package Data;

import java.util.ArrayList;

/**
 *
 * @author phinix
 */
public class PlayerClass implements Player{
   private int life;
   private int[] coordinates;
   private int team ;
   private int coins ;
   private boolean immortality ;
   private ArrayList<int[]> spawnpoint =new ArrayList();

    public PlayerClass(int life, int team, int coins) {
        this.life = life;
        this.team = team;
        this.coins = coins;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void addCoins(int coins){
        
    }
    
    public int getCoins() {
        return coins;
    }

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
        
        
    }
   
}
