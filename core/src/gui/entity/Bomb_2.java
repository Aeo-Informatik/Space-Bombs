/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity;

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
public class Bomb_2 extends Entity
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
    private float timer2;
    private float explosionTime;
    private int explosionRange;
    private float explosionDuration;
    private boolean isExploded = false;
    
    //Bomb 1 Player 1 Animation
    private  Animation normalBombAnim;

    //Constructor
    public Bomb_2(float posX, float posY, Vector2 direction, MapManager map, int playerId)
    {
        super(null, new Vector2(posX, posY), direction);
        
        //Needed variables
        this.playerId = playerId;
        this.blockLayer = map.getBlockLayer();
        this.map = map;

        
        //Get cell positon
        this.cellX = (int) (pos.x / map.getBlockLayer().getTileWidth());
        this.cellY = (int) (pos.y / map.getBlockLayer().getTileHeight());
        
        //Bomb settings
        this.normalBombAnim = TextureManager.normalBombAnim;
        this.explosionRange = 4; // In blocks
        this.explosionTime = 3; // in seconds
        this.explosionDuration = 0.6f; // in seconds      
    }
    

    @Override
    public void render(SpriteBatch sb)
    {       
        //If time to explode
        if(timer >= explosionTime)
        {
            explode();
            
            //If explosion effect is done
            if(timer2 >= explosionDuration)
            {
                deleteExplosionEffect();
                
                //Object gets delete only set if everything is done.
                this.isExploded = true;
            }else
            {
                timer2 += Constants.DELTATIME;
            }
            
        }else
        {
            //Create new cell and set its animation texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(getFrame(this.normalBombAnim)));
            cell.getTile().getProperties().put("blocked", null);
            
            //Set bomb into bomb layer
            map.getBombLayer().setCell((int) cellX, (int) cellY, cell);
            
            //Add passed to counter
            timer += Constants.DELTATIME;
        }
    }
    
    @Override
    public void update() 
    {

    }
    
    public boolean deleteBlock(int x, int y)
    {
        Cell currentCell = blockLayer.getCell(x , y);
        
        if(currentCell != null)
        {
            //If block is undestructable
            if(currentCell.getTile().getProperties().containsKey("undestructable"))
            {
                return false;
                
            }else
            {
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(TextureManager.emptyBlock));
                
                map.getBlockLayer().setCell( x, y, cell);

            }
        }
        
        // If there is no block 
        return true;
    }
    
    public void deleteExplosionEffect()
    {
        //Create new cell and set texture
        Cell cellCenter = new Cell();
        cellCenter.setTile(new StaticTiledMapTile(TextureManager.emptyBlock));
        
        boolean up = false;
        boolean down = false;
        boolean right = false;
        boolean left = false;
            
        //Explosion center replaces bomb texture
        map.getBombLayer().setCell(cellX, cellY, cellCenter);
        
        //Explode on y 
        for(int y=1; y <= explosionRange; y++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(TextureManager.emptyBlock));

            //Explosion above
            map.getBombLayer().setCell(cellX, cellY + y, cell);
            if(up==false)
                {                    
                    if(findCell(cellX , cellY + y) != "null")
                    {
                        up = true;
                    }
                    deleteBlock(cellX, cellY + y);
                }
            
            
            //Explosion below
            map.getBombLayer().setCell(cellX, cellY - y, cell);
            if(down==false)
                {                    
                    if(findCell(cellX , cellY - y) != "null")
                    {
                        down = true;
                    }
                    deleteBlock(cellX, cellY - y);
                }

        }
        
        for(int x=1; x <= explosionRange; x++)
        {
            //Set cell with middle explosion texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(TextureManager.emptyBlock));

            //Explosion right
            map.getBombLayer().setCell(cellX + x, cellY, cell);
            if(right==false)
                {                    
                    if(findCell(cellX + x , cellY) != "null")
                    {
                        right = true;
                    }
                    deleteBlock(cellX + x, cellY);
                }
            
            //Explosion left
            map.getBombLayer().setCell(cellX - x, cellY, cell);
            if(left==false)
                {                    
                    if(findCell(cellX - x , cellY) != "null")
                    {
                        left = true;
                    }
                    deleteBlock(cellX - x, cellY);
                }

        }
        
    }
    
    public void explode()
    {
        //Create new cell and set texture
        Cell cellCenter = new Cell();
        cellCenter.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionCenter));
        cellCenter.getTile().getProperties().put("deadly", null);
        
        boolean up = false;
        boolean down = false;
        boolean right = false;
        boolean left = false;
        
        //Explosion center, replaces bomb texture
        map.getBombLayer().setCell(cellX, cellY, cellCenter);
        
        //Explode on y 
        for(int y=1; y <= explosionRange; y++)
        {
            if(y != explosionRange) // If not end of explosion
            {
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                if(up==false)
                {
                    if (findCell(cellX, cellY + y)!="null")
                    { 
                        up=true;
                    
                    }
                    map.getBombLayer().setCell(cellX, cellY + y, cell);
                }
                
                if(down==false)
                {
                    if (findCell(cellX, cellY - y)!="null")
                    { 
                        down=true;                    
                    }
                    map.getBombLayer().setCell(cellX, cellY - y, cell);
                }
                
            }else
            {
                //Set end of explosion
                Cell cellUp = new Cell();
                cellUp.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionUpEnd));
                cellUp.getTile().getProperties().put("deadly", null);
                
                if(up==false)
                {                    
                map.getBombLayer().setCell(cellX, cellY + y, cellUp);
                }
                                
                Cell cellDown = new Cell();
                cellDown.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionDownEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                if(down==false)
                {                    
                map.getBombLayer().setCell(cellX, cellY - y, cellDown);
                }
                
            }
        }
        
        //Explode on x 
        for(int x=1; x <= explosionRange; x++)
        {
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                if(right==false)
                {
                    if (findCell(cellX + x, cellY)!="null")
                    { 
                        right=true;                    
                    }
                map.getBombLayer().setCell(cellX + x, cellY, cell);
                }
                
                if(left==false)
                {
                    if (findCell(cellX - x, cellY)!="null")
                    { 
                        left=true;                    
                    }
                map.getBombLayer().setCell(cellX - x, cellY, cell);
                }

            }else
            {
                //Set end of explosion
                Cell cellRight = new Cell();
                cellRight.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionRightEnd));
                cellRight.getTile().getProperties().put("deadly", null);
                
                if(right==false)
                {
                map.getBombLayer().setCell(cellX + x, cellY, cellRight);
                }

                
                Cell cellLeft = new Cell();
                cellLeft.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionLeftEnd));
                cellLeft.getTile().getProperties().put("deadly", null);
                
                if(up==false)
                {
                map.getBombLayer().setCell(cellX - x, cellY, cellLeft);
                }

            }
        }
        
    }
    
    
    public String findCell (int X , int Y )
    {
        String cellStatus = "null";
        Cell cell = blockLayer.getCell( X , Y );
        
        if(cell != null){
            if(cell.getTile().getProperties().containsKey("undestructable")){
                cellStatus="undestructable";
            }else if(cell.getTile().getProperties().containsKey("blocked")){
                cellStatus="destructable";
            }
        }
        
        return cellStatus;
        
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

    public boolean isExploded()
    {
        return this.isExploded;
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
    

}
