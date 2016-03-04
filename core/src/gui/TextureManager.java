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
    
    public static Animation p1WalkingDownAnim;
    public static Texture p1WalkingDown;
    
    public static Texture p1WalkingUp;
    public static Animation p1WalkingUpAnim;
    
    public static Texture p1WalkingRight;
    public static Animation p1WalkingRightAnim;
    
    public static Texture p1WalkingLeft;
    public static Animation p1WalkingLeftAnim;
    
    public static TextureRegion p1SpawnPosition;
    
    public static Texture loadTexture (String file) 
    {
	return new Texture(Gdx.files.internal(file));
    }
    
    public static void load () 
    {
        p1WalkingDown = loadTexture("data/player1/walking-down.png");
        p1WalkingDownAnim = new Animation(0.3f, new TextureRegion(p1WalkingDown, 0, 0, 18, 22), new TextureRegion(p1WalkingDown, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingDown, 36, 0, 18, 22), new TextureRegion(p1WalkingDown, 54, 0, 18, 22));
        
        
        p1WalkingUp = loadTexture("data/player1/walking-up.png");
        p1WalkingUpAnim = new Animation(0.3f, new TextureRegion(p1WalkingUp, 0, 0, 18, 22), new TextureRegion(p1WalkingUp, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingUp, 36, 0, 18, 22), new TextureRegion(p1WalkingUp, 54, 0, 18, 22));
        
        p1WalkingRight = loadTexture("data/player1/walking-right.png");
        p1WalkingRightAnim = new Animation(0.3f, new TextureRegion(p1WalkingRight, 0, 0, 18, 22), new TextureRegion(p1WalkingRight, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingRight, 36, 0, 18, 22), new TextureRegion(p1WalkingRight, 54, 0, 18, 22));
        
        p1WalkingLeft = loadTexture("data/player1/walking-left.png");
        p1WalkingLeftAnim = new Animation(0.3f, new TextureRegion(p1WalkingLeft, 0, 0, 18, 22), new TextureRegion(p1WalkingLeft, 18, 0, 18, 22), 
				new TextureRegion(p1WalkingLeft, 36, 0, 18, 22), new TextureRegion(p1WalkingLeft, 54, 0, 18, 22));
        
        p1SpawnPosition = new TextureRegion(p1WalkingUp, 0, 0, 18, 22);
    }
}
