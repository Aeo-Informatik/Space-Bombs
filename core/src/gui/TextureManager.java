/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author qubasa
 */
public class TextureManager {
    
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
    
    
    //Bomb P1
    public static Texture defaultBomb;
    public static Animation defaultBombAnim;
    
    
    //Explosion P1
    public static Texture p1Explosion;
    public static TextureRegion p1ExplosionYMiddle;
    public static TextureRegion p1ExplosionYEnd;
    public static TextureRegion p1ExplosionXMiddle;
    public static TextureRegion p1ExplosionXEnd;
    public static TextureRegion p1ExplosionCenter;
    
    
    public static Texture loadTexture (String file) 
    {
	return new Texture(Gdx.files.internal(file));
    }
    
    
    public static void load() 
    {
        //TextureRegion(texture, int cutX, int cutY, int width, int height)
        
        float WalkingAnimTime = 0.25f;
        
        /*---------------------------------PLAYER 1---------------------------------------*/
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
        
        
        /*---------------------------------P1 BOMB---------------------------------------*/
        defaultBomb = loadTexture("bombs/normal-bomb_anim.png");
        defaultBombAnim = new Animation(0.35f, new TextureRegion(defaultBomb, 0, 0, 34, 34), new TextureRegion(defaultBomb, 34, 0, 32, 32),
                                new TextureRegion(defaultBomb, 67, 0, 30, 32));
        
        
        /*---------------------------------P1 EXPLOSION---------------------------------*/
        p1Explosion = loadTexture("bombs/explosion_p1.png");
        p1ExplosionYMiddle = new TextureRegion(p1Explosion, 0, 32, 32, 32);
        p1ExplosionYEnd = new TextureRegion(p1Explosion, 0, 0, 32, 32);
        p1ExplosionCenter = new TextureRegion(p1Explosion, 128, 0, 32, 32);
        p1ExplosionXMiddle = new TextureRegion(p1Explosion, 32, 32, 32, 32);
        p1ExplosionXEnd = new TextureRegion(p1Explosion, 32, 0, 32, 32);
    }
}
