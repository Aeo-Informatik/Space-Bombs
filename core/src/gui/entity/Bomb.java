/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;

/**
 *
 * @author phinix
 */
public class Bomb extends Entity{
    
    private float stateTime;
    //private String lastMovementKeyPressed = "UP";
    private SpriteBatch sb;
    private int bombId=0;
    
 
    private  Animation b1BurnsAnim;
    private  TextureRegion b1StaticBurns;    

    //Constructor
    public Bomb(Vector2 pos, Vector2 direction,int id)
    {
        super(null, pos, direction);
        
        bombId=id;
        switch(bombId)
        {
            case 1:
                this.b1BurnsAnim = TextureManager.b1BurnsAnim;   
                this.b1StaticBurns = TextureManager.b1StaticBurns;
                break;
                
            default:
                System.err.println("ERROR: Wrong bomb number: " + bombId + " in Bomb. Using default b1");
                this.b1BurnsAnim = TextureManager.b1BurnsAnim; 
                this.b1StaticBurns = TextureManager.b1StaticBurns;
        }
    
    }
    

    @Override
    public void render(SpriteBatch sb)
    {
        //Changes the position of the texture 
        pos.add(direction);
        
        if(this.sb == null)
            this.sb = sb;
        
        //movePlayer(sb, "RIGHT");
            
    }
    
    @Override
    public void update() {
        
    }
    
    private TextureRegion getFrame(Animation animation)
    {
        /* Adds the time elapsed since the last render to the stateTime.*/
        this.stateTime += Gdx.graphics.getDeltaTime(); 
        
        /*
        Obtains the current frame. This is given by the animation for the current time. 
        The second variable is the looping. 
        Passing in true, we tell the animation to restart after it reaches the last frame.
        */
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        
        return currentFrame;
    }

    public int getBombId() {
        return bombId;
    }

    public void setBombId(int bombId) {
        this.bombId = bombId;
    }

    public Animation getB1BurnsAnim() {
        return b1BurnsAnim;
    }

    public void setB1BurnsAnim(Animation b1BurnsAnim) {
        this.b1BurnsAnim = b1BurnsAnim;
    }

    public TextureRegion getB1StaticBurns() {
        return b1StaticBurns;
    }

    public void setB1StaticBurns(TextureRegion b1StaticBurns) {
        this.b1StaticBurns = b1StaticBurns;
    }
    
}
