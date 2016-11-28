/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.audio.Sound;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class Charge extends Bomb
{

    public Charge(ThinGridCoordinates pos, ThinGridCoordinates direction, int range, float explosionTime, float explosionDuration, float delayExplodeAfterHitByBomb, int playerId, MapLoader map, EntityManager entityManager) {
        super(pos, direction, range, explosionTime, explosionDuration, delayExplodeAfterHitByBomb, playerId, map, entityManager);
    }

    @Override
    protected void explode(Sound sound) {
    }

    @Override
    public void render() {
    }
    
}
