/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.gdx.bomberman.Constants;

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
public class Mine extends Bomb
{

    private boolean playerDetected = false;
    private float detonationTimer = 0;
    private float detonationTimerEnd = 0.6f;
    
    public Mine(ThinGridCoordinates pos,int playerId, int range,  MapLoader map, EntityManager entityManager)
    {
        super(pos, playerId, range, Constants.MINEACTIVATIONTIME, Constants.MINEEXPLOSIONDURATION, Constants.MINEDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);
        super.setBombAnim(TextureManager.p1MineBombAnim);
    }

   

    @Override
    public void render() 
    {
        //To make sure no bomb gets placed into wall
        if(!map.isCellBlocked(new MapCellCoordinates(pos.getX(), pos.getY())) && !isExploded)
        {
            //Check if bomb has been hit by another bomb
            if(map.isCellDeadly(new MapCellCoordinates(pos.getX(), pos.getY())) && hasBombTouchedDeadlyTile == false)
            {
                timerTillExplosion = explosionTime - delayExplodeAfterHitByBomb;

                hasBombTouchedDeadlyTile = true;
            }

            //Timer to arm (activate) the mine
            if(timerTillExplosion >= explosionTime)
            {          
                // Explode if deadly tile has been touched
                if(hasBombTouchedDeadlyTile)
                {
                    triggerExplosion();
                }
                
                // Left
                int id1 = entityManager.getPlayerManager().getPlayerIdInCell(new MapCellCoordinates(cellPos.getX() -1,cellPos.getY()));
                
                // Right
                int id2 = entityManager.getPlayerManager().getPlayerIdInCell(new MapCellCoordinates(cellPos.getX() +1,cellPos.getY()));
                
                // Up
                int id3 = entityManager.getPlayerManager().getPlayerIdInCell(new MapCellCoordinates(cellPos.getX(),cellPos.getY() +1));
                
                // Down
                int id4 = entityManager.getPlayerManager().getPlayerIdInCell(new MapCellCoordinates(cellPos.getX(),cellPos.getY() -1));
                
                if(id1 != -1 || id2 != -1 || id3 != -1 || id4 != -1)
                {
                    playerDetected = true;
                }
                

                // If player has been detected reverse animation an trigger explosion
                if(playerDetected)
                {
                    if(detonationTimer >= detonationTimerEnd)
                    {
                        triggerExplosion();
                    }else if(!hasBombTouchedDeadlyTile)
                    {
                        //Create new cell and set its animation texture
                        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                        cell.setTile(new StaticTiledMapTile((TextureRegion) bombAnim.getKeyFrame(0)));
                        cell.getTile().getProperties().put("bomb", null);
                        
                        //Set bomb into bomb layer
                        map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY(), cell);
                    }
                    
                    detonationTimer += Constants.DELTATIME;
                }
                
            // Play animation of hiding mine    
            }else if(!hasBombTouchedDeadlyTile)
            {
                //Create new cell and set its animation texture
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(animEffects.getFrame(bombAnim, true)));
                cell.getTile().getProperties().put("bomb", null);
                //Set bomb into bomb layer
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY(), cell);
            }

            // Add passed time to bomb activation timer
            timerTillExplosion += Constants.DELTATIME;
            
        }else 
        {
            // If bomb placed in wall delete bomb object.
            this.isExploded = true;
        }
    }
    
    
    private void triggerExplosion()
    {
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
    }
    
    @Override
    protected void explode(Sound sound) 
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
}
