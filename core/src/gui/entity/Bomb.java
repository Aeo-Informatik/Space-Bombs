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
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import gui.Constants;
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
    
    //Bomb 1 Animation
    private  Animation defaultBombAnim;

    //Constructor
    public Bomb(float posX, float posY, Vector2 direction, int bombId, MapManager map,int playerId)
    {
        super(null, new Vector2(posX, posY), direction);
        
        this.playerId = playerId;
        this.blockLayer = map.getBlockLayer();
        this.bombId = bombId;
        
        //Please only use one switch for better practice (less code > more code)
        switch(bombId)
        {
            case 1:
                this.defaultBombAnim = TextureManager.defaultBombAnim;
                explosionRange = 1; // In blocks
                explosionTime = 3; // in seconds
                break;
                
            default:
                System.err.println("ERROR: Wrong bomb number: " + bombId + " in Bomb. Using default b1");
                explosionRange = 1; // In blocks
                explosionTime = 3; // in seconds  
        } 
    }
    

    @Override
    public void render(SpriteBatch sb)
    {
        if(bombId == 1)
        {
            //Do render in cell HERE!
            sb.draw(getFrame(defaultBombAnim), pos.x, pos.y);
            
//            //Get cell positon
//            int cellX = (int) (pos.x / map.getBlockLayer().getTileWidth());
//            int cellY = (int) (pos.y / map.getBlockLayer().getTileHeight());
//            
//            //Create new cell to set it in bomb layer
//            Cell cell = map.getBombLayer().getCell(cellX, cellY);
//            if(cell != null)
//            {
//                cell.setTile(new StaticTiledMapTile(getFrame(this.defaultBombAnim)));
//                map.getBombLayer().setCell((int) cellX, (int) cellY, cell);
//            }
            
        }else
        {
            System.err.println("Not implementet jet bombId " + bombId);
        }
    }
    
    @Override
    public void update() 
    {
        if(timer >= explosionTime)
        {
            //Do explosion effect HERE!
            this.isExploded = true;
            
        }else
        {
            timer += Constants.DELTATIME;
        }
    }
    
    
    private TextureRegion getFrame(Animation animation)
    {
        /* Adds the time elapsed since the last render to the stateTime.*/
        this.stateTime += Constants.DELTATIME; 
        
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

    public int getRange() 
    {
        return explosionRange;
    }

    
    public void explode()
    {
        
        System.out.println("BombID:" + bombId + " X:" + pos.x + " Y:" + pos.y );
        cellStatus((int)pos.x, (int)pos.y);
        
    }
    
    //Tipp: If there is no cell at given position a NullPointerException will be thrown ;)
    public int cellStatus(int x, int y)
    {
        Cell cell =  blockLayer.getCell((int)pos.x, (int)pos.y);
        System.out.println("Cell found");
        System.out.println("X:" + x + " Y:" + y);
        int cellStatus=0;
        
        return cellStatus;
    }


}
