/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import java.util.ArrayList;


/**
 *
 * @author qubasa
 */
public class Infinity extends Bomb
{
    private int explodePath = -1;
    private Array <InfinitySplitBomb> splittedExplosion = new Array<>();
    
    //Variables
    private int wave = 0;
    
     protected String description = "Replicates itself randomly but has a 2 times lower coin drop rate"; 
    
    public Infinity(ThinGridCoordinates pos, ThinGridCoordinates direction, int range, int playerId, MapLoader map, EntityManager entityManager, int explodePath) 
    {
        super(pos, direction, range, Constants.INFINITYBOMBEXPLOSIONTIME, Constants.INFINITYBOMBEXPLOSIONDURATION, Constants.INFINITYBOMBDELAYEXPLODEAFTERHITBYBOMB, playerId, map, entityManager);
        super.setBombAnimation(TextureManager.infinityAnim);
        this.explodePath = explodePath;
    }
    
    
    @Override
    protected void explode(Sound sound) 
    {
        // Nothing to do here o.O
    }
    
    
     @Override
    public void render()
    {
        if(wave == 0)
        {
            splittedExplosion.add(new InfinitySplitBomb(pos, direction, explosionRange, playerId, map, entityManager));
            wave++;
        }
        
        //Render bomb 
        for (Bomb bomb: splittedExplosion)
        {
            bomb.render();
        }

        // First possible explosion path
        switch (explodePath) 
        {
            case 1:
                if(wave < 4) 
                {
                    // Get bombs explosion coordinate endings
                    ArrayList<MapCellCoordinates> cellCoordinates = getExplosionEndings();
                    
                    // Check if bomb didn't explode till now
                    if (cellCoordinates != null)
                    {
                        // Go through all possible bomb pos coordiantes
                        for (MapCellCoordinates cellCoordinate : cellCoordinates)
                        {
                            // Spawn new bomb
                            splittedExplosion.add(new InfinitySplitBomb(new ThinGridCoordinates(cellCoordinate), direction, explosionRange, playerId, map, entityManager));
                            break;
                        }
                        wave++;
                    }
                }else
                {
                    updateSplitBombs();
                }   
                break;
            case 2:
                if(wave < 2) 
                {
                    // Check if one bomb exploded and get its explosion coordinate endings
                    ArrayList<MapCellCoordinates> cellCoordinates = getExplosionEndings();
                    
                    // Check if bomb didn't explode till now
                    if (cellCoordinates != null)
                    {
                        for (MapCellCoordinates cellCoordinate : cellCoordinates)
                        {
                            // The first coordinate found will spawn a bomb at that position
                            splittedExplosion.add(new InfinitySplitBomb(new ThinGridCoordinates(cellCoordinate), direction, explosionRange, playerId, map, entityManager));
                            break;
                        }
                        wave++;
                    }
                }else if(wave < 6) 
                {
                     // Get bombs explosion coordinate endings
                    ArrayList<MapCellCoordinates> cellCoordinates = getExplosionEndings();
                    
                    // Check if bomb didn't explode till now
                    if (cellCoordinates != null)
                    {
                        // Go through all possible bomb pos coordiantes
                        for (MapCellCoordinates cellCoordinate : cellCoordinates)
                        {
                            // Only set new bomb if there aren't more than 2 bombs already there
                            if(splittedExplosion.size < 3)
                            {
                                splittedExplosion.add(new InfinitySplitBomb(new ThinGridCoordinates(cellCoordinate), direction, explosionRange, playerId, map, entityManager));
                            }
                        }
                        wave++;
                    }
                }else
                {
                    updateSplitBombs();
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
   

}

