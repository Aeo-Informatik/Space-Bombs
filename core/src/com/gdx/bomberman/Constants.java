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
    //Client config
    public static boolean TESTSERVER = true; // Only for one player. Starts the game instantly
    public static int CONNECTIONPORT = 13199;
    public static boolean CLIENTDEBUG = false;
    public static boolean PROCESSDATADEBUG = false;
    
    //Audio config
    public static float MUSICVOLUME = 0.05f;
    public static float SOUNDVOLUME = 0.20f;
        
    //Window config
    public static int SCREENWIDTH = 800;
    public static int SCREENHEIGHT = 480;
    public static boolean FULLSCREEN = false;
    public static String WINDOWTITEL = "Aeo Bombs";
    public static String WINDOWICONPATH = "other/icon.png";
    
    //Game config
    public static float DEFAULTZOOM = 0.8f;

    //Entity config
    public static float DEFAULTCAMERASPEED = 10.0f;
    public static float DEFAULTENTITYSPEED = 1.5f;
    public static int DEFAULTBOMBRANGE = 2;
    
    //Item config
    public static float ITEMTIMER = 5;
    public static int MAXBOMBS = 5;
    public static int MAXBOMBRANGE = 5;
    public static int MAXLIFE = 6;
    public static int COINVALUE = 10;
    public static float MAXSPEED = 2f;
       
    //Ingame variables DO NOT CHANGE!
    public static Server OWNSERVEROBJ = null;
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
