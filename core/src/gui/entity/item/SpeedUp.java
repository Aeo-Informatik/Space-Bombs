/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.item;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapManager;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.gdx.bomberman.Constants;

/**
 *
 * @author cb0703
 */
public class SpeedUp extends Item{

    public SpeedUp(int CellX, int CellY, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(CellX, CellY, direction,TextureManager.speedUp, map, entityManager);
    }
    
    /**
     * do wahat the item does
     */
    @Override
    public void itemEffect()
    {
        entityManager.getMainPlayer().setSpeed((entityManager.getMainPlayer().getSpeed()+ 0.1f));
    }
    
    @Override
    public boolean canGetCollectedByMainPLayer()
    {
        if(entityManager.getMainPlayer().getSpeed()< Constants.MAXSPEED)
        {
            return true;
        }
        return false;
    }
}
