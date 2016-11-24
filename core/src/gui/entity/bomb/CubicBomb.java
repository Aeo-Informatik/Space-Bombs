/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.bomb;

import gui.entity.EntityManager;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author cb0703
 */
public abstract class CubicBomb extends Bomb{

    public CubicBomb(ThinGridCoordinates pos, ThinGridCoordinates direction, int range, int explosionTime, float explosionDuration, float delayExplodeAfterHitByBomb, int playerId, MapLoader map, EntityManager entityManager) {
        super(pos, direction, range, explosionTime, explosionDuration, delayExplodeAfterHitByBomb, playerId, map, entityManager);
    }
   
    @Override
    public abstract void render();
          
    @Override
    public void explode(){
        //cubic explosion for barrel and dynamite
    }
    
}
