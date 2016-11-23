/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;


import client.SendCommand;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import gui.entity.player.MainPlayer;
import gui.map.MapCellCoordinates;


/**
 *
 * @author Christian
 */
public class CoinBag extends Item{
    
    public String Discription = "You get 5 times the coinvalue in coins";
    private int value;

    public CoinBag(MapCellCoordinates cellPos, MapLoader map, EntityManager entityManager, int value) {
        super(cellPos,TextureManager.coinBag, map, entityManager);
        this.value = value;
    }
    
    @Override
    public void itemEffect()
    {
        MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            mainP.setCoins((mainP.getCoins() + value)); 
            sendCommand.setPlayerCoins(mainP.getPlayerId(), mainP.getCoins());
        }
    }
}
