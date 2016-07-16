/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdx.bomberman.Constants;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class Player extends Entity
{
    //Objects
    protected OrthographicCamera camera;
    
    //Variables
    protected float cameraSpeed = Constants.DEFAULTCAMERASPEED;
    
    //Constructor
    public Player(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager, OrthographicCamera camera)
    {
        super(pos, direction, map, entityManager);
        
        this.camera = camera;
    }
    
    /**------------------FUNCTIONS------------------**/
    /**
    * Follows the entity with smooth camera movements.
    */
    protected void cameraFollowPlayer(Vector2 pos)
    {
        Vector3 cameraPos = camera.position;
        cameraPos.x += (pos.x - cameraPos.x) * cameraSpeed * Constants.DELTATIME;
        cameraPos.y += (pos.y - cameraPos.y) * cameraSpeed * Constants.DELTATIME;
    }
    
    
    /**------------------GETTER & SETTER------------------**/
    public void setCameraFollowSpeed(float cameraSpeed)
    {
        this.cameraSpeed = cameraSpeed;
    }
    
    public float getCameraFollowSpeed()
    {
        return this.cameraSpeed;
    }
}
