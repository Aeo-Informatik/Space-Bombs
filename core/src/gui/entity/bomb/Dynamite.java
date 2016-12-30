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
public class Dynamite extends CubicBomb{
    
    public String Discription = "Cubic explosion";
    
    //Sound
    private long soundId = -1;
    private Sound fuseSound;
    
    public Dynamite(ThinGridCoordinates pos, int playerId, int cubicRange,  MapLoader map, EntityManager entityManager) 
    {
        //Vector2 pos, Vector2 direction, int cubicRange, int explosionTime, float explosionDuration, 
        //float delayExplodeAfterHitByBomb, int playerId, MapLoader map, EntityManager entityManager
        super(pos, playerId, cubicRange, Constants.DYNAMITEBOMBEXPLOSIONTIME, Constants.DYNAMITEBOMBEXPLOSIONDURATION, Constants.DYNAMITEBOMBDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);

        switch(cubicRange)
        {
            case(1):
                super.setBombAnimation(TextureManager.dynamiteAnim1);
                break;
            case(2):
                super.setBombAnimation(TextureManager.dynamiteAnim2);
                break;
            case(3):
                super.setBombAnimation(TextureManager.dynamiteAnim3);
                break;
            default:
                System.err.println("Something went wrong using dynamite texture 3");
                super.setBombAnimation(TextureManager.dynamiteAnim3);
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
                fuseSound = AudioManager.getBombFuse();
                soundId = fuseSound.play();
            }
            
            Player player = entityManager.getPlayerManager().getCurrentPlayerObject();
            if(player != null)
            {
                player.playSoundInDistance(fuseSound, soundId, pos, Constants.DYNAMITEFUSEMOD);
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
            timerTillExplosion += Constants.DELTATIME;
            
        }else //If bomb placed in wall
        {
            //Delete bomb object.
            this.isExploded = true;
        }
    }
    
    
}
