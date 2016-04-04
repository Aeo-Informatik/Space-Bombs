/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import data.PlayerClass;
import java.awt.event.KeyEvent;


/**
 *
 * @author Domplo & phinix
 */
public class CoreClass {
    
data.GameObjects [][] GameObjects = new data.GameObjects [20][30];// creates a matrix from GameObjects

    public CoreClass() {
        
    }

    public data.GameObjects getGameObject(int x, int y) {
        System.out.println(GameObjects [y][x]);
        return GameObjects[y][x];
    }

    public void addGameObject(int x,int y,data.GameObjects gameObject) {
        this.GameObjects[y][x] = gameObject;
    }
 
    /**
     * create the blocks of the map
     */    
    public void createMap(){
    }   

    /**
     * player can place a bomb
     */
    public void placeBomb(int x,int y){
        
    }

    /**
    * looks for players and blocks that would be destroyed
    * @param bomb 
     */
    public void explode(data.BombClass bomb){//Bumm
    
    }
    
    /**
     * checks how many coins are on a field
     * @param coordinates of the field
    * @return 
     */
    public data.CoinClass checkCoins(int[] coordinates){
    return null;
    }

    /**
     * looks for what the key that is pressed is used for
    * @param key 
    */
    public void hotkey(char key){
    
    }
 
    /**
     * cuts long Strings from NetworkClient into short Strings
     * @param data String from NetworkClient
     */
    public void analyseData(String data){
    }

    }
