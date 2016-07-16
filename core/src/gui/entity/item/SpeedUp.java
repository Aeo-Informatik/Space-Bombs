/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;


import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.entity.player.MainPlayer;

/**
 *
 * @author cb0703
 */
public class SpeedUp extends Item{

    public SpeedUp(int CellX, int CellY, MapManager map, EntityManager entityManager) {
        super(CellX, CellY,TextureManager.speedUp, map, entityManager);
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
            mainP.setEntitySpeed((mainP.getEntitySpeed() + 0.1f));
            client.sendData("enemyPlayerSetSpeed|" + mainP.getPlayerId() + "|" + (mainP.getEntitySpeed()) + "|*");
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPlayer ()
    {
        if(entityManager.getPlayerManager().getMainPlayer().getEntitySpeed() < Constants.MAXSPEED)
        {
            return true;
        }
        return false;
    }
}
