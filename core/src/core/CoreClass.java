/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import sun.net.NetworkClient;

/**
 *
 * @author plonies_d
 */
public class CoreClass {
    
int[][]map = new int [31][21]; //creates a matrix as map
data.GameObjects [][] GameObjects = new data.GameObjects [31][21];// creates a matrix from GameObjects
data.PlayerClass p1 = new data.PlayerClass(100,0,50,0,0);
public CoreClass() {
}

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public data.GameObjects[][] getGameObject() {
        return GameObjects;
    }

    public void setGameObject(data.GameObjects[][] gameObject) {
        this.GameObjects = gameObject;
    }
 


    /**
     * create an object map
     */    
    public void createMap(){
        data.MapClass m1 = new data.MapClass(31, 21);
    }   

    /**
     * updates the map 
     */
    public int[][] updateMap(){
    
    }

    /**
     * player can place a bomb
     */
    public void placeBomb(){
    
    }

    /**
         * looks for players and blocks that would be destroyed
    * @param bomb 
     */
    public void explode(data.BombClass bomb){
    
    }
    
    
    /**
     * moves the player
     * @param player which player
     * @param direction which direction
     * @return the coordinates of the player after his move
     */
    public int[] movePlayer(data.PlayerClass player, char direction, KeyEvent e){
        String key =" "+ e.getKeyChar();
        int[] coordinates = new int[1];
        coordinates = p1.getCoordinates();
             if(key=="d"&&true){
             coordinates[0] = coordinates[0]+1;
             }
             if(key=="a"){
              coordinates[0] = coordinates[0]-1;
             }
             
             
             
             
             
             
    }        
    
    
    
    /**
     * checks how many coins are on a field
     * @param coordinates of the field
    * @return 
     */
    public data.CoinClass checkCoins(int[] coordinates){
    
    }

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
    //String[] newData = data.split(Pattern.quote("|"));
    
    
    }
