/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gu.entity.trap;
import com.badlogic.gdx.math.Vector2;
import gui.entity.Entity;;

/**
 *
 * @author Christian
 */
import gui.entity.EntityManager;
import gui.map.MapManager;/**
 *
 * @author Christian
 */
public class Trap extends Entity{

    public Trap(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager) {
        super(pos, direction, map, entityManager);
    }
    
    public void render()
    {
        
    }
}
