/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.math.Vector2;
import gui.entity.EntityManager;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class NormalBomb extends Bomb
{
    
    public NormalBomb(Vector2 pos, Vector2 direction, MapManager map, int playerId, int range, EntityManager entityManager) 
    {
        super(pos, direction, map, playerId, range, entityManager);
    }
    
}
