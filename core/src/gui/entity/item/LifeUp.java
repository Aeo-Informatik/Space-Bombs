/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;


import client.SendCommand;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.player.MainPlayer;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;


/**
 *
 * @author Christian
 */
public class LifeUp extends Item{
    
    public String Discription = "You get one more live";
    
    //Constructor
    public LifeUp(MapCellCoordinates cellPos, MapLoader map, EntityManager entityManager) 
    {
        super(cellPos,TextureManager.lifeUp, map, entityManager);
    }
    
    @Override
    public void itemEffect()
    {
        MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setLife((mainP.getLife() + 1));
            SendCommand.setPlayerLife(mainP.getPlayerId(), mainP.getLife());
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPlayer ()
    {
        if(entityManager.getPlayerManager().getMainPlayer().getLife() < Constants.MAXLIFE)
        {
            return true;
        }
        return false;
    }

    
}
