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
    
    //Windows config
    public static int SCREENWIDTH = 800;
    public static int SCREENHEIGHT = 480;
    public static boolean FULLSCREEN = false;
    public static String WINDOWTITEL = "XBlast Reloaded";
    
    //Client config
    public static String CLIENTHOST = "127.0.0.1";
    public static int CLIENTPORT = 13199;
    
    //Ingame variable
    public static int PLAYERID = 0;
    public static int AMOUNTENEMYPLAYERS = 0;
    public static String MAPPATH = "maps/BasicMap.tmx";
    
    
    //Server config
    public static boolean LOCALSERVER = true;
    public static int SERVERPORT = 13199;
    public static int MINPLAYERS = 1;
    public static int MAXPLAYERS = 4;
    public static int SERVERTIMEOUT = 20000; // 20 seconds
}
