/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.bomb;

import client.SendCommand;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.gdx.bomberman.Constants;
import gui.AnimEffects;
import gui.TextureManager;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import java.util.Random;

/**
 *
 * @author qubasa
 */
public abstract class Bomb extends Entity
{

    //Variables
    protected int playerId;
    protected int bombId;
    protected MapCellCoordinates cellPos;
    protected boolean hasBombTouchedDeadlyTile = false;
    protected boolean isExploded = false;
    protected long ExplodeAudioId = -1;
    protected float timerTillExplosion;
    protected float timerTillExplosionDelete;
    protected SendCommand sendCommand;
    
    //Objects
    protected AnimEffects animEffects = new AnimEffects();
    
    //Bomb settings
    protected float explosionTime;
    protected int explosionRange;
    protected float explosionDuration;
    protected float delayExplodeAfterHitByBomb;
    
    //Empty block texture
    protected TextureRegion emptyBlock;
    
    //Bomb animation & texture
    protected  Animation bombAnim;
    protected TextureRegion explosionYMiddle;
    protected TextureRegion explosionXMiddle;
    protected TextureRegion explosionCenter;
    protected TextureRegion explosionDownEnd;
    protected TextureRegion explosionUpEnd;
    protected TextureRegion explosionRightEnd;
    protected TextureRegion explosionLeftEnd;
    
    
    //Constructor
    public Bomb(ThinGridCoordinates pos, ThinGridCoordinates direction, int range, float explosionTime, float explosionDuration,float delayExplodeAfterHitByBomb, int playerId, MapLoader map, EntityManager entityManager)
    { 
        super(pos, direction, map, entityManager);
        
        this.playerId = playerId;
        this.emptyBlock = TextureManager.emptyBlock;
        this.cellPos = new MapCellCoordinates(pos);
        this.sendCommand = entityManager.getSendCommand();
        
        //Bomb settings
        this.explosionRange = range; // In blocks
        this.explosionTime = explosionTime; // in seconds
        this.explosionDuration = explosionDuration; // in seconds     
        this.delayExplodeAfterHitByBomb = delayExplodeAfterHitByBomb; // in seconds
        
        //Sets the bomb texture animation 
        setBombTexture();
        
        /**----------------------------CALCULATE BOMBID----------------------------**/
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
    }
    
    
    protected abstract void explode();
    
    public void setBombAnimation(Animation bomb)
    {
        this.bombAnim = bomb ;
    }

    public void setExplosionCenter(TextureRegion explosionCenter) {
        this.explosionCenter = explosionCenter;
    }

    public TextureRegion getExplosionYMiddle() {
        return explosionYMiddle;
    }

    public TextureRegion getExplosionXMiddle() {
        return explosionXMiddle;
    }
    
    
    
    /**
     * Sets apropriate bomb texture for the playerId.
     * @param playerId 
     */
    private void setBombTexture()
    {
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
    
    /**
     * Deltes block on given cell position. Spawns also a coin randomly on destroyed blocks.
     * If it is undestructable nothing happens and false gets returned.
     * @param x: Cell position on x axis
     * @param y: Cell position on y axis
     * @return boolean
     */ 
    protected boolean deleteBlock(MapCellCoordinates localCellPos)
    {
        Cell currentCell = blockLayer.getCell(localCellPos.getX() , localCellPos.getY());
        
        if(currentCell != null)
        {
            //If block is undestructable
            if(currentCell.getTile().getProperties().containsKey("undestructable"))
            {
                return false;
                
            }else
            {
                //Delete block with empty texture
                Cell cell = new Cell();
                cell.setTile(new StaticTiledMapTile(emptyBlock));
                map.getBlockLayer().setCell(localCellPos.getX(), localCellPos.getY(), cell);
                
                
                /**---------------------RANDOM COIN---------------------**/
                //Check for a bug and if main player placed that bomb
                if(currentCell.getTile().getId() != cell.getTile().getId() && playerId == Constants.PLAYERID)
                {
                    dropFromBlock(localCellPos);
                }
            }
        }
    
        // If there is no block 
        return true;
    }
    
    /**
     * drops something when a block is destroyed
     * @param x: Cell position on x axis
     * @param y: Cell position on y axis
     */
    public void dropFromBlock (MapCellCoordinates localCellPos)
    {
        int randomNum = new Random().nextInt(10) +1;//Possible output: 1, 2...10
                    
            if(randomNum <= Constants.COINDROPCHANCE)
                {
                    //entityManager.getItemManager().spawnCoin(x, y, Constants.COINVALUE);
                        
                    //General:spawnCoin|CellX|CellY|target
                    sendCommand.spawnCoin(localCellPos);
                }
    }
    
    /**
     * Overwirtes in all 4 explosion directions the explosion tiles with empty tiles.
     * @param cellsUp: How many cell explosion tiles up should be cleared.
     * @param cellsDown: How many cell explosion tiles down should be cleared.
     * @param cellsRight: How many cell explosion tiles right should be cleared.
     * @param cellsLeft: How many cell explosion tiles left should be cleared.
     */
    protected void deleteExplosionEffect(int cellsUp, int cellsDown, int cellsRight, int cellsLeft)
    {
        //Create new cell and set texture
        Cell cellCenter = new Cell();
        cellCenter.setTile(new StaticTiledMapTile(emptyBlock));
            
        //Explosion center replaces bomb texture
        map.getBombLayer().setCell(cellPos.getX(), cellPos.getY(), cellCenter);
        
        //Explode DOWN
        for(int y=1; y <= cellsDown; y++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));

            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y)))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() - y, cell);
                
                //Delete block
                deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
                
                break;
            }
            
            //Explosion down
            map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() - y, cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
        }
        
        //Explode UP 
        for(int y=1; y <= cellsUp; y++)
        {
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y )))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() + y, cell);
                
                //Delete block
                deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
                
                break;
            }

            //Delete explosion effect
            map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() + y, cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
        }
        
        //Explode LEFT
        for(int x=1; x <= cellsLeft; x++)
        {
            //Set cell with middle explosion texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates((cellPos.getX() -x), cellPos.getY())))
            {

                //Explosion left
                map.getBombLayer().setCell(cellPos.getX() - x, cellPos.getY(), cell);
                deleteBlock(new MapCellCoordinates(cellPos.getX() -x, cellPos.getY()));
                break;
            }
            
            //Explosion left
            map.getBombLayer().setCell(cellPos.getX() - x, cellPos.getY(), cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX() -x, cellPos.getY()));
        }
        
        //Explode RIGHT
        for(int x=1; x <= cellsRight; x++)
        {
            //Set cell with middle explosion texture
            Cell cell = new Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates((cellPos.getX() + x), cellPos.getY())))
            {
                //Explosion right
                map.getBombLayer().setCell(cellPos.getX() + x, cellPos.getY(), cell);
                deleteBlock(new MapCellCoordinates(cellPos.getX() +x, cellPos.getY()));
                break;
            }
            
            //Explosion right
            map.getBombLayer().setCell(cellPos.getX() + x, cellPos.getY(), cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX() +x, cellPos.getY()));
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
    
    public float getExplosionTimer()
    {
        return explosionTime;
    }

    public void setExplosionTimer(float timer) 
    {
        this.explosionTime = timer;
    }

    public int getRange() 
    {
        return explosionRange;
    }
    
    public void setRange(int range)
    {
        this.explosionRange = range;
    }
    
    public int getCellX()
    {
        return cellPos.getX();
    }
    
    public int getCellY()
    {
        return cellPos.getY();
    }

    public void setBombAnim(Animation bombAnim) {
        this.bombAnim = bombAnim;
    }
    
}
