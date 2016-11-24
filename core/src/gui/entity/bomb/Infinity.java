/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;


import com.badlogic.gdx.utils.Array;
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
public class Infinity extends Bomb
{
    private int explodePath = -1;
    private Array <InfinitySplitBomb> splittedExplosion = new Array<>();
    
    //Variables
    private int wave = 0;
    
    public Infinity(ThinGridCoordinates pos, ThinGridCoordinates direction, int range, int playerId, MapLoader map, EntityManager entityManager) 
    {
        super(pos, direction, range, 2, 0.5f, 0.5f, playerId, map, entityManager);
        super.setBombAnimation(TextureManager.infinityAnim);
        this.explodePath = new Random().nextInt(6) +1; // From 1 .. 6
        explodePath = 1;
    }
    
    
    @Override
    protected void explode() 
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
        if(explodePath == 1)
        {
            if(wave < 5)
            {
                 // Check if one bomb exploded and get its explosion coordinate endings
                MapCellCoordinates[] cellCoordinates = getExplosionEndings();
                
                // Check if position array is empty
                if (cellCoordinates != null) 
                {
                    for (MapCellCoordinates cellCoordinate : cellCoordinates) 
                    {
                        // Check if current position in array has no coordinate
                        if(cellCoordinate != null)
                        {
                            // The first coordinate found will spawn a bomb at that position
                            splittedExplosion.add(new InfinitySplitBomb(new ThinGridCoordinates(cellCoordinate), direction, explosionRange, playerId, map, entityManager));
                            wave++;
                            break;
                        }
                    }
                }
            }else
            {
                updateSplitBombs();
                
                if(splittedExplosion.size == 0)
                {
                    this.isExploded = true;
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
    }
    
    private MapCellCoordinates[] getExplosionEndings()
    {
        //Deletes bombs if exploded
        for (int i=0; i < splittedExplosion.size; i++)
        {
            if(this.splittedExplosion.get(i).isExploded())
            {
                MapCellCoordinates[] cellCoordinates = splittedExplosion.get(i).getNextBombPosArray();
                splittedExplosion.removeIndex(i);
                
                return cellCoordinates;
            }
        }
        return null;
    }
   

}

