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
public interface core {

int[][]map = new int [21][31]; //creates a matrix as map
GameObjects [][] gameObject = new GameObjects [21][31]; //creates a matrix of gameObjects 
 
/**
 * Setter for map
 * @param map 
 */
public void setMap(int[][] map); 

/**
 * Getter for map
 * @return 
 */
public int[][] getMap();

/**
 * create an object map
 */    
public void createMap();   

/**
 * updates the map 
 */
public int[][] updateMap();

/**
 * Setter for gameObject
 * @param gameObject 
 */
public void setGameObject(GameObjects[][] gameObject);

/**
 * Getter for gameObject
 * @return 
 */
public GameObjects[][] getGameObject();

/**
 * player can place a bomb
 */
public void placeBomb();

/**
 * looks for players and blocks that would be destroyed
 * @param bomb 
 */
public void explode(Bomb bomb);

/**
 * moves the player
 * @param player which player
 * @param direction which direction
 * @return the coordinates of the player after his move
 */
public tuple movePlayer(Player player, char direction);        
        
/**
 * checks how many coins are on a field
 * @param coordinates of the field
 * @return 
 */
public Coin checkCoins(tuple coordinates);

/**
 * looks for what the key that be pressed is used for
 * @param key 
 */
public void hotkey(char key);
 
/**
 * cuts long Strings from NetworkClient into short Strings
 * @param data String from NetworkClient
 */
public void analyseData(String data);
        
}
