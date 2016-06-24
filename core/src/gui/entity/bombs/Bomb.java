/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.AnimEffects;
import gui.TextureManager;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class Bomb extends Entity
{

    //General variables
    private int playerId;
    private boolean touchedDeadlyTile = false;
    private AnimEffects animEffects = new AnimEffects();
    
    //Bomb settings
    private float timer;
    private float timer2;
    private float explosionTime;
    private int explosionRange;
    private float explosionDuration;
    private boolean isExploded = false;
    private float delayAfterHitByBomb;
    private int bombId;
    private int cellX, cellY;
    
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
    public Bomb(Vector2 pos, Vector2 direction, MapManager map, int playerId, EntityManager entityManager)
    { 
        super(pos, direction, map, entityManager);
        
        //Needed variables
        this.playerId = playerId;
        this.emptyBlock = TextureManager.emptyBlock;
        this.cellX = (int) (pos.x / Constants.MAPTEXTUREWIDTH);
        this.cellY = (int) (pos.y / Constants.MAPTEXTUREHEIGHT);
        
        //Bomb settings
        this.explosionRange = 2; // In blocks
        this.explosionTime = 2; // in seconds
        this.explosionDuration = 0.4f; // in seconds     
        this.delayAfterHitByBomb = 0.4f;
                
        //If player placed more bombs then defined in range reset Id to startvalue
        switch(playerId)
        {
            case 1:
                Constants.BOMBIDCOUNTERP1 += 1;
                bombId = Constants.BOMBIDCOUNTERP1;
                
                if(bombId > 1000)
                    Constants.BOMBIDCOUNTERP1 = 0;
                break;
            
            case 2:
                Constants.BOMBIDCOUNTERP2 += 1;
                bombId = Constants.BOMBIDCOUNTERP2;
                
                if(bombId > 2000)
                    Constants.BOMBIDCOUNTERP2 = 1000;
                break;
                
            case 3:
                Constants.BOMBIDCOUNTERP3 += 1;
                bombId = Constants.BOMBIDCOUNTERP3;
                
                if(bombId > 3000)
                    Constants.BOMBIDCOUNTERP3 = 2000;
                break;
                
            case 4:
                Constants.BOMBIDCOUNTERP4 += 1;
                this.bombId = Constants.BOMBIDCOUNTERP4;
                
                if(bombId > 4000)
                    Constants.BOMBIDCOUNTERP4 = 3000;
                break;
        }
        
        if(Constants.CLIENTDEBUG)
            System.out.println("BomId for player " + playerId +" is: " + bombId);
        
        //Set Textures
        switch(playerId)
        {
            case 1:
                this.bombAnim = TextureManager.p1NormalBombAnim;
                this.explosionYMiddle = TextureManager.p1ExplosionYMiddle;
                this.explosionXMiddle = TextureManager.p1ExplosionXMiddle;
                this.explosionCenter = TextureManager.p1ExplosionCenter;
                this.explosionDownEnd = TextureManager.p1ExplosionDownEnd;
                this.explosionUpEnd = TextureManager.p1ExplosionUpEnd;
                this.explosionRightEnd = TextureManager.p1ExplosionRightEnd;
                this.explosionLeftEnd = TextureManager.p1ExplosionLeftEnd;
                break;
            
            case 2:
                this.bombAnim = TextureManager.p2NormalBombAnim;
                this.explosionYMiddle = TextureManager.p2ExplosionYMiddle;
                this.explosionXMiddle = TextureManager.p2ExplosionXMiddle;
                this.explosionCenter = TextureManager.p2ExplosionCenter;
                this.explosionDownEnd = TextureManager.p2ExplosionDownEnd;
                this.explosionUpEnd = TextureManager.p2ExplosionUpEnd;
                this.explosionRightEnd = TextureManager.p2ExplosionRightEnd;
                this.explosionLeftEnd = TextureManager.p2ExplosionLeftEnd;
                break;
                
            case 3:
                this.bombAnim = TextureManager.p3NormalBombAnim;
                this.explosionYMiddle = TextureManager.p3ExplosionYMiddle;
                this.explosionXMiddle = TextureManager.p3ExplosionXMiddle;
                this.explosionCenter = TextureManager.p3ExplosionCenter;
                this.explosionDownEnd = TextureManager.p3ExplosionDownEnd;
                this.explosionUpEnd = TextureManager.p3ExplosionUpEnd;
                this.explosionRightEnd = TextureManager.p3ExplosionRightEnd;
                this.explosionLeftEnd = TextureManager.p3ExplosionLeftEnd;
                break;
                
            case 4:
                this.bombAnim = TextureManager.p4NormalBombAnim;
                this.explosionYMiddle = TextureManager.p4ExplosionYMiddle;
                this.explosionXMiddle = TextureManager.p4ExplosionXMiddle;
                this.explosionCenter = TextureManager.p4ExplosionCenter;
                this.explosionDownEnd = TextureManager.p4ExplosionDownEnd;
                this.explosionUpEnd = TextureManager.p4ExplosionUpEnd;
                this.explosionRightEnd = TextureManager.p4ExplosionRightEnd;
                this.explosionLeftEnd = TextureManager.p4ExplosionLeftEnd;
                break;
                
            default:
                System.err.println("ERROR: Wrong playerId in bomb defined " + playerId + " using default p1 textures.");
                this.bombAnim = TextureManager.p1NormalBombAnim;
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
    public void render(SpriteBatch renderObject)
    {
        this.cellX = (int) (pos.x / Constants.MAPTEXTUREWIDTH);
        this.cellY = (int) (pos.y / Constants.MAPTEXTUREHEIGHT);
        
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
                cell.setTile(new StaticTiledMapTile(animEffects.getFrame(bombAnim)));
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
            
    /**
     * If bomb explosion hits block delete it
     * @param x: position on x axis
     * @param y: position on y axis
     * @return boolean
     */ 
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

    public int getPlayerId()
    {
        return this.playerId;
    }
    
    public boolean isExploded()
    {
        return this.isExploded;
    }

    public int getBombId()
    {
        return this.bombId;
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
