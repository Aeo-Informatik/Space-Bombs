/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.items;

import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.players.MainPlayer;
import gui.map.MapManager;


/**
 *
 * @author Christian
 */
public class RangeUp extends Item{
    

    //Constructor
    public RangeUp(int cellX, int cellY, MapManager map, EntityManager entityManager) {
        super(cellX, cellY,TextureManager.rangeUp, map, entityManager);
    }
    
    @Override
    public void itemEffect()
    {
         MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setBombRange((mainP.getBombRange() + 1)); 
            client.sendData("enemyPlayerSetRange|" + mainP.getPlayerId() + "|" + (mainP.getBombRange()) + "|*");
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPlayer ()
    {
        if(entityManager.getPlayerManager().getMainPlayer().getBombRange() < Constants.MAXBOMBRANGE)
        {
            return true;
        }
        return false;
    }
    
}
