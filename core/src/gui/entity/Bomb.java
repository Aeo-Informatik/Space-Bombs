/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private SpriteBatch sb;
    private int playerId ;
    int cellX, cellY;
    
    //Bomb settings
    private float timer;
    private float timer2;
    private float explosionTime;
    private int explosionRange;
    private float explosionDuration;
    private boolean isExploded = false;
    
    //Bomb 1 Animation
    private  Animation normalBombAnim;

    
    //Constructor
    public Bomb(Vector2 pos, Vector2 direction, MapManager map, int playerId)
    {
        super(pos, direction, map);
        
        //Needed variables
        this.playerId = playerId;
        
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
            
        }else //Creates bomb animation
        {
            //Create new cell and set its animation texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(getFrame(this.normalBombAnim)));
            cell.getTile().getProperties().put("bomb", null);
            
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
            
        //Explosion center replaces bomb texture
        map.getBombLayer().setCell(cellX, cellY, cellCenter);
        
        //Explode on y 
        for(int y=1; y <= explosionRange; y++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(TextureManager.emptyBlock));

            //Explosion above
            map.getBombLayer().setCell(cellX, cellY + y, cell);
            deleteBlock(cellX, cellY + y);
            
            //Explosion below
            map.getBombLayer().setCell(cellX, cellY - y, cell);
            deleteBlock(cellX, cellY - y);
        }
        
        for(int x=1; x <= explosionRange; x++)
        {
            //Set cell with middle explosion texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(TextureManager.emptyBlock));

            //Explosion right
            map.getBombLayer().setCell(cellX + x, cellY, cell);
            deleteBlock(cellX +x, cellY);
            
            //Explosion left
            map.getBombLayer().setCell(cellX - x, cellY, cell);
            deleteBlock(cellX -x, cellY);
        }
        
    }
    
    public void explode()
    {
        //Create new cell and set texture
        Cell cellCenter = new Cell();
        cellCenter.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionCenter));
        cellCenter.getTile().getProperties().put("deadly", null);
        
        //Explosion center, replaces bomb texture
        map.getBombLayer().setCell(cellX, cellY, cellCenter);
        
        //Explode DOWN
        for(int y=1; y <= explosionRange; y++)
        {
            if(y != explosionRange) // If not end of explosion
            {
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY - y, cell);
            }else
            {
                Cell cellDown = new Cell();
                cellDown.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionDownEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY - y, cellDown);
            }
        }
        
         //Explode UP
        for(int y=1; y <= explosionRange; y++)
        {
            if(y != explosionRange) // If not end of explosion
            {
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY + y, cell);
            }else
            {
                //Set end of explosion
                Cell cellUp = new Cell();
                cellUp.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionUpEnd));
                cellUp.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY + y, cellUp);
            }
        }
        
        //Explode RIGHT
        for(int x=1; x <= explosionRange; x++)
        {
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX + x, cellY, cell);
                
            }else
            {
                //Set end of explosion
                Cell cellRight = new Cell();
                cellRight.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionRightEnd));
                cellRight.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX + x, cellY, cellRight);
            }
        }
        
        //Explode LEFT
        for(int x=1; x <= explosionRange; x++)
        {
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX - x, cellY, cell);
                
            }else
            {
                Cell cellLeft = new Cell();
                cellLeft.setTile(new StaticTiledMapTile(TextureManager.p1ExplosionLeftEnd));
                cellLeft.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX - x, cellY, cellLeft);
            }
        }
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
