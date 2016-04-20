/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import gui.Constants;
import gui.camera.OrthoCamera;
import com.badlogic.gdx.maps.MapObjects;


/**
 *
 * @author qubasa
 */
public class MapManager
{
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthoCamera camera;
    private TiledMapTileLayer blockLayer; 
    private TiledMapTileLayer floorLayer;
    private MapObjects entityLayer;
    
    public MapManager(OrthoCamera camera)
    {
        this.camera = camera;
        this.tiledMap = new TmxMapLoader().load(Constants.MAPPATH);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.blockLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Blocks");
        this.floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Floor");
        this.entityLayer =  tiledMap.getLayers().get("Entities").getObjects();
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
    public TiledMapTileLayer getBlockLayer()
    {
        return this.blockLayer;
    }
    
    public MapObjects getEntityLayer()
    {
        return this.entityLayer;
    }
    
    public TiledMapTileLayer getFloor()
    {
        return this.floorLayer;
    }
}
