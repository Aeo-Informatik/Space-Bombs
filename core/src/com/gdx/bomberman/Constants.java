/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.bomberman;

import server.Server;

/**
 *
 * @author qubasa
 */
public class Constants 
{
    // Client settings
    public static final int CONNECTIONPORT = 13199;
    public static final boolean CLIENTDEBUG = false;
    public static final boolean PROCESSDATADEBUG = false;
    
    // Server settings
    public static final int LISTENINGPORT = 13199;
    public static final int MAXPLAYERS = 4;
    public static final boolean SERVERDEBUG = false;
    
    // Audio settings
    public static float MUSICVOLUME = 0.05f;
    public static float SOUNDVOLUME = 0.20f;
        
    // Window settings
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 480;
    public static final boolean DEFAULTFULLSCREEN = false;
    public static final String WINDOWTITEL = "Aeo Bombs";
    public static final String WINDOWICONPATH = "other/icon.png";
    public static final float DEFAULTZOOM = 0.8f;
    public static final float DEFAULTMAXZOOMIN = 0.5f;
    public static final float DEFAULTMAXZOOMOUT = 1.5f;
    
    // General entity settings
    public static final float DEFAULTCAMERASPEED = 10.0f;
    public static final float DEFAULTENTITYSPEED = 1.5f;
    
    // Coin settings
    public static final int COINVALUE = 10; // Coins
    public static final int COINDROPCHANCE = 5; // 50% (1-10)
    public static final float COINBONUSTIMER = 5; // Seconds
    public static final int COINBONUS = 10; // Coins
    public static final int STARTCOINS = 100;
    
    // Item settings
    public static final long ITEMTIMER = 5; // Seconds
    public static final int COINBAGVALUE = 50; // Coins
    public static final boolean DELETEITEMSTHROUGHBOMB = false;
    public static final float SPAWNPROTECTION = 0.5f;
    
    // Player settings
    public static final int MAXLIFE = 6;
    public static final float MAXSPEED = 2f;
    public static final int DEFAULTLIFE = 3;
    public static final float GODMODEDURATION = 2f; // Seconds
    
    // Bomb settings
    public static final int MAXBOMBPLACE = 5;
    public static final int MAXBOMBRANGE = 5; // Cells
    public static final int DEFAULTBOMBRANGE = 2;
    public static final int DEFAULTBOMBPLACE = 5;
    public static final int INFINITYREPRODUCTIONCHANCE = 10; // Decreases every time
    public static final int INFINITYREPRODUCTIONDECREASE = 2; // After every copy - 20%
    
    // Bomb cost settings
    public static final int BOMB1 = 0;
    public static final int BOMB2 = 30;
    public static final int BOMB3 = 50;
    public static final int BOMB4 = 70;
    public static final int BOMB5 = 80;
    public static final int BOMB6 = 100;
    public static final int BOMB7 = 120;
    public static final int BOMB8 = 140;
    public static final int BOMB9 = 200;
    
    
    
    // Ingame variables DO NOT CHANGE!
    //public static Server OWNSERVEROBJ = null;
    public static int PLAYERID = 0;
    public static int AMOUNTPLAYERS = 0;
    public static volatile boolean PLAYERSPAWNED = false;
    public static float PLAYERWIDTH;
    public static float PLAYERHEIGHT;
    public static float MAPTEXTUREWIDTH;
    public static float MAPTEXTUREHEIGHT;
    public static float DELTATIME = 0;
    public static int AMOUNTSPECTATORS = 0;
    public static int BOMBIDCOUNTERP1 = 0;
    public static int BOMBIDCOUNTERP2 = 1000;
    public static int BOMBIDCOUNTERP3 = 2000;
    public static int BOMBIDCOUNTERP4 = 3000;
}
