/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import client.SendCommand;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import java.util.Random;

/**
 *
 * @author qubasa
 */
public class Infinity extends NormalBomb
{
    private int chance;
    
    public Infinity(ThinGridCoordinates pos, ThinGridCoordinates direction, int range, int playerId, int chance, MapLoader map, EntityManager entityManager) 
    {
        super(pos, direction, range, playerId, map, entityManager);
        super.setBombAnimation(TextureManager.infinityAnim);
        this.chance = chance;
        super.description = "No coins but sometimes reproduces itselves when a block gets destoyed";
    }
    
    @Override
    public void dropFromBlock(MapCellCoordinates localCellPos)
    {
        int randomNum = new Random().nextInt(10) +1;//Possible output: 1, 2...10  
        
        if(randomNum <= chance)
        {    
            chance -= Constants.INFINITYREPRODUCTIONDECREASE;

            // Gucke ob der main Player nicht tot ist und ob dort schon eine Bombe ist und ob eine weitere bombe der Spieler überhaupt legen kann
            if( entityManager.getPlayerManager().getMainPlayer() != null && !map.isBombPlaced(localCellPos) )
            {   
                entityManager.getBombManager().spawnInfinity(new ThinGridCoordinates(localCellPos), playerId, super.getRange(), chance);
                
                // Send bomb command to server
                SendCommand.placeBomb(playerId, pos, "infinity");
            }
        }
    }
}
