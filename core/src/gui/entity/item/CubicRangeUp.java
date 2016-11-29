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
 * @author Christian
 */
public class CubicRangeUp extends Item {
    
    public String Discription = "Your cubicRange expands";

    //Constructor
    public CubicRangeUp(MapCellCoordinates cellPos, MapLoader map, EntityManager entityManager) {
        super(cellPos,TextureManager.cubicRangeUp, map, entityManager);
    }
    
    @Override
    public void itemEffect()
    {
         MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setCubicRange((mainP.getCubicRange() + 1));
            mainP.setBombPrice(2, Constants.DYNAMITEPRICEUPGRADE);
            mainP.setBombPrice(8, Constants.BARRELPRICEUPGRADE);
            sendCommand.setPlayerCubicRange(mainP.getPlayerId(), mainP.getCubicRange());
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPlayer ()
    {
        if(entityManager.getPlayerManager().getMainPlayer().getCubicRange() < Constants.MAXCUBICRANGE)
        {
            return true;
        }
        return false;
    }
    
}

    

