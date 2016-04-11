/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import gui.camera.OrthoCamera;


/**
 *
 * @author qubasa
 */
public class MapManager
{
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private String mapPath;
    private OrthoCamera camera;
    
    public MapManager(OrthoCamera camera)
    {
        this.camera = camera;
        this.tiledMap = new TmxMapLoader().load(mapPath);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }
    
    public void render(SpriteBatch sb) 
    {
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
    }
    
    public void resize(int width, int height)
    {
        
    }
    
    public void dispose()
    {
        
    }
    
    //Getter & Setter
    public TiledMap getTiledMap()
    {
        return tiledMap;
    }
    
    //Getter & Setter
    public void setMap(String mapPath)
    {
        this.mapPath = mapPath;
    }
    
}
