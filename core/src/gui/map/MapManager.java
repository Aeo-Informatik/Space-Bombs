/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.gdx.bomberman.Constants;


/**
 *
 * @author qubasa
 */
public class MapManager
{
    //General Objects and variables
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer blockLayer; 
    private TiledMapTileLayer floorLayer;
    private TiledMapTileLayer bombLayer;
    private TiledMapTileLayer itemLayer;
    
    //Constuctor
    public MapManager(OrthographicCamera camera)
    {
//        Parameters params = new Parameters();
//        params.textureMinFilter = TextureFilter.Nearest;
//        params.textureMagFilter = TextureFilter.Nearest;
        
        this.camera = camera;
        this.tiledMap = new TmxMapLoader().load(Constants.MAPPATH);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.blockLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Blocks");
        this.floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Floor");
        this.bombLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Bombs");
        this.itemLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Items");
        
        
        Constants.MAPTEXTUREWIDTH = blockLayer.getTileWidth();
        Constants.MAPTEXTUREHEIGHT = blockLayer.getTileWidth();
    }
    
    public void render() 
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
    
    
    /**-------------------MAP FUNCTIONS-------------------**/
    public boolean isCellBlocked(float x, float y)
    {
        TiledMapTileLayer.Cell cell = blockLayer.getCell((int) (x / Constants.MAPTEXTUREWIDTH ), (int) (y / Constants.MAPTEXTUREHEIGHT));
        //System.out.println("X: " + (int) (x / blockLayer.getTileWidth()) + " Y: " + (int) (y / blockLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("blocked");
    }
    
    public boolean isCellGhostBlocked(float x, float y)
    {
        TiledMapTileLayer.Cell cell = blockLayer.getCell((int) (x / Constants.MAPTEXTUREWIDTH), (int) (y / Constants.MAPTEXTUREHEIGHT));
        //System.out.println("X: " + (int) (x / blockLayer.getTileWidth()) + " Y: " + (int) (y / blockLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("ghost-blocked");
    }
    
    public boolean isBombPlaced(float x, float y)
    {
        TiledMapTileLayer.Cell cell = bombLayer.getCell((int) (x / Constants.MAPTEXTUREWIDTH), (int) (y / Constants.MAPTEXTUREHEIGHT));
        //System.out.println("X: " + (int) (x / blockLayer.getTileWidth()) + " Y: " + (int) (y / blockLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("bomb");
    }
    
    public boolean isCellDeadly(float x, float y)
    {
        TiledMapTileLayer.Cell cell = bombLayer.getCell((int) (x / Constants.MAPTEXTUREWIDTH), (int) (y / Constants.MAPTEXTUREHEIGHT));
        return cell != null && cell.getTile().getProperties().containsKey("deadly");
    }
    
    
    /**-------------------Getter & Setter-------------------**/
    public TiledMapTileLayer getBlockLayer()
    {
        return this.blockLayer;
    }
    
    public TiledMapTileLayer getBombLayer()
    {
        return this.bombLayer;
    }
    
    public TiledMapTileLayer getFloorLayer()
    {
        return this.floorLayer;
    }
    
    public TiledMapTileLayer getItemLayer()
    {
        return this.itemLayer;
    }
    
}
