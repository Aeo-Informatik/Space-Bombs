/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.gdx.bomberman.Constants;
import gui.entity.bomb.Bomb;
import gui.map.MapCellCoordinates;
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
    
    
    protected float entitySpeed = Constants.DEFAULTENTITYSPEED;
    
    
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
    
    public abstract void render();
    
    /**
     * Sets the entity movement direction to left controlled from entitySpeed.
     */
    protected void goLeft()
    {
        direction.set(-100 * entitySpeed * Constants.DELTATIME, 0);
    }
    
    
    /**
    * Sets the entity movement direction to right controlled from entitySpeed.
    */
    protected void goRight()
    {
        direction.set(100 * entitySpeed * Constants.DELTATIME, 0);
    }
    
    
    /**
    * Sets the entity movement direction to up controlled from entitySpeed.
    */
    protected void goUp()
    {
        direction.set(0, 100 * entitySpeed * Constants.DELTATIME);
    }
    
    
    /**
    * Sets the entity movement direction to down controlled from entitySpeed.
    */
    protected void goDown()
    {
        direction.set(0, -100 * entitySpeed * Constants.DELTATIME);
    }
    
    
    /**
    * Sets the entity movement direction to 0.
    */
    protected void stopMoving()
    {
        direction.set(0, 0);
    }
    
    /**------------------Getter & Setter------------------**/
    public ThinGridCoordinates getPosition()
    {
        return pos;
    }
    
    public void setPosition(ThinGridCoordinates pos)
    {
        this.pos = pos;
    }
    
    public void setEntitySpeed(float entitySpeed)
    {
        this.entitySpeed = entitySpeed;
    }
    
    public float getEntitySpeed()
    {
        return this.entitySpeed;
    }
}

