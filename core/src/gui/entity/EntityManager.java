/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import gui.entity.players.EnemyPlayer;
import gui.entity.players.Spectator;
import gui.entity.players.MainPlayer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import gui.entity.bombs.Bomb;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.entity.bombs.BombManager;
import gui.map.MapManager;
import gui.entity.items.ItemManager;
import gui.entity.players.PlayerManager;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Objects
    //NOTE: Array from libgdx is much faster in comparison to an arraylist
    private OrthographicCamera camera;
    private MapManager map;
    
    //Managers
    private ItemManager itemManager;
    private PlayerManager playerManager;
    private BombManager bombManager;

    //Constructor
    public EntityManager(OrthographicCamera camera, MapManager map)
    {
        this.camera = camera;
        this.map = map;
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
