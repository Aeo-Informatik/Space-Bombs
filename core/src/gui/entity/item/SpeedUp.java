/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;


import com.gdx.bomberman.Constants;

import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.player.MainPlayer;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;

/**
 *
 * @author cb0703
 */
public class SpeedUp extends Item{

    public String Discription = "You get faster";
    
    public SpeedUp(MapCellCoordinates cellPos, MapLoader map, EntityManager entityManager) {
        super(cellPos,TextureManager.speedUp, map, entityManager);
    }
    
    /**
     * do wahat the item does
     */
    @Override
    public void itemEffect()
    {
        
        MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setEntitySpeed((mainP.getEntitySpeed() + Constants.SPEEDUPGRADE));
            sendCommand.setPlayerSpeed(mainP.getPlayerId(), mainP.getEntitySpeed());
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPlayer ()
    {
        return entityManager.getPlayerManager().getMainPlayer().getEntitySpeed() < Constants.MAXSPEED;
    }
}
