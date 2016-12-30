/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;

import java.util.ArrayList;

import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;


/**
 *
 * @author qubasa
 */
public class Infinity extends Bomb
{
    private int explodePath = -1;
    private Array <InfinitySplitBomb> splittedExplosion = new Array<>();
    protected String description = "Replicates itself randomly"; 
    private int numberOfReplications = 0;
    
    public Infinity(ThinGridCoordinates pos, int playerId, int range, int explodePath, MapLoader map, EntityManager entityManager) 
    {
        super(new ThinGridCoordinates(-999,-999), playerId, range, Constants.INFINITYBOMBEXPLOSIONTIME, Constants.INFINITYBOMBEXPLOSIONDURATION, Constants.INFINITYBOMBDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);
        
        
        // Choosing explosion path
        this.explodePath = explodePath;
        
        // Set first bomb
        splittedExplosion.add(new InfinitySplitBomb(pos, playerId, explosionRange, map, entityManager));
    }
    
    
    @Override
    public void render()
    {
        //Render bomb 
        for (Bomb bomb: splittedExplosion)
        {
            bomb.render();
        }

        // First possible explosion path
        switch (explodePath) 
        {
            case 1:
                if(numberOfReplications < 6) 
                {
                    replicate(3);
                }else if(numberOfReplications < 9)
                {
                    replicate(2);
                }
                break;
            case 2:
                if(numberOfReplications < 6) 
                {
                    replicate(2);
                }else if(numberOfReplications < 7)
                {
                    replicate(1);
                }else if(numberOfReplications < 9)
                {
                    replicate(3);
                }
                break;
            case 3:
                if(numberOfReplications < 2) 
                {
                    replicate(2);
                }else if(numberOfReplications < 5)
                {
                    replicate(3);
                }else if(numberOfReplications < 7)
                {
                    replicate(1);
                }else if(numberOfReplications < 9)
                {
                    replicate(3);
                }
                break;
            case 4:
                if(numberOfReplications < 9)
                {
                    replicate(4);
                }
                break;
        }
        
        updateSplitBombs();
    }
    
    private void replicate(int maxBombsConcurrently)
    {
        assert(maxBombsConcurrently >= 1 || maxBombsConcurrently <= 4);
        
        // Get bombs explosion coordinate endings
        ArrayList<MapCellCoordinates> cellCoordinates = getExplosionEndings();

        // Check if bomb didn't explode till now
        if (cellCoordinates != null)
        {
            // Go through all possible bomb pos coordiantes
            for (MapCellCoordinates cellCoordinate : cellCoordinates)
            {
                // Only set new bomb if there aren't more than 2 bombs already there
                if(splittedExplosion.size < maxBombsConcurrently)
                {
                    splittedExplosion.add(new InfinitySplitBomb(new ThinGridCoordinates(cellCoordinate), playerId, explosionRange, map, entityManager));
                    numberOfReplications++;
                }
            }
        }
    }
    
    private void updateSplitBombs()
    {
        //Deletes bombs if exploded
        for (int i=0; i < splittedExplosion.size; i++)
        {
            if(this.splittedExplosion.get(i).isExploded())
            {
                splittedExplosion.removeIndex(i);
            }
        }
       
        if(splittedExplosion.size == 0)
        {
            this.isExploded = true;
        } 
    }
    
    private ArrayList<MapCellCoordinates> getExplosionEndings()
    {
        //Deletes bombs if exploded
        for (int i=0; i < splittedExplosion.size; i++)
        {
            if(this.splittedExplosion.get(i).isExploded())
            {
                ArrayList<MapCellCoordinates> cellCoordinates = splittedExplosion.get(i).getNextBombPosArray();
                splittedExplosion.removeIndex(i);
                
                return cellCoordinates;
            }
        }
        return null;
    }
   
    @Override
    protected void explode(Sound sound) 
    {
        // Nothing to do here o.O
    }
    
}

