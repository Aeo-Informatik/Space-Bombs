/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 *
 * @author qubasa
 */
public class MapObjectRenderer extends OrthogonalTiledMapRenderer
{
    private SpriteBatch sb;
    
    public MapObjectRenderer(TiledMap map, SpriteBatch sb) {
        super(map);
        this.sb = sb;
    }
    
    @Override
    public void renderObject(MapObject mapObject) 
    {
        
        if(mapObject instanceof TextureMapObject) 
        {
            //Cast parameter MapObject to TextureMapObject
            TextureMapObject textureObject = (TextureMapObject) mapObject;
            
            sb.draw(textureObject.getTextureRegion(), textureObject.getX(), textureObject.getY());
        }
    }
    
}
