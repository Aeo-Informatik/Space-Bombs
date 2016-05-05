/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.bombs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import gui.Constants;
import gui.TextureManager;
import gui.entity.Entity;
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
    boolean touchedDeadlyTile = false;
    
    //Bomb settings
    private float timer;
    private float timer2;
    private float explosionTime;
    private int explosionRange;
    private float explosionDuration;
    private boolean isExploded = false;
    private float delayAfterHitByBomb;
    
    //Blocks
    private TextureRegion emptyBlock;
    
    //Bomb Animation
    private  Animation bombAnim;
    private TextureRegion explosionYMiddle;
    private TextureRegion explosionXMiddle;
    private TextureRegion explosionCenter;
    private TextureRegion explosionDownEnd;
    private TextureRegion explosionUpEnd;
    private TextureRegion explosionRightEnd;
    private TextureRegion explosionLeftEnd;
    
    
    //Constructor
    public Bomb(Vector2 pos, Vector2 direction, MapManager map, int playerId)
    {
        super(pos, direction, map);
        
        //Needed variables
        this.playerId = playerId;
        this.emptyBlock = TextureManager.emptyBlock;
        
        //Get cell positon
        this.cellX = (int) (pos.x / Constants.MAPTEXTUREWIDTH);
        this.cellY = (int) (pos.y / Constants.MAPTEXTUREHEIGHT);
        
        //Bomb settings
        this.explosionRange = 2; // In blocks
        this.explosionTime = 2; // in seconds
        this.explosionDuration = 0.4f; // in seconds     
        this.delayAfterHitByBomb = 0.4f;
        
        //Set Textures
        switch(playerId)
        {
            case 1:
                this.bombAnim = TextureManager.normalBombAnimP1;
                this.explosionYMiddle = TextureManager.p1ExplosionYMiddle;
                this.explosionXMiddle = TextureManager.p1ExplosionXMiddle;
                this.explosionCenter = TextureManager.p1ExplosionCenter;
                this.explosionDownEnd = TextureManager.p1ExplosionDownEnd;
                this.explosionUpEnd = TextureManager.p1ExplosionUpEnd;
                this.explosionRightEnd = TextureManager.p1ExplosionRightEnd;
                this.explosionLeftEnd = TextureManager.p1ExplosionLeftEnd;
                break;
            
            default:
                System.err.println("ERROR: Wrong playerId in Bomb defined " + playerId + " using default p1 textures.");
                this.bombAnim = TextureManager.normalBombAnimP1;
                this.explosionYMiddle = TextureManager.p1ExplosionYMiddle;
                this.explosionXMiddle = TextureManager.p1ExplosionXMiddle;
                this.explosionCenter = TextureManager.p1ExplosionCenter;
                this.explosionDownEnd = TextureManager.p1ExplosionDownEnd;
                this.explosionUpEnd = TextureManager.p1ExplosionUpEnd;
                this.explosionRightEnd = TextureManager.p1ExplosionRightEnd;
                this.explosionLeftEnd = TextureManager.p1ExplosionLeftEnd;
        }
    }
    
    
    @Override
    public void render(SpriteBatch sb)
    {
        //To make sure no bomb gets placed into wall
        if(!map.isCellBlocked(pos.x, pos.y) && !isExploded)
        {
            if(map.isCellDeadly(pos.x, pos.y) && touchedDeadlyTile == false && timer < explosionTime)
            {
                //To delay the explosion after hit from another bomb
                timer = explosionTime - delayAfterHitByBomb;

                touchedDeadlyTile = true;
            }

            //If time to explode or deadly tile has been touched
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

            }else if(!touchedDeadlyTile)//Creates bomb animation
            {
                //Create new cell and set its animation texture
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(getFrame(bombAnim)));
                cell.getTile().getProperties().put("bomb", null);

                //Set bomb into bomb layer
                map.getBombLayer().setCell(cellX, cellY, cell);
            }

            //Add passed to counter
            timer += Constants.DELTATIME;
        }else
        {
            //Object gets delete only set if everything is done.
            this.isExploded = true;
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
                cell.setTile(new StaticTiledMapTile(emptyBlock));
                
                map.getBlockLayer().setCell( x, y, cell);
            }
        }
        
        // If there is no block 
        return true;
    }
    
    private void deleteExplosionEffect()
    {
        //Create new cell and set texture
        Cell cellCenter = new Cell();
        cellCenter.setTile(new StaticTiledMapTile(emptyBlock));
            
        //Explosion center replaces bomb texture
        map.getBombLayer().setCell(cellX, cellY, cellCenter);
        
        //Explode DOWN
        for(int y=1; y <= explosionRange; y++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));

            //If explosion hits block
            if(map.isCellBlocked(cellX * Constants.MAPTEXTUREWIDTH, (cellY - y) * Constants.MAPTEXTUREHEIGHT))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellX, cellY - y, cell);
                
                //Delete block
                deleteBlock(cellX, cellY - y);
                
                break;
            }
            
            //Explosion down
            map.getBombLayer().setCell(cellX, cellY - y, cell);
            deleteBlock(cellX, cellY - y);
        }
        
        //Explode UP 
        for(int y=1; y <= explosionRange; y++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked(cellX * Constants.MAPTEXTUREWIDTH, (cellY + y) * Constants.MAPTEXTUREHEIGHT))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellX, cellY + y, cell);
                
                //Delete block
                deleteBlock(cellX, cellY + y);
                
                break;
            }

            //Delete explosion effect
            map.getBombLayer().setCell(cellX, cellY + y, cell);
            deleteBlock(cellX, cellY + y);
        }
        
        //Explode LEFT
        for(int x=1; x <= explosionRange; x++)
        {
            //Set cell with middle explosion texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked((cellX -x) * Constants.MAPTEXTUREWIDTH, cellY * Constants.MAPTEXTUREHEIGHT))
            {

                //Explosion left
                map.getBombLayer().setCell(cellX - x, cellY, cell);
                deleteBlock(cellX -x, cellY);
                break;
            }
            
            //Explosion left
            map.getBombLayer().setCell(cellX - x, cellY, cell);
            deleteBlock(cellX -x, cellY);
        }
        
        //Explode RIGHT
        for(int x=1; x <= explosionRange; x++)
        {
            //Set cell with middle explosion texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked((cellX + x) * Constants.MAPTEXTUREWIDTH, cellY * Constants.MAPTEXTUREHEIGHT))
            {
                //Explosion right
                map.getBombLayer().setCell(cellX + x, cellY, cell);
                deleteBlock(cellX +x, cellY);
                break;
            }
            
            //Explosion right
            map.getBombLayer().setCell(cellX + x, cellY, cell);
            deleteBlock(cellX +x, cellY);
        }
    }
    
    private void explode()
    {
        //Create new cell and set texture
        Cell cellCenter = new Cell();
        cellCenter.setTile(new StaticTiledMapTile(explosionCenter));
        cellCenter.getTile().getProperties().put("deadly", null);
        
        //Explosion center, replaces bomb texture
        map.getBombLayer().setCell(cellX, cellY, cellCenter);
        
        //Explode DOWN
        for(int y=1; y <= explosionRange; y++)
        {   
            //If explosion hits block
            if(map.isCellBlocked(cellX * Constants.MAPTEXTUREWIDTH, (cellY - y) * Constants.MAPTEXTUREHEIGHT))
            {
                //Set ending texture and break out of loop
                Cell cellDown = new Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionDownEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY - y, cellDown);
                break;
            }
           
                
            if(y != explosionRange) // If not end of explosion
            {
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(explosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY - y, cell);
            }else
            {
                Cell cellDown = new Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionDownEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY - y, cellDown);
            }
        }
        
         //Explode UP
        for(int y=1; y <= explosionRange; y++)
        {
            //If explosion hits block
            if(map.isCellBlocked(cellX * Constants.MAPTEXTUREWIDTH, (cellY + y) * Constants.MAPTEXTUREHEIGHT))
            {
                //Set ending texture and break out of loop
                Cell cellDown = new Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionUpEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY + y, cellDown);
                break;
            }
            
            if(y != explosionRange) // If not end of explosion
            {
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(explosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY + y, cell);
            }else
            {
                //Set end of explosion
                Cell cellUp = new Cell();
                cellUp.setTile(new StaticTiledMapTile(explosionUpEnd));
                cellUp.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX, cellY + y, cellUp);
            }
        }
        
        //Explode RIGHT
        for(int x=1; x <= explosionRange; x++)
        {
            //If explosion hits block
            if(map.isCellBlocked((cellX +x) * Constants.MAPTEXTUREWIDTH, cellY * Constants.MAPTEXTUREHEIGHT))
            {
                //Set ending texture and break out of loop
                Cell cellDown = new Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionRightEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX +x, cellY, cellDown);
                break;
            }
            
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(explosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX + x, cellY, cell);
                
            }else
            {
                //Set end of explosion
                Cell cellRight = new Cell();
                cellRight.setTile(new StaticTiledMapTile(explosionRightEnd));
                cellRight.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX + x, cellY, cellRight);
            }
        }
        
        //Explode LEFT
        for(int x=1; x <= explosionRange; x++)
        {
            //If explosion hits block
            if(map.isCellBlocked((cellX -x) * Constants.MAPTEXTUREWIDTH, cellY * Constants.MAPTEXTUREHEIGHT))
            {
                //Set ending texture and break out of loop
                Cell cellDown = new Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionLeftEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX -x, cellY, cellDown);
                break;
            }
            
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(explosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(cellX - x, cellY, cell);
                
            }else
            {
                Cell cellLeft = new Cell();
                cellLeft.setTile(new StaticTiledMapTile(explosionLeftEnd));
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
        return explosionTime;
    }

    public void setTimer(float timer) 
    {
        this.explosionTime = timer;
    }

    public int getRange() 
    {
        return explosionRange;
    }
    
    public int getCellX()
    {
        return cellX;
    }
    
    public int getCellY()
    {
        return cellY;
    }
}
