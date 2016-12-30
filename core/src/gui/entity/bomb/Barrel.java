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

import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.player.Player;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author Christian
 */
public class Barrel extends CubicBomb {
    
    private long soundId = -1;
    private Sound placeSound;
    
    public Barrel(ThinGridCoordinates pos, int playerId, int cubicRange, MapLoader map, EntityManager entityManager) 
    {
        //Vector2 pos, Vector2 direction, int cubicRange, 
        //int explosionTime,  float explosionDuration, float delayExplodeAfterHitByBomb, int playerId, MapLoader map, EntityManager entityManager
        super(pos, playerId, cubicRange +1, 1, Constants.BARRELEXPLOSIONDURATION, Constants.BARRELDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);
        switch(cubicRange)
        {
            case(1):
                super.setBombAnimation(TextureManager.barrelAnim1);
                break;
            case(2):
                super.setBombAnimation(TextureManager.barrelAnim2);
                break;
            case(3):
                super.setBombAnimation(TextureManager.barrelAnim3);
                break;
            default:
                System.out.println("Something went wrong using barrel 3");
                super.setBombAnimation(TextureManager.barrelAnim3);
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
                placeSound = AudioManager.getBarrelPlace();
                soundId = placeSound.play();
            }
            
            Player player = entityManager.getPlayerManager().getCurrentPlayerObject();
            if(player != null && placeSound != null)
            {
                player.playSoundInDistance(placeSound, soundId, pos, Constants.BARRELFUSEMOD);
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
                placeSound.stop();
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
            if(hasBombTouchedDeadlyTile == true){
                timerTillExplosion += Constants.DELTATIME;
            }
            
        }else //If bomb placed in wall
        {
            //Delete bomb object.
            this.isExploded = true;
        }
    }
    
    @Override
    protected boolean deleteBlock(MapCellCoordinates localCellPos)
    {
        return false;
    }
}

