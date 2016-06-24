/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.bomberman;

import networkServer.RunServer;

/**
 *
 * @author qubasa
 */
public class Constants {
    
    //Window config
    public static int SCREENWIDTH = 800;
    public static int SCREENHEIGHT = 480;
    public static boolean FULLSCREEN = true;
    public static String WINDOWTITEL = "Aeo Bombs";
    public static String WINDOWICONPATH = "other/icon.png";
    
    //Main config
    public static boolean PROCESSDATADEBUG = false;
    public static String MAPPATH = "maps/BasicMap.tmx";
    
    //Client config
    public static String SERVERIP = "localhost";
    public static int CONNECTIONPORT = 13199;
    public static boolean CLIENTDEBUG = false;
    public static boolean CLIENTSHOWPONG = false; // Show received pong
    public static int PINGDELAY = 2; // 1 ping each 2 seconds
    
    //Server config
    public static boolean TESTSERVER = true; // Only for one player. Starts the game instantly
    public static int SERVERPORT = 13199;
    public static int MAXPLAYERS = 4;
    public static boolean SERVERDEBUG = false;
    public static boolean SERVERSHOWPING = false; // Show received ping
        
    //Ingame variable DO NOT CHANGE!
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
