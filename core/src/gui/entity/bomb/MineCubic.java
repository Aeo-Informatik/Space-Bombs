/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.gdx.bomberman.Constants;

import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class MineCubic extends CubicBomb
{

    private boolean playerDetected = false;
    private float detonationTimer = 0;
    private float detonationTimerEnd = 1f;
    
    
    public MineCubic(ThinGridCoordinates pos,int playerId, int cubicRange,  MapLoader map, EntityManager entityManager)
    {
        super(pos, playerId, 2, Constants.MINEACTIVATIONTIME, Constants.MINEEXPLOSIONDURATION, Constants.MINEDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);
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
                
                
                for (int x = super.cellPos.getX() - cubicRange; x <= super.cellPos.getX() + cubicRange; x++)
                {
                    for (int y = super.cellPos.getY() - cubicRange; y <= super.cellPos.getY() + cubicRange; y++)
                    {
                        int id = entityManager.getPlayerManager().getPlayerIdInCell(new MapCellCoordinates(x,y));
                         
                        if(id != -1)
                        {
                            playerDetected = true;
                        }
                    }
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
        explode(AudioManager.getBigBombExplosion());

        //Delete explosion effect after a while
        if(timerTillExplosionDelete >= explosionDuration)
        {
            deleteCubicExplosionEffect();

            //Object gets delete only set if everything is done.
            this.isExploded = true;
        }else
        {
            //Add passed time to timer
            timerTillExplosionDelete += Constants.DELTATIME;
        }
    }
}
