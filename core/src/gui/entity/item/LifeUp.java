/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;


/**
 *
 * @author Christian
 */
public class LifeUp extends Item{
    
    //Objects
    private final TextureRegion lifeUp;

    //Constructor
    public LifeUp(int cellX, int cellY, Vector2 direction, MapManager map, EntityManager entityManager) 
    {
        super(cellX, cellY, direction, map, entityManager);
        this.lifeUp = TextureManager.lifeUp;
    }
   
    public void render(SpriteBatch renderObject)
    {     
        //Render item
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(new StaticTiledMapTile(lifeUp));
        cell.getTile().getProperties().put("lifeUp", null);
        map.getItemLayer().setCell(cellX, cellY, cell);
        
        //Check if main player is alive
        if(entityManager.getMainPlayer() != null)
        {
            if(entityManager.getMainPlayer().getLife() < Constants.maxLife)
            {
               int id = getPlayerCollectingItem();
                if(id != -1)
                {
                    if(isMainPlayer(id) == true)
                    {
                        itemEffect();
                    }
                }
            }
        }
    }
    
    @Override
    public void itemEffect()
    {
        //Check if main player is alive
        if(entityManager.getMainPlayer() != null)
        {
            entityManager.getMainPlayer().setLife((entityManager.getMainPlayer().getLife() + 1));
        }
    }

    
}
