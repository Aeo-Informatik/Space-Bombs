/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;


import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import gui.AudioManager;
import gui.entity.player.MainPlayer;


/**
 *
 * @author Christian
 */
public class CoinBag extends Item{
    
    public String Discription = "You get 10 times the coinvalue in coins (default 100)";
    private int value;

    public CoinBag(int cellX, int cellY, MapLoader map, EntityManager entityManager, int value) {
        super(cellX, cellY,TextureManager.coinBag, map, entityManager);
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
            client.sendData("enemyPlayerSetCoins|" + mainP.getPlayerId() + "|" + (mainP.getCoins()) + "|*");
        }
    }
    

    
}
