package gui.entity.specialBlocks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import gui.TextureManager;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import sun.management.ThreadInfoCompositeData;

/**
 * Created by qubasa on 27.01.17.
 */

public class PoisonGas extends Block
{

    public PoisonGas(MapCellCoordinates pos, MapLoader map, EntityManager entityManager)
    {
        super(TextureManager.poisonGasAnim, pos, map, entityManager);
    }

    @Override
    public void render()
    {
        if(!map.isCellBlocked(cellPos))
        {
            //Create new cell and set its animation texture
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(animEffects.getFrame(blockAnim, true)));
            cell.getTile().getProperties().put("poisonGas", null);

            //Set texture into block layer
            blockLayer.setCell(super.cellPos.getX(), super.cellPos.getY(), cell);
        }else
        {
            super.destroyed = true;
        }
    }
}
