/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdx.bomberman.Constants;

/**
 *
 * @author qubasa
 */
public class TextureManager 
{
    //General Menu
    public static FreeTypeFontGenerator menuFont;
    public static Texture menuBackground;
    public static Texture hostBackground;
    public static Texture startBackground;
    
    // Standard bomb button
    public static Skin skin;
    public static TextureAtlas textureAtlas;
    
    // Standard dynamite button
    public static Skin dynamiteSkin;
    public static TextureAtlas dynamiteAtlas;
    
    // Round button
    public static Skin roundSkin;
    public static TextureAtlas roundAtlas;
    
    // Back button
    public static Skin backSkin;
    public static TextureAtlas backAtlas;
    
    // Settings button
    public static Skin settingsSkin;
    public static TextureAtlas settingsAtlas;
    
    // Winner Screen
    public static Texture playerMarker;
    
    /*-------------------HUD MAIN PLAYER--------------------*/
    // Life
    public static Texture hudCounterFullLive;
    public static Texture hudCounterTwoThirdLive;
    public static Texture hudCounterOneThirdLive;
    public static Texture hudCounterNoLive;
    public static Texture hudCounterFourLive;
    public static Texture hudCounterFiveLive;
    public static Texture hudCounterSixLive;
    
    //Bomb inventory
    public static Texture hudInventory;
    public static TextureRegion hudInventoryHiglighted1;
    public static TextureRegion hudInventoryHiglighted2;
    public static TextureRegion hudInventoryHiglighted3;
    public static TextureRegion hudInventoryHiglighted4;
    public static TextureRegion hudInventoryHiglighted5;
    public static TextureRegion hudInventoryHiglighted6;
    public static TextureRegion hudInventoryHiglighted7;
    public static TextureRegion hudInventoryHiglighted8;
    public static TextureRegion hudInventoryHiglighted9;
    
    /*-------------------END--------------------*/
    //Items
    public static Texture speedup;
    public static TextureRegion speedUp;
    
    public static Texture bombup;
    public static TextureRegion bombUp;
    
    public static Texture rangeup;
    public static TextureRegion rangeUp;
    
    public static Texture cubicRangeup;
    public static TextureRegion cubicRangeUp;
    
    public static Texture coin;
    public static Animation coinAnim;
    
    public static Texture coinbag;
    public static TextureRegion coinBag;
    
    public static Texture heart;
    public static TextureRegion yellowHeart;
    
    public static Texture lifeup;
    public static TextureRegion lifeUp;
    
    public static Texture tomb;
    public static TextureRegion tombstone;
    
    //Player 1 Walking
    public static Texture p1WalkingDown;
    public static Animation p1WalkingDownAnim;
    
    public static Texture p1WalkingUp;
    public static Animation p1WalkingUpAnim;
    
    public static Texture p1WalkingRight;
    public static Animation p1WalkingRightAnim;
    
    public static Texture p1WalkingLeft;
    public static Animation p1WalkingLeftAnim;
    
    
    //Player 2 Walking
    public static Texture p2WalkingDown;
    public static Animation p2WalkingDownAnim;
    
    public static Texture p2WalkingUp;
    public static Animation p2WalkingUpAnim;
    
    public static Texture p2WalkingRight;
    public static Animation p2WalkingRightAnim;
    
    public static Texture p2WalkingLeft;
    public static Animation p2WalkingLeftAnim;

    
    //Player 3 Walking 
    public static Texture p3WalkingDown;
    public static Animation p3WalkingDownAnim;

    public static Texture p3WalkingUp;
    public static Animation p3WalkingUpAnim;

    public static Texture p3WalkingRight;
    public static Animation p3WalkingRightAnim;

    public static Texture p3WalkingLeft;
    public static Animation p3WalkingLeftAnim;

    
    //Player 4 Walking
    public static Texture p4WalkingDown;
    public static Animation p4WalkingDownAnim;

    public static Texture p4WalkingUp;
    public static Animation p4WalkingUpAnim;

    public static Texture p4WalkingRight;
    public static Animation p4WalkingRightAnim;

    public static Texture p4WalkingLeft;
    public static Animation p4WalkingLeftAnim;
    
    
    //Map Textures
    public static Texture nullBlock;
    public static TextureRegion emptyBlock;
    
    
    //Normal Bomb Animation Player 1
    public static Texture p1NormalBomb;
    public static Animation p1NormalBombAnim;
    
    
    //Normal Bomb Animation Player 2
    public static Texture p2NormalBomb;
    public static Animation p2NormalBombAnim;
    
    
    //Normal Bomb Animation Player 3
    public static Texture p3NormalBomb;
    public static Animation p3NormalBombAnim;
    
    
    //Normal Bomb Animation Player 4
    public static Texture p4NormalBomb;
    public static Animation p4NormalBombAnim;
    
    
    //Explosion effect Player 1
    public static Texture p1Explosion;
    public static TextureRegion p1ExplosionYMiddle;
    public static TextureRegion p1ExplosionXMiddle;
    public static TextureRegion p1ExplosionCenter;
    public static TextureRegion p1ExplosionCubic;
    
    public static TextureRegion p1ExplosionDownEnd;
    public static TextureRegion p1ExplosionUpEnd;
    public static TextureRegion p1ExplosionRightEnd;
    public static TextureRegion p1ExplosionLeftEnd;
    
    
    //Explosion effect Player 2
    public static Texture p2Explosion;
    public static TextureRegion p2ExplosionYMiddle;
    public static TextureRegion p2ExplosionXMiddle;
    public static TextureRegion p2ExplosionCenter;
    public static TextureRegion p2ExplosionCubic;
    
    public static TextureRegion p2ExplosionDownEnd;
    public static TextureRegion p2ExplosionUpEnd;
    public static TextureRegion p2ExplosionRightEnd;
    public static TextureRegion p2ExplosionLeftEnd;
    
    
    //Explosion effect Player 3
    public static Texture p3Explosion;
    public static TextureRegion p3ExplosionYMiddle;
    public static TextureRegion p3ExplosionXMiddle;
    public static TextureRegion p3ExplosionCenter;
    public static TextureRegion p3ExplosionCubic;
    
    public static TextureRegion p3ExplosionDownEnd;
    public static TextureRegion p3ExplosionUpEnd;
    public static TextureRegion p3ExplosionRightEnd;
    public static TextureRegion p3ExplosionLeftEnd;
    
    
    //Explosion effect Player 4
    public static Texture p4Explosion;
    public static TextureRegion p4ExplosionYMiddle;
    public static TextureRegion p4ExplosionXMiddle;
    public static TextureRegion p4ExplosionCenter;
    public static TextureRegion p4ExplosionCubic;
    
    public static TextureRegion p4ExplosionDownEnd;
    public static TextureRegion p4ExplosionUpEnd;
    public static TextureRegion p4ExplosionRightEnd;
    public static TextureRegion p4ExplosionLeftEnd;
    
    // Dynamite
    public static Texture dynamite;
    public static Animation dynamiteAnim1;
    public static Animation dynamiteAnim2;
    public static Animation dynamiteAnim3;
    
    // Infinity
    public static Texture infinity;
    public static Animation infinityAnim;
    
    // X3
    public static Texture x3;
    public static Animation x3Anim;
    
    // Barrel
    public static Texture barrel;
    public static Animation barrelAnim1;
    public static Animation barrelAnim2;
    public static Animation barrelAnim3;
    
    // Black hole bomb
    public static Texture blackHole;
    public static Animation blackHoleAnim;
    public static Texture blackHoleCompressed;
    public static Animation blackHoleCompressedAnim;
    public static Texture blackHoleActivationBomb;
    public static Animation blackHoleActivationBombAnim;
    
    // Mine Bomb
    public static Texture p1MineBomb;
    public static Animation p1MineBombAnim;
    
    // Turret
    public static Texture turret;
    public static Animation turretAnimRight;
    public static Animation turretAnimUp;
    public static Animation turretAnimLeft;
    public static Animation turretAnimDown;
    
    
    //Remote Bomb Animation Player 1
    public static Texture p1RemoteBomb;
    public static Animation p1RemoteBombAnim;
    
    
    //Remote Bomb Animation Player 2
    public static Texture p2RemoteBomb;
    public static Animation p2RemoteBombAnim;
    
    
    //Remote Bomb Animation Player 3
    public static Texture p3RemoteBomb;
    public static Animation p3RemoteBombAnim;
    
    
    //Remote Bomb Animation Player 4
    public static Texture p4RemoteBomb;
    public static Animation p4RemoteBombAnim;

    /*-------------------SPECIAL BLOCKS--------------------*/
    public static Texture poisonGas;
    public static Animation poisonGasAnim;

    public static void load() 
    {

        /*---------------------------------SPECIAL BLOCKS---------------------------------*/
        poisonGas = loadTexture("blocks/gas_explosion.png");
        poisonGasAnim = new Animation(0.3f, new TextureRegion(poisonGas,0,0,32,32));


        /*---------------------------------GENERAL MENU---------------------------------*/
        menuFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/press-start/prstartk.ttf"));
        menuBackground = loadTexture("menu/menu.png");
        hostBackground = loadTexture("menu/menu-host.png");
        startBackground = loadTexture("menu/start_picture.png");        
        
        // Winner screen
        playerMarker = loadTexture("other/playerMarker.png");
        
        // Standard bomb button
        textureAtlas = new TextureAtlas(Gdx.files.internal("button/button.pack"));
        skin = new Skin(Gdx.files.internal("menu/uiskin.json"));
        skin.addRegions(textureAtlas);
        
        // Standard dynamite button
        dynamiteAtlas = new TextureAtlas(Gdx.files.internal("button/dynamite-button.pack"));
        dynamiteSkin = new Skin(Gdx.files.internal("menu/uiskin.json"));
        dynamiteSkin.addRegions(dynamiteAtlas);
        
        // Round button
        roundAtlas = new TextureAtlas(Gdx.files.internal("button/round-button.pack"));
        roundSkin = new Skin(Gdx.files.internal("menu/uiskin.json"));
        roundSkin.addRegions(roundAtlas);
        
        // Back button
        backAtlas = new TextureAtlas(Gdx.files.internal("button/back-button.pack"));
        backSkin = new Skin(Gdx.files.internal("menu/uiskin.json"));
        backSkin.addRegions(backAtlas);
        
        // Back button
        settingsAtlas = new TextureAtlas(Gdx.files.internal("button/settings-button.pack"));
        settingsSkin = new Skin(Gdx.files.internal("menu/uiskin.json"));
        settingsSkin.addRegions(settingsAtlas);
        
        /*---------------------------------HUD---------------------------------*/
        hudCounterFullLive = loadTexture("hud/hud2_full_live.png");
        hudCounterTwoThirdLive = loadTexture("hud/hud2_two_third_live.png");
        hudCounterOneThirdLive = loadTexture("hud/hud2_one_third_live.png");
        hudCounterNoLive = loadTexture("hud/hud2_no_live.png");
        hudCounterFourLive = loadTexture("hud/hud2_4_live.png");
        hudCounterFiveLive = loadTexture("hud/hud2_5_live.png");
        hudCounterSixLive = loadTexture("hud/hud2_6_live.png");
        
        // Bomb Inventory
        hudInventory = loadTexture("hud/bomb_hud2.png");
        hudInventoryHiglighted1 = new TextureRegion(hudInventory, 0,0, 280, 32);
        hudInventoryHiglighted2 = new TextureRegion(hudInventory, 0,40, 280, 32);
        hudInventoryHiglighted3 = new TextureRegion(hudInventory, 0,81, 280, 32);
        hudInventoryHiglighted4 = new TextureRegion(hudInventory, 0,121, 280, 32);
        hudInventoryHiglighted5 = new TextureRegion(hudInventory, 0,162, 280, 32);
        hudInventoryHiglighted6 = new TextureRegion(hudInventory, 0,202, 280, 32);
        hudInventoryHiglighted7 = new TextureRegion(hudInventory, 0,242, 280, 32);
        hudInventoryHiglighted8 = new TextureRegion(hudInventory, 0,283, 280, 32);
        hudInventoryHiglighted9 = new TextureRegion(hudInventory, 0,323, 280, 32);
        
        /*------------------------------ITEMS--------------------------------*/
        float coinAnimTime = 0.25f;
        
        speedup = loadTexture("items/speed_upgrade.png");
        speedUp = new TextureRegion(speedup,0,0,32,32);
        
        bombup = loadTexture("items/bomb_upgrade.png");
        bombUp = new TextureRegion(bombup,0,0,32,32);
        
        rangeup = loadTexture("items/range_upgrade.png");
        rangeUp = new TextureRegion(rangeup,0,0,32,32);
        
        cubicRangeup = loadTexture("items/cubicRange_upgrade.png");
        cubicRangeUp = new TextureRegion(cubicRangeup,0,0,32,32);
        
        coin = loadTexture("items/coin.png");
        coinAnim = new Animation(coinAnimTime, new TextureRegion(coin,5,0,28,32), new TextureRegion(coin,45,0,20,32), new TextureRegion(coin,26,0,20,32), new TextureRegion(coin,45,0,20,32), new TextureRegion(coin,26,0,20,32));
        
        coinbag = loadTexture("items/coin_bags.png");
        coinBag = new TextureRegion(coinbag,0,0,32,32);
        
        lifeup = loadTexture("items/liveUp.png");
        lifeUp = new TextureRegion(lifeup,0,0,32,32);
        
        heart = loadTexture("items/yellowheart.png");
        yellowHeart = new TextureRegion(heart,0,0,32,32);
        
        tomb = loadTexture("items/tomb_stone.png");
        tombstone = new TextureRegion(tomb,0,0,32,32);
        
        
        /*---------------------------------PLAYER 1---------------------------------------*/
        float WalkingAnimTime = 0.25f;
        
        p1WalkingDown = loadTexture("players/player1/walking-down.png");
        p1WalkingDownAnim = new Animation(WalkingAnimTime, new TextureRegion(p1WalkingDown, 0, 0, 18, 22), new TextureRegion(p1WalkingDown, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingDown, 36, 0, 18, 22), new TextureRegion(p1WalkingDown, 54, 0, 18, 22));

        
        
        p1WalkingUp = loadTexture("players/player1/walking-up.png");
        p1WalkingUpAnim = new Animation(WalkingAnimTime, new TextureRegion(p1WalkingUp, 0, 0, 18, 22), new TextureRegion(p1WalkingUp, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingUp, 36, 0, 18, 22), new TextureRegion(p1WalkingUp, 54, 0, 18, 22));

        
        
        p1WalkingRight = loadTexture("players/player1/walking-right.png");
        p1WalkingRightAnim = new Animation(WalkingAnimTime, new TextureRegion(p1WalkingRight, 0, 0, 17, 22), new TextureRegion(p1WalkingRight, 17, 0, 17, 22), 
				new TextureRegion(p1WalkingRight, 34, 0, 17, 22), new TextureRegion(p1WalkingRight, 51, 0, 17, 22));

        
        
        p1WalkingLeft = loadTexture("players/player1/walking-left.png");
        p1WalkingLeftAnim = new Animation(WalkingAnimTime, new TextureRegion(p1WalkingLeft, 0, 0, 17, 22), new TextureRegion(p1WalkingLeft, 17, 0, 17, 22), 
				new TextureRegion(p1WalkingLeft, 34, 0, 17, 22), new TextureRegion(p1WalkingLeft, 51, 0, 17, 22));

        /*---------------------------------PLAYER 2---------------------------------------*/
        p2WalkingDown = loadTexture("players/player2/walking-down.png");
        p2WalkingDownAnim = new Animation(WalkingAnimTime, new TextureRegion(p2WalkingDown, 0, 0, 18, 22), new TextureRegion(p2WalkingDown, 18, 0, 18, 22), 
				new TextureRegion(p2WalkingDown, 36, 0, 18, 22), new TextureRegion(p2WalkingDown, 54, 0, 18, 22));
 
        
        
        p2WalkingUp = loadTexture("players/player2/walking-up.png");
        p2WalkingUpAnim = new Animation(WalkingAnimTime, new TextureRegion(p2WalkingUp, 0, 0, 18, 22), new TextureRegion(p2WalkingUp, 18, 0, 18, 22), 
				new TextureRegion(p2WalkingUp, 36, 0, 18, 22), new TextureRegion(p2WalkingUp, 54, 0, 18, 22));

        
        
        p2WalkingRight = loadTexture("players/player2/walking-right.png");
        p2WalkingRightAnim = new Animation(WalkingAnimTime, new TextureRegion(p2WalkingRight, 0, 0, 17, 22), new TextureRegion(p2WalkingRight, 17, 0, 17, 22), 
				new TextureRegion(p2WalkingRight, 34, 0, 17, 22), new TextureRegion(p2WalkingRight, 51, 0, 17, 22));

        
        
        p2WalkingLeft = loadTexture("players/player2/walking-left.png");
        p2WalkingLeftAnim = new Animation(WalkingAnimTime, new TextureRegion(p2WalkingLeft, 0, 0, 17, 22), new TextureRegion(p2WalkingLeft, 17, 0, 17, 22), 
				new TextureRegion(p2WalkingLeft, 34, 0, 17, 22), new TextureRegion(p2WalkingLeft, 51, 0, 17, 22));

         /*---------------------------------PLAYER 3---------------------------------------*/
        p3WalkingDown = loadTexture("players/player3/walking-down.png");
        p3WalkingDownAnim = new Animation(WalkingAnimTime, new TextureRegion(p3WalkingDown, 0, 0, 18, 22), new TextureRegion(p3WalkingDown, 18, 0, 18, 22), 
				new TextureRegion(p3WalkingDown, 36, 0, 18, 22), new TextureRegion(p3WalkingDown, 54, 0, 18, 22));

        
        
        p3WalkingUp = loadTexture("players/player3/walking-up.png");
        p3WalkingUpAnim = new Animation(WalkingAnimTime, new TextureRegion(p3WalkingUp, 0, 0, 18, 22), new TextureRegion(p3WalkingUp, 18, 0, 18, 22), 
				new TextureRegion(p3WalkingUp, 36, 0, 18, 22), new TextureRegion(p3WalkingUp, 54, 0, 18, 22));

        
        
        p3WalkingRight = loadTexture("players/player3/walking-right.png");
        p3WalkingRightAnim = new Animation(WalkingAnimTime, new TextureRegion(p3WalkingRight, 0, 0, 17, 22), new TextureRegion(p3WalkingRight, 17, 0, 17, 22), 
				new TextureRegion(p3WalkingRight, 34, 0, 17, 22), new TextureRegion(p3WalkingRight, 51, 0, 17, 22));

        
        
        p3WalkingLeft = loadTexture("players/player3/walking-left.png");
        p3WalkingLeftAnim = new Animation(WalkingAnimTime, new TextureRegion(p3WalkingLeft, 0, 0, 17, 22), new TextureRegion(p3WalkingLeft, 17, 0, 17, 22), 
				new TextureRegion(p3WalkingLeft, 34, 0, 17, 22), new TextureRegion(p3WalkingLeft, 51, 0, 17, 22));

     /*---------------------------------PLAYER 4---------------------------------------*/
        p4WalkingDown = loadTexture("players/player4/walking-down.png");
        p4WalkingDownAnim = new Animation(WalkingAnimTime, new TextureRegion(p4WalkingDown, 0, 0, 18, 22), new TextureRegion(p4WalkingDown, 18, 0, 18, 22), 
				new TextureRegion(p4WalkingDown, 36, 0, 18, 22), new TextureRegion(p4WalkingDown, 54, 0, 18, 22));

        
        
        p4WalkingUp = loadTexture("players/player4/walking-up.png");
        p4WalkingUpAnim = new Animation(WalkingAnimTime, new TextureRegion(p4WalkingUp, 0, 0, 18, 22), new TextureRegion(p4WalkingUp, 18, 0, 18, 22), 
				new TextureRegion(p4WalkingUp, 36, 0, 18, 22), new TextureRegion(p4WalkingUp, 54, 0, 18, 22));

        
        
        p4WalkingRight = loadTexture("players/player4/walking-right.png");
        p4WalkingRightAnim = new Animation(WalkingAnimTime, new TextureRegion(p4WalkingRight, 0, 0, 17, 22), new TextureRegion(p4WalkingRight, 17, 0, 17, 22), 
				new TextureRegion(p4WalkingRight, 34, 0, 17, 22), new TextureRegion(p4WalkingRight, 51, 0, 17, 22));

        
        
        p4WalkingLeft = loadTexture("players/player4/walking-left.png");
        p4WalkingLeftAnim = new Animation(WalkingAnimTime, new TextureRegion(p4WalkingLeft, 0, 0, 17, 22), new TextureRegion(p4WalkingLeft, 17, 0, 17, 22), 
				new TextureRegion(p4WalkingLeft, 34, 0, 17, 22), new TextureRegion(p4WalkingLeft, 51, 0, 17, 22));
        
        
        /*---------------------------------BLOCK TEXTURES---------------------------------------*/
        nullBlock = loadTexture("blocks/empty.png");
        emptyBlock = new TextureRegion(nullBlock, 0, 0, 32, 32);
        
        
        /*---------------------------------NORMAL BOMB P1---------------------------------------*/
        p1NormalBomb = loadTexture("bombs/normal_bomb-anim_p1.png");
        p1NormalBombAnim = new Animation(0.35f, new TextureRegion(p1NormalBomb, 0, 0, 34, 34), new TextureRegion(p1NormalBomb, 34, 0, 32, 32),
                                new TextureRegion(p1NormalBomb, 67, 0, 30, 32));
        
        /*--------------------------------REMOTE BOMB P1-----------------------------------*/
        p1RemoteBomb = loadTexture("bombs/remote_bomb_p1.png");
        p1RemoteBombAnim = new Animation(0.35f, new TextureRegion(p1RemoteBomb, 0, 0, 34, 34), new TextureRegion(p1RemoteBomb, 34, 0, 32, 32),
                                new TextureRegion(p1RemoteBomb, 67, 0, 30, 32));
        
        
        /*---------------------------------P1 EXPLOSION---------------------------------*/
        p1Explosion = loadTexture("bombs/explosion_p1.png");
        p1ExplosionYMiddle = new TextureRegion(p1Explosion, 0, 32, 32, 32);
        p1ExplosionCenter = new TextureRegion(p1Explosion, 128, 0, 32, 32);
        p1ExplosionXMiddle = new TextureRegion(p1Explosion, 32, 32, 32, 32);
        p1ExplosionCubic = new TextureRegion(p1Explosion,129,33,32,32);
        
        p1ExplosionDownEnd = new TextureRegion(p1Explosion, 64, 0, 32, 32);
        p1ExplosionUpEnd = new TextureRegion(p1Explosion, 0, 0, 32, 32);
        p1ExplosionLeftEnd = new TextureRegion(p1Explosion, 32, 0, 32, 32);
        p1ExplosionRightEnd = new TextureRegion(p1Explosion, 96, 0, 32, 32);
        
        
        /*---------------------------------NORMAL BOMB P2---------------------------------------*/
        p2NormalBomb = loadTexture("bombs/normal_bomb-anim_p2.png");
        p2NormalBombAnim = new Animation(0.35f, new TextureRegion(p2NormalBomb, 0, 0, 34, 34), new TextureRegion(p2NormalBomb, 34, 0, 32, 32),
                                new TextureRegion(p2NormalBomb, 67, 0, 30, 32));
        
        /*--------------------------------REMOTE BOMB P2-----------------------------------*/
        p2RemoteBomb = loadTexture("bombs/remote_bomb_p2.png");
        p2RemoteBombAnim = new Animation(0.35f, new TextureRegion(p2RemoteBomb, 0, 0, 34, 34), new TextureRegion(p2RemoteBomb, 34, 0, 32, 32),
                                new TextureRegion(p2RemoteBomb, 67, 0, 30, 32));
        
        /*---------------------------------P2 EXPLOSION---------------------------------*/
        p2Explosion = loadTexture("bombs/explosion_p2.png");
        p2ExplosionYMiddle = new TextureRegion(p2Explosion, 0, 32, 32, 32);
        p2ExplosionCenter = new TextureRegion(p2Explosion, 128, 0, 32, 32);
        p2ExplosionXMiddle = new TextureRegion(p2Explosion, 32, 32, 32, 32);
        p2ExplosionCubic = new TextureRegion(p2Explosion,129,33,32,32);
        
        p2ExplosionDownEnd = new TextureRegion(p2Explosion, 64, 0, 32, 32);
        p2ExplosionUpEnd = new TextureRegion(p2Explosion, 0, 0, 32, 32);
        p2ExplosionLeftEnd = new TextureRegion(p2Explosion, 32, 0, 32, 32);
        p2ExplosionRightEnd = new TextureRegion(p2Explosion, 96, 0, 32, 32);
        
        
        /*---------------------------------NORMAL BOMB P3---------------------------------------*/
        p3NormalBomb = loadTexture("bombs/normal_bomb-anim_p3.png");
        p3NormalBombAnim = new Animation(0.35f, new TextureRegion(p3NormalBomb, 0, 0, 34, 34), new TextureRegion(p3NormalBomb, 34, 0, 32, 32),
                                new TextureRegion(p3NormalBomb, 67, 0, 30, 32));
        
        /*--------------------------------REMOTE BOMB P3-----------------------------------*/
        p3RemoteBomb = loadTexture("bombs/remote_bomb_p3.png");
        p3RemoteBombAnim = new Animation(0.35f, new TextureRegion(p3RemoteBomb, 0, 0, 34, 34), new TextureRegion(p3RemoteBomb, 34, 0, 32, 32),
                                new TextureRegion(p3RemoteBomb, 67, 0, 30, 32));
        
        /*---------------------------------P3 EXPLOSION---------------------------------*/
        p3Explosion = loadTexture("bombs/explosion_p3.png");
        p3ExplosionYMiddle = new TextureRegion(p3Explosion, 0, 32, 32, 32);
        p3ExplosionCenter = new TextureRegion(p3Explosion, 128, 0, 32, 32);
        p3ExplosionXMiddle = new TextureRegion(p3Explosion, 32, 32, 32, 32);
        p3ExplosionCubic = new TextureRegion(p3Explosion,129,33,32,32);
        
        p3ExplosionDownEnd = new TextureRegion(p3Explosion, 64, 0, 32, 32);
        p3ExplosionUpEnd = new TextureRegion(p3Explosion, 0, 0, 32, 32);
        p3ExplosionLeftEnd = new TextureRegion(p3Explosion, 32, 0, 32, 32);
        p3ExplosionRightEnd = new TextureRegion(p3Explosion, 96, 0, 32, 32);
        
        
        /*---------------------------------NORMAL BOMB P4---------------------------------------*/
        p4NormalBomb = loadTexture("bombs/normal_bomb-anim_p4.png");
        p4NormalBombAnim = new Animation(0.35f, new TextureRegion(p4NormalBomb, 0, 0, 34, 34), new TextureRegion(p4NormalBomb, 34, 0, 32, 32),
                                new TextureRegion(p4NormalBomb, 67, 0, 30, 32));
        
        /*--------------------------------REMOTE BOMB P4-----------------------------------*/
        p4RemoteBomb = loadTexture("bombs/remote_bomb_p4.png");
        p4RemoteBombAnim = new Animation(0.35f, new TextureRegion(p4RemoteBomb, 0, 0, 34, 34), new TextureRegion(p4RemoteBomb, 34, 0, 32, 32),
                                new TextureRegion(p4RemoteBomb, 67, 0, 30, 32));
        
        /*---------------------------------P4 EXPLOSION---------------------------------*/
        p4Explosion = loadTexture("bombs/explosion_p4.png");
        p4ExplosionYMiddle = new TextureRegion(p4Explosion, 0, 32, 32, 32);
        p4ExplosionCenter = new TextureRegion(p4Explosion, 128, 0, 32, 32);
        p4ExplosionXMiddle = new TextureRegion(p4Explosion, 32, 32, 32, 32);
        p4ExplosionCubic = new TextureRegion(p4Explosion,129,33,32,32);
        
        p4ExplosionDownEnd = new TextureRegion(p4Explosion, 64, 0, 32, 32);
        p4ExplosionUpEnd = new TextureRegion(p4Explosion, 0, 0, 32, 32);
        p4ExplosionLeftEnd = new TextureRegion(p4Explosion, 32, 0, 32, 32);
        p4ExplosionRightEnd = new TextureRegion(p4Explosion, 96, 0, 32, 32);
        

        Constants.PLAYERWIDTH = ((TextureRegion) p1WalkingRightAnim.getKeyFrame(0)).getRegionWidth();
        Constants.PLAYERHEIGHT = ((TextureRegion) p1WalkingRightAnim.getKeyFrame(0)).getRegionHeight();
       
        /*---------------------------------MINE BOMB P1---------------------------------*/
        p1MineBomb = loadTexture("bombs/hiding_mine_p1.png");
        p1MineBombAnim =  new Animation(0.5f, new TextureRegion(p1MineBomb, 0, 0, 33, 32), new TextureRegion(p1MineBomb, 32, 0, 33, 32), new TextureRegion(p1MineBomb, 64, 0, 33, 32));
        
         
        /*-----------------------------SpecialBombs--------------------------------------*/
        float BombAnimTime = 0.7f;
        dynamite = loadTexture("bombs/dynamite_anim.png");
        dynamiteAnim1 = new Animation(BombAnimTime, new TextureRegion(dynamite, 0, 0, 33, 32), new TextureRegion(dynamite, 32, 0, 33, 32), new TextureRegion(dynamite, 64, 0, 33, 32));
        dynamiteAnim2 = new Animation(BombAnimTime, new TextureRegion(dynamite, 0, 32, 33, 32), new TextureRegion(dynamite, 32, 32, 33, 32), new TextureRegion(dynamite, 64, 32, 33, 32));
        dynamiteAnim3 = new Animation(BombAnimTime, new TextureRegion(dynamite, 0, 64, 33, 32), new TextureRegion(dynamite, 32, 64, 33, 32), new TextureRegion(dynamite, 64, 64, 33, 32));
        
        infinity = loadTexture("bombs/infinity_anim.png");
        infinityAnim = new Animation(BombAnimTime, new TextureRegion(infinity, 0, 0, 33, 32), new TextureRegion(infinity, 32, 0, 33, 32), new TextureRegion(infinity, 64, 0, 33, 32));
        
        x3 = loadTexture("bombs/x3_anim.png");
        x3Anim = new Animation(BombAnimTime, new TextureRegion(x3, 0, 0, 33, 32), new TextureRegion(x3, 32, 0, 33, 32), new TextureRegion(x3, 64, 0, 33, 32));
    
        barrel = loadTexture("bombs/barrel.png");
        barrelAnim1 = new Animation(BombAnimTime,  new TextureRegion(barrel, 0, 0, 33, 32), new TextureRegion(barrel, 0, 0, 33, 32), new TextureRegion(barrel, 0, 0, 33, 32));
        barrelAnim2 = new Animation(BombAnimTime,  new TextureRegion(barrel, 32, 0, 33, 32), new TextureRegion(barrel, 32, 0, 33, 32), new TextureRegion(barrel, 32, 0, 33, 32));
        barrelAnim3 = new Animation(BombAnimTime,  new TextureRegion(barrel, 64, 0, 33, 32), new TextureRegion(barrel, 64, 0, 33, 32), new TextureRegion(barrel, 64, 0, 33, 32));
    
        turret = loadTexture("bombs/turret_anim.png");
        turretAnimRight = new Animation(BombAnimTime, new TextureRegion(turret, 0, 0, 32, 32));
        turretAnimUp = new Animation(BombAnimTime, new TextureRegion(turret, 32, 0, 32, 32));
        turretAnimLeft = new Animation(BombAnimTime, new TextureRegion(turret, 64, 0, 32, 32));
        turretAnimDown = new Animation(BombAnimTime, new TextureRegion(turret, 96, 0, 32, 32));
        
        blackHoleActivationBomb = loadTexture("bombs/black_hole_activation_bomb.png");
        blackHoleActivationBombAnim = new Animation(0.35f, new TextureRegion(blackHoleActivationBomb, 0, 0, 34, 34), new TextureRegion(blackHoleActivationBomb, 34, 0, 32, 32), new TextureRegion(blackHoleActivationBomb, 67, 0, 30, 32));
        
        blackHole = loadTexture("bombs/black_hole_anim.png");
        blackHoleAnim = new Animation(BombAnimTime, new TextureRegion(blackHole, 0, 0, 32, 32), new TextureRegion(blackHole, 34, 0, 32, 32), new TextureRegion(blackHole, 68, 0, 32, 32), new TextureRegion(blackHole, 102, 0, 32, 32));

        blackHoleCompressed = loadTexture("bombs/black_hole_compressed_single.png");
        blackHoleCompressedAnim = new Animation(BombAnimTime, new TextureRegion(blackHoleCompressed, 0, 0, 32, 32), new TextureRegion(blackHoleCompressed, 32, 0, 32, 32));
    }
    
    
    /**-------------------TEXTURE FUNCTIONS-------------------**/
    /**
     * loads texture out of assets folder
     * @param file
     * @return Texture
     */
    public static Texture loadTexture (String file) 
    {
	return new Texture(Gdx.files.internal(file));
    }
}
