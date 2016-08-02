/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.item;

import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.player.MainPlayer;
import gui.map.MapManager;


/**
 *
 * @author Christian
 */
public class Teleport extends Item{
    
    public String Discription = "You get Teleportet to your Spawn-Point";

    //Constructor
    public Teleport(int cellX, int cellY, MapManager map, EntityManager entityManager) {
        super(cellX, cellY,TextureManager.emptyBlock, map, entityManager);
    }
    
    @Override
    public void itemEffect()
    {
         MainPlayer mainP = entityManager.getPlayerManager().getMainPlayer();
        
        //Check if main player is alive
        if(mainP != null)
        {
            for(int mapY=0; mapY < map.getFloorLayer().getHeight(); mapY++)
        {
            for(int mapX=0; mapX < map.getFloorLayer().getWidth(); mapX++)
            {
                try
                {
                    if(map.getFloorLayer().getCell(mapX, mapY).getTile().getProperties().containsKey("Spawn-P" + mainP.getPlayerId()))
                    {
                        mainP.setPosition(new Vector2 (mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT ));
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
        }
    }
    
    @Override
    public boolean canGetCollectedByMainPlayer ()
    {
        return true;
    }
    
}
