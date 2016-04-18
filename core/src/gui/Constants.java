/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author qubasa
 */
public class Constants {
    
    //Window config
    public static int SCREENWIDTH = 800;
    public static int SCREENHEIGHT = 480;
    public static boolean FULLSCREEN = false;
    public static String WINDOWTITEL = "XBlast Reloaded";
    
    
    //Main config
    public static boolean PROCESSDATADEBUG = false;
    public static String MAPPATH = "maps/BasicMap.tmx";
    
    
    //Client config
    public static String CLIENTHOST = "localhost";
    public static int CLIENTPORT = 13199;
    public static boolean CLIENTDEBUG = false;
    
    
    //Server config
    public static boolean LOCALSERVER = false; // Only for one player. Starts the game instantly
    public static int SERVERPORT = 13199;
    public static int MINPLAYERS = 1;
    public static int MAXPLAYERS = 4;
    public static int SERVERTIMEOUT = 20000; // 20 seconds
    public static boolean SERVERDEBUG = false;
    
        
    //Ingame variable DO NOT CHANGE!
    public static int PLAYERID = 0;
    public static int AMOUNTENEMYPLAYERS = 0;
    public static volatile boolean PLAYERSPAWNED = false;
}
