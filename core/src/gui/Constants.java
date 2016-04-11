/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author hebendanz_l
 */
public class Constants {
    
    //Window config
    public static int SCREENWIDTH = 800;
    public static int SCREENHEIGHT = 480;
    public static boolean FULLSCREEN = true;
    public static String WINDOWTITEL = "XBlast Reloaded";
    
    
    //Client config
    public static String CLIENTHOST = "127.0.0.1";
    public static int CLIENTPORT = 13199;
    
    //Server config
    public static boolean LOCALSERVER = true;
    public static int SERVERPORT = 13199;
    public static int MINPLAYERS = 1;
    public static int MAXPLAYERS = 4;
    public static int SERVERTIMEOUT = 20000; // 20 seconds
    
    
        
    //Ingame variable do not change!
    public static int PLAYERID = 0;
    public static int AMOUNTENEMYPLAYERS = 0;
    public static String MAPPATH = "maps/BasicMap.tmx";
    public static volatile boolean PLAYERSPAWNED = false;
}
