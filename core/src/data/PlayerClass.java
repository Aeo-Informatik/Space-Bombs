package data;



/**
 *
 * @author phinix
 */
public class PlayerClass implements Player, GameObjects{
    private int life;//the life of the player
    private int[] coordinates;//coordinates of the player
    private int team ;//the team of the player from 0 to 3
    private int coins ;//coins of the player
    private boolean immortality ;//if the player is immortal
    
    /**
     * Constructor
     * @param life the life of the player
     * @param team the team of the player
     * @param coins coins of the player
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public PlayerClass(int life, int team, int coins, int x,int y) {
        this.life = life;
        this.team = team;
        this.coins = coins;
        this.coordinates[0]=x;
        this.coordinates[1]=y;
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
        System.out.println(coordinates[0]);
        System.out.println(coordinates[1]);
        return coordinates;
    }
    
    /**
     * set the coordinates of the player
     * @param x the x coordinate
     * @param y the x coordinate
     */
    public void setCoordinates(int x, int y) {
        this.coordinates[0] = x;
        this.coordinates[1] =y;
    }
    
    /**
     * get the team of the player
     * @return the team of the player
     */
    public int getTeam() {
        return team;
    }

    /**
     * set the team of the player
     * @param team the team of the player
     */
    public void setTeam(int team) {
        this.team = team;
    }

    /**
     * add coins to player`s coins
     * @param coins that will be added
     */
    public void addCoins(int coins){
        this.coins=+coins;
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

    /**
     * set if the player is immortal
     * @param immortality if the player isimmortal
     */
    public void setImmortality(boolean immortality) {//God mode
        this.immortality = immortality;
    }


    


   
}
