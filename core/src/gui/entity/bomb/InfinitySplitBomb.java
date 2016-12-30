/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.gdx.bomberman.Constants;

import java.util.ArrayList;

import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.player.Player;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class InfinitySplitBomb extends Bomb
{
    // Down, Up, Left, Right
    private ArrayList<MapCellCoordinates> nextBombPos = new ArrayList<>();
    
        //Sound
    private long soundId = -1;
    private Sound fuseSound;
    
    public InfinitySplitBomb(ThinGridCoordinates pos,  int playerId, int range, MapLoader map, EntityManager entityManager) {
        super(pos, playerId, range, Constants.INFINITYBOMBEXPLOSIONTIME, Constants.INFINITYBOMBEXPLOSIONDURATION, Constants.INFINITYBOMBDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);
         super.setBombAnimation(TextureManager.infinityAnim);
    }
    
    
    @Override
    protected void deleteExplosionEffect(int cellsUp, int cellsDown, int cellsRight, int cellsLeft)
    {
        //Create new cell and set texture
        TiledMapTileLayer.Cell cellCenter = new TiledMapTileLayer.Cell();
        cellCenter.setTile(new StaticTiledMapTile(emptyBlock));
            
        //Explosion center replaces bomb texture
        map.getBombLayer().setCell(cellPos.getX(), cellPos.getY(), cellCenter);
        
        //Explode UP 
        for(int y=1; y <= cellsUp; y++)
        {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y )))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() + y, cell);
                
                //Delete block
                boolean isDestroyed = deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
                
                // If bomb could destory the block add to possible bomb position
                if(isDestroyed)
                {
                    nextBombPos.add(0, new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
                }
                
                break;
            }

            // If explosion has reached is maximum range without touching a block add that to possible bomb positions
            if(y == cellsUp)
            {
                nextBombPos.add(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
            }
            
            //Delete explosion effect
            map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() + y, cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
        }
        
        //Explode DOWN
        for(int y=1; y <= cellsDown; y++)
        {
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));

            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y)))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() - y, cell);
                
                //Delete block
                boolean isDestroyed = deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
                
                // If bomb could destory the block add to possible bomb position
                if(isDestroyed)
                {
                    nextBombPos.add(0, new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
                }
                
                break;
            }
            
            // If explosion has reached is maximum range without touching a block add that to possible bomb positions
            if(y == cellsDown)
            {
                nextBombPos.add(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
            }
            
            //Explosion down
            map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() - y, cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
        }
        
        //Explode LEFT
        for(int x=1; x <= cellsLeft; x++)
        {
            //Set cell with middle explosion texture
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates((cellPos.getX() -x), cellPos.getY())))
            {

                //Explosion left
                map.getBombLayer().setCell(cellPos.getX() - x, cellPos.getY(), cell);
                boolean isDestroyed = deleteBlock(new MapCellCoordinates(cellPos.getX() -x, cellPos.getY()));
                
                // If bomb could destory the block add to possible bomb position
                if(isDestroyed)
                {
                    nextBombPos.add(0, new MapCellCoordinates(cellPos.getX() -x, cellPos.getY()));
                }
                
                break;
            }
            
            // If explosion has reached is maximum range without touching a block add that to possible bomb positions
            if(x == cellsLeft)
            {
                nextBombPos.add(new MapCellCoordinates(cellPos.getX() -x, cellPos.getY()));
            }
            
            //Explosion left
            map.getBombLayer().setCell(cellPos.getX() - x, cellPos.getY(), cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX() -x, cellPos.getY()));
        }
        
        //Explode RIGHT
        for(int x=1; x <= cellsRight; x++)
        {
            //Set cell with middle explosion texture
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));
            
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates((cellPos.getX() + x), cellPos.getY())))
            {
                //Explosion right
                map.getBombLayer().setCell(cellPos.getX() + x, cellPos.getY(), cell);
                boolean isDestroyed = deleteBlock(new MapCellCoordinates(cellPos.getX() +x, cellPos.getY()));
                
                                
                // If bomb could destory the block add to possible bomb position
                if(isDestroyed)
                {
                    nextBombPos.add(0,new MapCellCoordinates(cellPos.getX() +x, cellPos.getY()));
                }
                
                break;
            }
            
            // If explosion has reached is maximum range without touching a block add that to possible bomb positions
            if(x == cellsRight)
            {
                nextBombPos.add(new MapCellCoordinates(cellPos.getX() +x, cellPos.getY()));
            }
            
            //Explosion right
            map.getBombLayer().setCell(cellPos.getX() + x, cellPos.getY(), cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX() +x, cellPos.getY()));
        }
    }
    
    @Override
    public void explode(Sound sound)
    {
        //To Execute the sound only once
        if(ExplodeAudioId == -1)
        {
            ExplodeAudioId = sound.play();
        }
        
        Player player = entityManager.getPlayerManager().getCurrentPlayerObject();
        if(player != null && sound != null)
        {
            player.playSoundInDistance(sound, ExplodeAudioId, pos, Constants.NORMALBOMBEXPLOSIONMOD);
        }
        
        //Create new cell and set texture
        TiledMapTileLayer.Cell cellCenter = new TiledMapTileLayer.Cell();
        cellCenter.setTile(new StaticTiledMapTile(explosionCenter));
        cellCenter.getTile().getProperties().put("deadly", null);
        
        //Explosion center, replaces bomb texture
        map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY(), cellCenter);
        
        //Explode DOWN
        for(int y=1; y <= explosionRange; y++)
        {   
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(super.cellPos.getX(), super.cellPos.getY() - y)))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionDownEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY()- y, cellDown);
                break;
            }
           
                
            if(y != explosionRange) // If not end of explosion
            {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() - y, cell);
            }else
            {
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionDownEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() - y, cellDown);
            }
        }
        
        //Explode UP
        for(int y=1; y <= explosionRange; y++)
        {
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y )))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionUpEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() + y, cellDown);
                break;
            }
            
            if(y != explosionRange) // If not end of explosion
            {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() + y, cell);
            }else
            {
                //Set end of explosion
                TiledMapTileLayer.Cell cellUp = new TiledMapTileLayer.Cell();
                cellUp.setTile(new StaticTiledMapTile(explosionUpEnd));
                cellUp.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() + y, cellUp);
            }
        }
        
        //Explode RIGHT
        for(int x=1; x <= explosionRange; x++)
        {
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX() +x, cellPos.getY())))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionRightEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() +x, super.cellPos.getY(), cellDown);
                break;
            }
            
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() +x, super.cellPos.getY(), cell);
                
            }else
            {
                //Set end of explosion
                TiledMapTileLayer.Cell cellRight = new TiledMapTileLayer.Cell();
                cellRight.setTile(new StaticTiledMapTile(explosionRightEnd));
                cellRight.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() +x, super.cellPos.getY(), cellRight);
            }
        }
        
        //Explode LEFT
        for(int x=1; x <= explosionRange; x++)
        {
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX() -x, cellPos.getY())))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionLeftEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() -x, super.cellPos.getY(), cellDown);
                break;
            }
            
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() -x, super.cellPos.getY(), cell);
                
            }else
            {
                TiledMapTileLayer.Cell cellLeft = new TiledMapTileLayer.Cell();
                cellLeft.setTile(new StaticTiledMapTile(explosionLeftEnd));
                cellLeft.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() -x, super.cellPos.getY(), cellLeft);
            }
        }
    }
    
    @Override
    public void render()
    {
        //To make sure no bomb gets placed into wall
        if(!map.isCellBlocked(new MapCellCoordinates(pos.getX(), pos.getY())) && !isExploded)
        {
            //Execute fuse sound
            if(soundId == -1)
            {
                fuseSound = AudioManager.getGranadeClipDrop();
                soundId = fuseSound.play();
            }
            
            Player player = entityManager.getPlayerManager().getCurrentPlayerObject();
            if(player != null && fuseSound != null)
            {
                player.playSoundInDistance(fuseSound, soundId, pos, Constants.INFINITYFUSEMOD);
            }
            
            //Check if bomb has been hit by deadly tile
            if(map.isCellDeadly(new MapCellCoordinates(pos.getX(), pos.getY())) && hasBombTouchedDeadlyTile == false && timerTillExplosion < explosionTime)
            {
                //To delay the explosion after beeing hit from another bomb
                timerTillExplosion = explosionTime - delayExplodeAfterHitByBomb;

                hasBombTouchedDeadlyTile = true;
            }

            //If time to explode or deadly tile has been touched
            if(timerTillExplosion >= explosionTime)
            {
                fuseSound.stop();
                explode(AudioManager.getNormalExplosion());

                //Delete explosion effect after a while
                if(timerTillExplosionDelete >= explosionDuration)
                {
                    deleteExplosionEffect(explosionRange, explosionRange, explosionRange, explosionRange);
                    
                    //Object gets delete only set if everything is done.
                    this.isExploded = true;
                }else
                {
                    //Add passed time to timer
                    timerTillExplosionDelete += Constants.DELTATIME;
                }

            }else if(!hasBombTouchedDeadlyTile)//Creates bomb animation
            {
                //Create new cell and set its animation texture
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(animEffects.getFrame(bombAnim, true)));
                cell.getTile().getProperties().put("bomb", null);

                //Set bomb into bomb layer
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY(), cell);
            }

            //Add passed time to timer
            timerTillExplosion += Constants.DELTATIME;
            
        }else //If bomb placed in wall
        {
            //Delete bomb object.
            this.isExploded = true;
        }
    }

    /**
     * @return the nextBombPos
     */
    public ArrayList<MapCellCoordinates> getNextBombPosArray() {
        return nextBombPos;
    }
    

}
