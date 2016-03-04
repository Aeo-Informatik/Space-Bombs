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

    


    
    
    public static Texture loadTexture (String file) 
    {
	return new Texture(Gdx.files.internal(file));
    }
    
    public static void load () 
    {
        //TextureRegion(texture, int cutX, int cutY, int width, int height)
        
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
        
        
        
    }
}
