/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import gui.entity.bomb.BombManager;
import gui.map.MapLoader;
import gui.entity.item.ItemManager;
import gui.entity.player.PlayerManager;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //NOTE: Array from libgdx is much faster in comparison to an arraylist
    
    //Managers
    private ItemManager itemManager;
    private PlayerManager playerManager;
    private BombManager bombManager;

    //Constructor
    public EntityManager(OrthographicCamera camera, MapLoader map)
    {
        this.itemManager = new ItemManager(map, this);
        this.playerManager = new PlayerManager(camera, map, this);
        this.bombManager = new BombManager(map, this);
    }
    

    public void render()
    {
        //Render items
        itemManager.render();
        
        //Render players
        playerManager.render();
            
        //Renders bombs
        bombManager.render();
    }
    
    
    public void update()
    {
        //Update items
        itemManager.update();
        
        //Update players
        playerManager.update();
        
        //Update bombs
        bombManager.update();
    }
    
    
    /**--------------------GETTER & SETTER--------------------**/
    public ItemManager getItemManager()
    {
        return this.itemManager;
    }    
    
    public PlayerManager getPlayerManager()
    {
        return this.playerManager;
    }
    
    public BombManager getBombManager()
    {
        return this.bombManager;
    }
}
