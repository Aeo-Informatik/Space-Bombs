/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.bomberman;

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
    public static final boolean COLLIISIONDETECTIONDEBUG = false;
    public static final int CONNECTIONTIMEOUT = 2000; // Milliseconds

    // Test settings
    public static final boolean TESTMAP = false;
    public static final boolean TINYWINDOWS = false;
    public static final boolean CONTROLLERKEYDEBUG = false;
    public static final String TESTMAPPATH = "maps/Test-Map_(24x24).tmx";
    
    // Controll settings
    public static final boolean TOUCHPAD = false;
    public static final boolean TOUCHSCREEN = true;

    // Server settings
    public static final int LISTENINGPORT = 13199;
    public static final int MAXPLAYERS = 4;
    public static final boolean SERVERDEBUG = false;
    
    // Audio settings
    public static final float DEFAULTMUSICVOLUME = 0.02f;
    public static final float DEFAULTSOUNDVOLUME = 0.30f;//
    
    // Sound Settings
    // Explosion
    public static final float NORMALBOMBEXPLOSIONMOD = 0.4f;
    public static final float CUBICBOMBEXPLOSIONMOD = 0.8f;
    public static final float BLACKHOLEEXPLOSIONMOD = 1f;
    
    // Fuse
    public static final float X3FUSEMOD = 0.2f;
    public static final float REMOTEBOMBFUSEMOD = 2;
    public static final float INFINITYFUSEMOD = 0.2f;
    public static final float DYNAMITEFUSEMOD = 0.6f;
    public static final float BLACKHOLEFUSEMOD = 0.5f;
    public static final float BARRELFUSEMOD = 2f;
    
    // Items
    public static final float COINMOD = 0.3f;
    
    // Menu
    public static final float CLICKSOUNDSMOD = 0.3f;
    
    
    // Window settings
    public static final int SCREENWIDTH = 800;
    public static final int SCREENHEIGHT = 480;
    public static final boolean DEFAULTFULLSCREEN = false;
    public static final String WINDOWTITEL = "Space Bombs";
    public static final String WINDOWICONPATH = "other/icon.png";
    public static float DEFAULTZOOM = 1f;
    public static float SMARTPHONEZOOM = 0.5f;
    public static final float DEFAULTMAXZOOMIN = 0.5f;
    public static final float DEFAULTMAXZOOMOUT = 1.5f;
    
    
    
    // General player settings
    public static final float GODMODEDURATION = 2f; // Seconds
    
    // General Item settings
    public static final long ITEMTIMER = 5; // Seconds
    public static final boolean DELETEITEMSTHROUGHBOMB = false;
    public static final float SPAWNPROTECTION = 0.5f;
    public static final int STARTITEMFIELDNORMALBOMBRANGE = 1;
    public static final int STARTITEMFIELCUBICRANGE = 1; 
    
    // Coin settings
    public static final int COINVALUE = 10; // Coins
    public static final int COINDROPCHANCE = 5; // 50% (1-10)
    public static final float COINBONUSTIMER = 5; // Seconds
    public static final int COINBONUS = 10; // Coins
    public static final int STARTCOINS = 100;
    public static final int INCREASECOINBONUS = 5;
    public static final float INCREASECOINBONUSTIMEINTERVALL = 120; // Seconds (2 min)
    public static final int MAXCOINBONUS = 25;
    public static final int COINBAGVALUE = 100; // Coins
    
    // Life settings
    public static final int MAXLIFE = 6;
    public static final int DEFAULTLIFE = 3;
    
    // Speed settings
    public static final int MAXSPEED = 200;
    public static final int MINSPEED = 50;
    public static final int SPEEDUPGRADE = 10;
    public static final int DEFAULTCAMERASPEED = 10;
    public static final int ITEMSPEEDINCREASE = 2; // This is added to the position of the player
    public static final int DEFAULTENTITYSPEED = 100; // This is how many times it should be added
    
    // Bomb settings
    public static final int MAXBOMBPLACE = 10;
    public static final int MAXBOMBRANGE = 10; // Cells
    public static final int DEFAULTBOMBRANGE = 2;
    public static final int DEFAULTBOMBPLACE = 3;
    public static final int DEFAULTCUBICRANGE = 1;
    public static final int MAXCUBICRANGE = 3;
    
    // Bomb timers
    public static final float BLACKHOLEGRAVITYACTIVATION = 4f;
    public static final float BLACKHOLEGRAVITYDURATION = 5f;
    public static final float BLACKHOLEEXPLOSIONTIME = 0.7f;
    public static final float BLACKHOLEDELAYEXPLODEAFTERHITBYBOMB = 0.5f;
    public static final float BLACKHOLEEXPLOSIONDURATION = 4f;
    
    public static final float TELEPORTCOOLDOWNTIMER = 60f;
    
    public static final float NORMALBOMBEXPLOSIONTIME = 2f;
    public static final float NORMALBOMBEXPLOSIONDURATION = 0.5f;
    public static final float NORMALBOMBDELAYEXPLODEAFTERHITBYBOMB = 0.5f;
    
    public static final float TURRETEXPLOSIONTIME = 0.5f;
    public static final float TURRETEXPLOSIONDURATION = 0.5f;
    public static final float TURRETDELAYEXPLODEAFTERHITBYBOMB = 0.5f;
    
    public static final float REMOTEEXPLOSIONTIME = 0.2f;
    public static final float REMOTEEXPLOSIONDURATION = 0.5f;
    public static final float REMOTEDELAYEXPLODEAFTERHITBYBOMB = 0.5f;
    
    public static final float  BARRELEXPLOSIONDURATION = 0.4f;
    public static final float  BARRELDELAYEXPLODEAFTERHITBYBOMB = 0.4f;
    
    
    public static final float INFINITYBOMBEXPLOSIONTIME = 2;
    public static final float INFINITYBOMBEXPLOSIONDURATION = 0.5f;
    public static final float INFINITYBOMBDELAYEXPLODEAFTERHITBYBOMB = 0.5f;
    
    public static final float DYNAMITEBOMBEXPLOSIONTIME = 2f;
    public static final float  DYNAMITEBOMBEXPLOSIONDURATION = 0.4f;
    public static final float  DYNAMITEBOMBDELAYEXPLODEAFTERHITBYBOMB = 0.4f;
    
    public static final float X3BOMBEXPLOSIONTIME = 2;
    public static final float  X3BOMBEXPLOSIONDURATION = 0.5f;
    public static final float  X3BOMBDELAYEXPLODEAFTERHITBYBOMB = 0.5f;
    
    public static final float MINEACTIVATIONTIME = 1f;
    public static final float  MINEEXPLOSIONDURATION = 0.5f;
    public static final float  MINEDELAYEXPLODEAFTERHITBYBOMB = 0.5f;
    
    
    // Default bomb cost settings
    public static final int BOMB1 = 0; // Normal Bomb
    public static final int BOMB2 = 30; // Dynamite
    public static final int BOMB3 = 150; // Granade
    public static final int BOMB4 = 70; // X3
    public static final int BOMB5 = 50; // Mine
    public static final int BOMB6 = 200; // Black Hole
    public static final int BOMB7 = 70; // Remote Bomb
    public static final int BOMB8 = 50; // Barrel
    public static final int BOMB9 = 50; // Turret
    
    //After cubic range upgrade
    public static final int DYNAMITEPRICEUPGRADE = 20;

    
    
    
    //---------------------- KEYBOARD BINDINGS DO NOT CHANGE ----------------------
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String NONE = "NONE";
    
    // Ingame variables DO NOT CHANGE!
    public static int PLAYERID = 0;
    public static int AMOUNTPLAYERS = 0;
    public static volatile boolean PLAYERSPAWNED = false;
    public static boolean ISRUNNINGONSMARTPHONE = false;
    public static float PLAYERWIDTH;
    public static float PLAYERHEIGHT;
    public static float MAPTEXTUREWIDTH;
    public static float MAPTEXTUREHEIGHT;
    public static float DELTATIME = 0;
    public static int BOMBIDCOUNTERP1 = 0;
    public static int BOMBIDCOUNTERP2 = 1000;
    public static int BOMBIDCOUNTERP3 = 2000;
    public static int BOMBIDCOUNTERP4 = 3000;
    public static int BOMBIDCOUNTERP0 = 4000;
}
