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
    
    //Player 1
    public static Texture p1WalkingDown;
    public static Animation p1WalkingDownAnim;
    public static TextureRegion p1StaticDown;
    
    public static Texture p1WalkingUp;
    public static Animation p1WalkingUpAnim;
    public static TextureRegion p1StaticUp;
    
    public static Texture p1WalkingRight;
    public static Animation p1WalkingRightAnim;
    public static TextureRegion p1StaticRight;
    
    public static Texture p1WalkingLeft;
    public static Animation p1WalkingLeftAnim;
    public static TextureRegion p1StaticLeft;

    
    //Player 2
    public static Texture p2WalkingDown;
    public static Animation p2WalkingDownAnim;
    public static TextureRegion p2StaticDown;
    
    public static Texture p2WalkingUp;
    public static Animation p2WalkingUpAnim;
    public static TextureRegion p2StaticUp;
    
    public static Texture p2WalkingRight;
    public static Animation p2WalkingRightAnim;
    public static TextureRegion p2StaticRight;
    
    public static Texture p2WalkingLeft;
    public static Animation p2WalkingLeftAnim;
    public static TextureRegion p2StaticLeft;

    
    //Player 3
    public static Texture p3WalkingDown;
    public static Animation p3WalkingDownAnim;
    public static TextureRegion p3StaticDown;
    
    public static Texture p3WalkingUp;
    public static Animation p3WalkingUpAnim;
    public static TextureRegion p3StaticUp;
    
    public static Texture p3WalkingRight;
    public static Animation p3WalkingRightAnim;
    public static TextureRegion p3StaticRight;
    
    public static Texture p3WalkingLeft;
    public static Animation p3WalkingLeftAnim;
    public static TextureRegion p3StaticLeft;
    
    
    //Player 4
    public static Texture p4WalkingDown;
    public static Animation p4WalkingDownAnim;
    public static TextureRegion p4StaticDown;
    
    public static Texture p4WalkingUp;
    public static Animation p4WalkingUpAnim;
    public static TextureRegion p4StaticUp;
    
    public static Texture p4WalkingRight;
    public static Animation p4WalkingRightAnim;
    public static TextureRegion p4StaticRight;
    
    public static Texture p4WalkingLeft;
    public static Animation p4WalkingLeftAnim;
    public static TextureRegion p4StaticLeft;
    
    
    public static Texture loadTexture (String file) 
    {
	return new Texture(Gdx.files.internal(file));
    }
    
    public static void load() 
    {
        //TextureRegion(texture, int cutX, int cutY, int width, int height)
        
        /*---------------------------------PLAYER 1---------------------------------------*/
        p1WalkingDown = loadTexture("data/player1/walking-down.png");
        p1WalkingDownAnim = new Animation(0.3f, new TextureRegion(p1WalkingDown, 0, 0, 18, 22), new TextureRegion(p1WalkingDown, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingDown, 36, 0, 18, 22), new TextureRegion(p1WalkingDown, 54, 0, 18, 22));
        p1StaticDown = new TextureRegion(p1WalkingDown, 0, 0, 18, 22);
        
        
        p1WalkingUp = loadTexture("data/player1/walking-up.png");
        p1WalkingUpAnim = new Animation(0.3f, new TextureRegion(p1WalkingUp, 0, 0, 18, 22), new TextureRegion(p1WalkingUp, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingUp, 36, 0, 18, 22), new TextureRegion(p1WalkingUp, 54, 0, 18, 22));
        p1StaticUp = new TextureRegion(p1WalkingUp, 0, 0, 18, 22);
        
        
        p1WalkingRight = loadTexture("data/player1/walking-right.png");
        p1WalkingRightAnim = new Animation(0.3f, new TextureRegion(p1WalkingRight, 0, 0, 17, 22), new TextureRegion(p1WalkingRight, 17, 0, 17, 22), 
				new TextureRegion(p1WalkingRight, 34, 0, 17, 22), new TextureRegion(p1WalkingRight, 51, 0, 17, 22));
        p1StaticRight = new TextureRegion(p1WalkingRight, 0, 0, 17, 22);
        
        
        p1WalkingLeft = loadTexture("data/player1/walking-left.png");
        p1WalkingLeftAnim = new Animation(0.3f, new TextureRegion(p1WalkingLeft, 0, 0, 17, 22), new TextureRegion(p1WalkingLeft, 17, 0, 17, 22), 
				new TextureRegion(p1WalkingLeft, 34, 0, 17, 22), new TextureRegion(p1WalkingLeft, 51, 0, 17, 22));
        p1StaticLeft = new TextureRegion(p1WalkingLeft, 0, 0, 17, 22);
        
        
        /*---------------------------------PLAYER 2---------------------------------------*/
        p2WalkingDown = loadTexture("data/player2/walking-down.png");
        p2WalkingDownAnim = new Animation(0.3f, new TextureRegion(p2WalkingDown, 0, 0, 18, 22), new TextureRegion(p2WalkingDown, 18, 0, 18, 22), 
				new TextureRegion(p2WalkingDown, 36, 0, 18, 22), new TextureRegion(p2WalkingDown, 54, 0, 18, 22));
        p2StaticDown = new TextureRegion(p2WalkingDown, 0, 0, 18, 22);
        
        
        p2WalkingUp = loadTexture("data/player2/walking-up.png");
        p2WalkingUpAnim = new Animation(0.3f, new TextureRegion(p2WalkingUp, 0, 0, 18, 22), new TextureRegion(p2WalkingUp, 18, 0, 18, 22), 
				new TextureRegion(p2WalkingUp, 36, 0, 18, 22), new TextureRegion(p2WalkingUp, 54, 0, 18, 22));
        p2StaticUp = new TextureRegion(p2WalkingUp, 0, 0, 18, 22);
        
        
        p2WalkingRight = loadTexture("data/player2/walking-right.png");
        p2WalkingRightAnim = new Animation(0.3f, new TextureRegion(p2WalkingRight, 0, 0, 17, 22), new TextureRegion(p2WalkingRight, 17, 0, 17, 22), 
				new TextureRegion(p2WalkingRight, 34, 0, 17, 22), new TextureRegion(p2WalkingRight, 51, 0, 17, 22));
        p2StaticRight = new TextureRegion(p2WalkingRight, 0, 0, 17, 22);
        
        
        p2WalkingLeft = loadTexture("data/player2/walking-left.png");
        p2WalkingLeftAnim = new Animation(0.3f, new TextureRegion(p2WalkingLeft, 0, 0, 17, 22), new TextureRegion(p2WalkingLeft, 17, 0, 17, 22), 
				new TextureRegion(p2WalkingLeft, 34, 0, 17, 22), new TextureRegion(p2WalkingLeft, 51, 0, 17, 22));
        p2StaticLeft = new TextureRegion(p2WalkingLeft, 0, 0, 17, 22);
    }
}
