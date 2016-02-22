/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

/**
 *
 * @author plonies_d
 */
public class coreClass {
    
int[][]map = new int [31][21]; //creates a matrix as map
GameObjects [][] gameObject = new GameObjects [31][21]; //creates a matrix of gameObjects    

public coreClass() {
}

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public GameObjects[][] getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObjects[][] gameObject) {
        this.gameObject = gameObject;
    }
 


    /**
     * create an object map
     */    
    public void createMap(){
        Map m = new Map(31,21,int[31][21],int[31][21]);
    }   

    /**
     * updates the map 
     */
    public int[][] updateMap(){}

    /**
     * player can place a bomb
     */
    public void placeBomb(){}

    /**
         * looks for players and blocks that would be destroyed
    * @param bomb 
     */
    public void explode(Bomb bomb){}

    /**
     * moves the player
     * @param player which player
     * @param direction which direction
     * @return the coordinates of the player after his move
     */
    public int[] movePlayer(Player player, char direction){}        
        
    /**
     * checks how many coins are on a field
     * @param coordinates of the field
    * @return 
     */
    public Coin checkCoins(int[] coordinates){}

    /**
     * looks for what the key that is pressed is used for
    * @param key 
    */
    public void hotkey(char key){}
 
    /**
     * cuts long Strings from NetworkClient into short Strings
     * @param data String from NetworkClient
     */
    public void analyseData(String data){}
    data = NetworkClient.decode();
    
    }
