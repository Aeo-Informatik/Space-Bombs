package gui.entity.specialBlocks;

import com.badlogic.gdx.graphics.g2d.Animation;

import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import sun.management.ThreadInfoCompositeData;

/**
 * Created by qubasa on 27.01.17.
 */

public class PoisonGas extends Entity
{

    private Animation blockAnim;


    public PoisonGas(MapCellCoordinates pos, MapLoader map, EntityManager entityManager)
    {
        super(new ThinGridCoordinates(pos), map, entityManager);

    }

    @Override
    public void render()
    {

    }
}
