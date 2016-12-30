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
import gui.entity.player.MainPlayer;
import gui.entity.player.Player;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class BlackHole extends CubicBomb
{
    private float gravityForce = 300;
    private float gravityCenterRadius = 10;
    
    // Timer till gravity activates
    private float gravityActivateTimer = 0;
    private float gravityActivateTimerEnd = Constants.BLACKHOLEGRAVITYACTIVATION;
    
    // Timer how long the gravity is activated
    private float gravityDurationTimer = 0;
    private float gravityDurationTimerEnd = Constants.BLACKHOLEGRAVITYDURATION;
    
    private boolean stopGravity = false;
    private boolean isGravityTextureSet = false;
    private boolean isCompressedTextureSet = false;
    // Timer for cubic explosion
    
    //Sound
    private long soundId1 = -1;
    private long soundId2 = -1;
    private Sound blackHoleSound;
    private Sound implosionSound;
    
    public BlackHole(ThinGridCoordinates pos, int playerId, int range,  MapLoader map, EntityManager entityManager) {
        super(pos, playerId, range +1, Constants.BLACKHOLEEXPLOSIONTIME, Constants.BLACKHOLEEXPLOSIONDURATION, Constants.BLACKHOLEDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);
    
        super.setBombAnim(TextureManager.blackHoleActivationBombAnim);
    }

    private void gravityActivation()
    {
        //Execute fuse sound
        if(soundId1 == -1)
        {
            blackHoleSound = AudioManager.getBlackHole();
            soundId1 = blackHoleSound.play();
        }
        
        Player player = entityManager.getPlayerManager().getCurrentPlayerObject();
        if(player != null)
        {
            player.playSoundInDistance(blackHoleSound, soundId1, pos, Constants.BLACKHOLEFUSEMOD);
        }
        
        if(!isGravityTextureSet)
        {
            animEffects.resetAnimationTime();
            super.setBombAnim(TextureManager.blackHoleAnim);
            isGravityTextureSet = true;
        }
            
        animateBomb();
        
        MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        if(mainP != null)
        {
            mainP.gravitationForce(pos, gravityForce, gravityCenterRadius);
        }
    }
    

    @Override
    public void render() 
    {
        if(!map.isCellBlocked(new MapCellCoordinates(pos.getX(), pos.getY())) && !isExploded)
        {
            //Check if bomb has been hit by deadly tile
            if(map.isCellDeadly(new MapCellCoordinates(pos.getX(), pos.getY())) && hasBombTouchedDeadlyTile == false && timerTillExplosion < explosionTime)
            {
                //To delay the explosion after beeing hit from another bomb
                gravityActivateTimer = gravityActivateTimerEnd - delayExplodeAfterHitByBomb;

                hasBombTouchedDeadlyTile = true;
            }
            
            //If time to explode or deadly tile has been touched
            if(gravityActivateTimer >= gravityActivateTimerEnd)
            {
                if(!stopGravity)
                {
                    gravityActivation();
                }
                    
                //Delete explosion effect after a while
                if(gravityDurationTimer >= gravityDurationTimerEnd)
                {
                    stopGravity = true;
                    blackHoleSound.stop();
                    blackHoleCubicExplosion();
                }else
                {
                    //Add passed time to timer
                    gravityDurationTimer += Constants.DELTATIME;
                }

            }else if(!hasBombTouchedDeadlyTile)//Creates bomb animation
            {
                animateBomb();
            }

            //Add passed time to timer
            gravityActivateTimer += Constants.DELTATIME;
        }else //If bomb placed in wall
        {
            //Delete bomb object.
            this.isExploded = true;
        }
    }
    
    private void animateBomb()
    {
        //Create new cell and set its animation texture
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(animEffects.getFrame(bombAnim, true)));
        cell.getTile().getProperties().put("bomb", null);

        //Set bomb into bomb layer
        map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY(), cell);
    }
    
    private void blackHoleCubicExplosion()
    {
        //To make sure no bomb gets placed into wall
        if(!map.isCellBlocked(new MapCellCoordinates(pos.getX(), pos.getY())) && !isExploded)
        {
            //Execute fuse sound
            if(soundId2 == -1)
            {
                implosionSound = AudioManager.getBlackHoleImplosion();
                soundId2 = implosionSound.play();
            }
            
            Player player = entityManager.getPlayerManager().getCurrentPlayerObject();
            if(player != null)
            {
                player.playSoundInDistance(implosionSound, soundId2, pos, Constants.BLACKHOLEEXPLOSIONMOD);
            }
            
            if(!isCompressedTextureSet)
            {
                animEffects.resetAnimationTime();
                super.setBombAnim(TextureManager.blackHoleCompressedAnim);
                isCompressedTextureSet = true;
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
                explode(null);
                
                //Delete explosion effect after a while
                if(timerTillExplosionDelete >= explosionDuration)
                {
                    deleteCubicExplosionEffect();
                    
                    implosionSound.stop();
                    
                    //Object gets delete only set if everything is done.
                    this.isExploded = true;
                }else
                {
                    //Add passed time to timer
                    timerTillExplosionDelete += Constants.DELTATIME;
                }

            }else if(!hasBombTouchedDeadlyTile)//Creates bomb animation
            {
                animateBomb();
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
