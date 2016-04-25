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
    int cellX, cellY;
    
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
        this.map = map;
        
        //Get cell positon
        this.cellX = (int) (pos.x / map.getBlockLayer().getTileWidth());
        this.cellY = (int) (pos.y / map.getBlockLayer().getTileHeight());
        
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

            //Create new cell and set its tile texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(getFrame(this.defaultBombAnim)));
            
            //Set bomb into bomb layer
            map.getBombLayer().setCell((int) cellX, (int) cellY, cell);
            
        }else
        {
            System.err.println("Not implementet jet bombId " + bombId);
        }
    }
    
    @Override
    public void update() 
    {
        //Do explosion effect HERE!
        if(timer >= explosionTime)
        {
            this.isExploded = true;
            
            //Override bomb cell with empty cell
            map.getBombLayer().setCell((int) cellX, (int) cellY, new Cell());
            
            explode();
            
        }else
        {
            timer += Constants.DELTATIME;
        }
    }
    
    
    public void explode()
    {
        //Explode on y 
        for(int y=1; y <= explosionRange; y++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionYMiddle));
            
            //Explosion above
            map.getBombLayer().setCell((int) cellX, (int) cellY + y, cell);
            
            //Explosion below
            map.getBombLayer().setCell((int) cellX, (int) cellY - y, cell);
        }
        
        //Explode on x 
        for(int x=1; x <= explosionRange; x++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionXMiddle));
            
            //Explosion right
            map.getBombLayer().setCell((int) cellX +x, (int) cellY, cell);
            
            //Explosion left
            map.getBombLayer().setCell((int) cellX -x, (int) cellY, cell);
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
    
    public String findCell (int X , int Y )
    {
        String cellStatus="null";
        Cell cell = blockLayer.getCell( X , Y );
        
        if(cell != null){
            if(cell.getTile().getProperties().containsKey("undestructible")){
                cellStatus="undestructible";
            }else{
                cellStatus="destructible";
            }
        }
        
        return cellStatus;
        
    }
}
