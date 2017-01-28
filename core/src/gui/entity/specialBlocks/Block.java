package gui.entity.specialBlocks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import gui.AnimEffects;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;

/**
 * Created by qubasa on 28.01.17.
 */

public abstract class Block
{
    protected MapLoader map;
    protected TiledMapTileLayer blockLayer;
    protected MapCellCoordinates cellPos;
    protected EntityManager entityManager;
    protected AnimEffects animEffects;
    protected Animation blockAnim;
    protected boolean destroyed = false;

    public Block(Animation anim, MapCellCoordinates pos, MapLoader map, EntityManager entityManager)
    {
        this.cellPos = pos;
        this.blockAnim = anim;
        this.map = map;
        this.blockLayer = map.getBlockLayer();
        this.entityManager = entityManager;
        this.animEffects = new AnimEffects();
    }

    public abstract void render();

    public boolean isDestroyed()
    {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
        this.destroyed = destroyed;
    }

}
