/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.audio.Sound;
import com.gdx.bomberman.Constants;
import gui.AudioManager;
import gui.entity.EntityManager;
import gui.entity.player.MainPlayer;
import gui.hud.MainPlayerHud;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class Teleport extends Bomb{

    //Sound
    private long soundId = -1;
    private Sound itemSound;
    
    //Item
    private float teleportTimer = 0;
    private float teleportTimerEnd = 2;
    
    //Objects
    MainPlayer mainP;
    
    public Teleport(ThinGridCoordinates pos, ThinGridCoordinates direction, int playerId, MapLoader map, EntityManager entityManager) {
        super(pos, direction, 0, 0, 0, 0, playerId, map, entityManager);
    }

    
    
    @Override
    protected void explode(Sound sound) 
    {
        for(int mapY=0; mapY < map.getFloorLayer().getHeight(); mapY++)
        {
            for(int mapX=0; mapX < map.getFloorLayer().getWidth(); mapX++)
            {
                try
                {
                    if(map.getFloorLayer().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + mainP.getPlayerId()))
                    {
                        //Execute sound
                        if(soundId == -1)
                        {
                            soundId = sound.play();
                            sound.setVolume(soundId, Constants.SOUNDVOLUME);
                        }

                        mainP.setPosition(new ThinGridCoordinates(mapX, mapY));
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
        this.isExploded = true;
    }

    @Override
    public void render() 
    {
        mainP = entityManager.getPlayerManager().getMainPlayer();
        
        if(mainP != null)
        {
            if(new MapCellCoordinates(mainP.getFeetLocation()).equalCoordinates(cellPos))
            {
                if(teleportTimer >= teleportTimerEnd)
                {
                    explode(AudioManager.getTeleport());
                }else
                {
                    teleportTimer += Constants.DELTATIME;
                }
            }else
            {
                MainPlayerHud.printToScreen("Teleportation aborted. Don't move!");
                this.isExploded = true;
            }
        }else
        {
            this.isExploded = true;
        }
        
    }
    
}
