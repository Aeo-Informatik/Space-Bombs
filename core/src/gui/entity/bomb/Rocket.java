/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.audio.Sound;
import com.gdx.bomberman.Constants;
import gui.entity.EntityManager;
import gui.entity.player.MainPlayer;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class Rocket extends Bomb{
    
    private MainPlayer mainP;
    String faceingDirection = "";
    
    
    public Rocket(ThinGridCoordinates pos, ThinGridCoordinates direction, int range,int playerId, MapLoader map, EntityManager entityManager) {
        super(pos, direction, range, Constants.ROCKETEXPLOSIONTIME, Constants.ROCKETEXPLOSIONDURATION, Constants.ROCKETDELAYEXPLODEAFTERHITBYBOMB, playerId, map, entityManager);
    }

    @Override
    public void render() 
    {
        mainP = entityManager.getPlayerManager().getMainPlayer();
        
        renderObject.begin();
        renderObject.draw(animEffects.getFrame(bombAnim), pos.getX(), pos.getY());
        renderObject.end();
        
        if(mainP != null)
        {
            if(faceingDirection.isEmpty())
            {
                faceingDirection = mainP.getLastMovementKeyPressed();    
            }
            
            switch(faceingDirection)
            {
                case "UP":
                    super.goUp();
                    break;

                case "DOWN":
                    super.goDown();
                    break;

                case "LEFT":
                    super.goLeft();
                    break;

                case "RIGHT":
                    super.goRight();
                    break;
            }
        }
        
                    

    }
    
    @Override
    protected void explode(Sound sound) {
    }


    
}
