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
public class BombUp extends Item{
    
    public String Discription = "You can hold more Bombs";
    
    //Constructor
    public BombUp(MapCellCoordinates cellPos, MapLoader map, EntityManager entityManager) 
    {
        super(cellPos,TextureManager.bombUp, map, entityManager);
    }
    
    @Override
    public void itemEffect()
    {
        
        MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setMaxBombPlacing((mainP.getMaxBombPlacing() + 1)); 
            sendCommand.setMaxPlaceBombs(mainP.getPlayerId(), mainP.getMaxBombPlacing());
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPlayer ()
    {
        return entityManager.getPlayerManager().getMainPlayer().getMaxBombPlacing() < Constants.MAXBOMBPLACE;
    }
    
}
