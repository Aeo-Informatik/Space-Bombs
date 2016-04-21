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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.map.MapManager;

/**
 *
 * @author phinix
 */
public class Bomb extends Entity
{
    
    private float stateTime;
    private SpriteBatch sb;
    private int bombId=0;
    private MapManager map;
    private int playerId ;
    private float timer;
    private int range;
    private TiledMapTileLayer blockLayer;
    
    //Bomb Animation
    private  Animation b1BurnsAnim;

    //Constructor
    public Bomb(float posX, float posY, Vector2 direction,int id, MapManager map,int playerId)
    {
        super(null, new Vector2(posX, posY), direction);
        
        this.blockLayer = map.getBlockLayer();
        
        bombId = id;
        
        switch(bombId)
        {
            case 1:
                timer=10;
                range=1;
                break;
                
            default:
                System.err.println("ERROR: Wrong bomb id given. Using default bomb 1");
                timer=10;
                range=1;  
        }
        
        this.playerId=playerId;
        
        
        switch(bombId)
        {
            case 1:
                this.b1BurnsAnim = TextureManager.b1BurnsAnim;   
                break;
                
            default:
                System.err.println("ERROR: Wrong bomb number: " + bombId + " in Bomb. Using default b1");
                this.b1BurnsAnim = TextureManager.b1BurnsAnim; 
        }
    
    }
    

    @Override
    public void render(SpriteBatch sb)
    {
        sb.draw(getFrame(b1BurnsAnim), pos.x, pos.y);
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

    
    /**------------Getter & Setter-------------**/
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

    public float getTimer() {
        return timer;
    }

    public void changeTimer(float timer) {
        this.timer -= timer;
    }

    public int getRange() {
        return range;
    }
    
    public void explode(){
        
        System.out.println("BombID:" + bombId + "X:" + pos.x + "Y:" + pos.y );
        
        ///return cell != null && cell.getTile().getProperties().containsKey("blocked");

    }
  
    

}
