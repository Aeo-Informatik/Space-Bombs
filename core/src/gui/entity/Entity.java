/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.gdx.bomberman.Constants;

import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public abstract class Entity
{
    //General objects & variables
    protected ThinGridCoordinates pos, direction;
    protected MapLoader map;
    protected TiledMapTileLayer blockLayer;
    protected TiledMapTileLayer bombLayer;
    protected EntityManager entityManager;
    protected SpriteBatch renderObject;
    
    // Direction
    protected int entitySpeed = Constants.DEFAULTENTITYSPEED;
    protected String playerOrientation = Constants.LEFT;
    
    
    //The first parameter is the image that should be drawn the second one is the position x, y
    //and the third is the movement direction and speed in which the texture moves x,y.
    public Entity(ThinGridCoordinates pos, ThinGridCoordinates direction, MapLoader map, EntityManager entityManager){
        
        this.pos = pos;
        this.map = map;
        this.direction = direction;
        this.blockLayer = map.getBlockLayer();
        this.bombLayer = map.getBombLayer();
        this.entityManager = entityManager;
        this.renderObject = new SpriteBatch();
    }
    
    public Entity(ThinGridCoordinates pos, MapLoader map, EntityManager entityManager){
        
        this.pos = pos;
        this.map = map;
        this.direction = new ThinGridCoordinates(0,0);
        this.blockLayer = map.getBlockLayer();
        this.bombLayer = map.getBombLayer();
        this.entityManager = entityManager;
        this.renderObject = new SpriteBatch();
    }
    
    public abstract void render();
    
    
    /**------------------Getter & Setter------------------**/
    public ThinGridCoordinates getPosition()
    {
        return pos;
    }
    
    public void setPosition(ThinGridCoordinates pos)
    {
        this.pos = pos;
    }
    
    public void setEntitySpeed(int entitySpeed)
    {
        this.entitySpeed = entitySpeed;
    }
    
    public int getEntitySpeed()
    {
        return this.entitySpeed;
    }
}

