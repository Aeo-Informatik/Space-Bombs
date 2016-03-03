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
data.PlayerClass p1 = new data.PlayerClass(100,0,50,0,0);
data.MapClass m1 = new data.MapClass(20,30);
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
        data.BlocksClass b1=new data.BlocksClass(false, -100);
        data.BlocksClass b2=new data.BlocksClass(true, 3);
        
        //create the blocks that can be destroyed
        for(int i=2;i<=18;i++){
            for(int e=2; e<=28;e+=2){
                if((int)(Math.random()*4)!=4){
                   GameObjects[e][i]=b2; 
                   m1.setPassable(e, i, 1);
                }
            }
        }
        
        for(int i=2;i<=18;i++){
            if((int)(Math.random()*4)!=4){
                GameObjects[1][i]=b2;
                m1.setPassable(1, i, 1);
            }
            if((int)(Math.random()*4)!=4){
                GameObjects[29][i]=b2;
                m1.setPassable(29, i, 1);
            }
        }
        
        for(int i=2;i<=28;i++){
            if((int)(Math.random()*4)!=4){
                GameObjects[i][1]=b2; 
                m1.setPassable(i, 1, 1);
            }
            if((int)(Math.random()*4)!=4){
                GameObjects[i][19]=b2; 
                m1.setPassable(i, 19, 1);
            }
        }
        
        
        //create the blocks at the ends of the map
        for (int i=0;i<=30;i++){
           GameObjects[0][i]=b1;
           m1.setPassable(0, i, 1);
           GameObjects[20][i]=b1;
           m1.setPassable(20, i, 1);
        }
        
        for (int i=0;i<=20;i++){
           GameObjects[i][0]=b1;
           m1.setPassable(i, 0, 1);
           GameObjects[i][30]=b1;
           m1.setPassable(i, 30, 1);
        }
        
        //create the undestructible block raster
        for(int i=2;i<=18;i+=2){
            for(int e=2; e<=28;e+=2){
               GameObjects[e][i]=b1; 
               m1.setPassable(e, i, 1);
            }
        }
            
        
        
        
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
     * moves the player
     * @param x the x coordinate
     * @param y the y coordinate
     * @param direction which direction
     */
    public void movePlayer(int x,int y, char direction){
        
        if (GameObjects[y][x]==data.PlayerClass){
            
            data.PlayerClass p1;
            p1 = (PlayerClass) GameObjects[y][x];
            
            if(direction=='W'){
                if(m1.getPassable(y-1, x)==0){
                    GameObjects[y-1][x]=p1;
                    GameObjects[y][x]=null;
                }
            }
            
            if(direction=='A'){
                if(m1.getPassable(y, x-1)==0){
                    GameObjects[y][x-1]=p1;
                    GameObjects[y][x]=null;
                }
            }  
            
            if(direction=='S'){
                if(m1.getPassable(y+1, x)==0){
                    GameObjects[y+1][x]=p1;
                    GameObjects[y][x]=null;
                }
            }
            
            if(direction=='D'){
                if(m1.getPassable(y, x+1)==0){
                    GameObjects[y][x+1]=p1;
                    GameObjects[y][x]=null;
                }
            }

        }
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
