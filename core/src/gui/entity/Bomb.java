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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.map.MapManager;

/**
 *
 * @author phinix
 */
public class Bomb extends Entity
{
    //General variables
    private float stateTime;
    private SpriteBatch sb;
    private MapManager map;
    private int playerId ;
    private TiledMapTileLayer blockLayer;
    
    //Bomb settings
    private float timer;
    private int bombId;
    private float explosionTime;
    private int explosionRange;
    private boolean isExploded = false;
    private int range;
    
    //Bomb 1 Animation
    private  Animation b1BurnsAnim;

    //Constructor
    public Bomb(float posX, float posY, Vector2 direction, int bombId, MapManager map,int playerId)
    {
        super(null, new Vector2(posX, posY), direction);
        
        this.playerId = playerId;
        this.blockLayer = map.getBlockLayer();
        this.bombId = bombId;
        
        switch(bombId)
        {
            case 1:

               
                timer=1;
                range=1;
                break;
                
            default:
                System.err.println("ERROR: Wrong bomb id given. Using default bomb 1");
                timer=1;
                range=1;  
        }
        
        this.playerId=playerId;
        
        
        switch(bombId)
        {
            case 1:

                this.explosionTime = 3; // In seconds
                this.explosionRange = 1; // In blocks

                this.b1BurnsAnim = TextureManager.b1BurnsAnim;   
                break;
                
            default:
                System.err.println("ERROR: Wrong bomb number: " + bombId + " in Bomb. Using default b1");
                this.explosionTime = 3;
                this.explosionRange = 1;  
        }   
    }
    

    @Override
    public void render(SpriteBatch sb, float deltaTime)
    {
        sb.draw(getFrame(b1BurnsAnim), pos.x, pos.y);
        
        {
            timer-=(1*deltaTime);
        }
    }
    
    @Override
    public void update() 
    {
        if(timer >= explosionTime)
        {
            this.isExploded = true;
        }else
        {
            timer += Gdx.graphics.getDeltaTime();
            System.out.println(timer);
        }
        
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
    public int getBombId() 
    {
        return bombId;
    }

    public boolean isExploded()
    {
        return this.isExploded;
    }
    
    public void setBombId(int bombId) 
    {
        this.bombId = bombId;
    }

    public float getTimer()
    {
        return timer;
    }

    public void setTimer(float timer) 
    {
        this.timer = timer;
    }

    public int getRange() {
        return explosionRange;
    }

    
    public void explode(){
        
        System.out.println("BombID:" + bombId + " X:" + pos.x + " Y:" + pos.y );
        cellStatus((int)pos.x, (int)pos.y);
        
    }
    
    public int cellStatus(int x, int y){
        Cell cell =  blockLayer.getCell((int)pos.x, (int)pos.y);
        System.out.println("Cell found");
        System.out.println("X:" + x + " Y:" + y);
        int cellStatus=0;
       
            
        
        return cellStatus;
    }


}
