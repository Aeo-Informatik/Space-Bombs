/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.players;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class Player extends Entity
{
    public Player(Vector2 pos, Vector2 direction, MapManager map, EntityManager entityManager, OrthographicCamera camera)
    {
        super(pos, direction, map, entityManager, camera);
    }
}
