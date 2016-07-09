/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;


/**
 *
 * @author qubasa
 */
public class MapManager
{
    //Objects
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer blockLayer; 
    private TiledMapTileLayer floorLayer;
    private TiledMapTileLayer bombLayer;
    private TiledMapTileLayer itemLayer;
    
    //Variables
    private Array<Vector2> itemSpawnerPositions = new Array<>(); // In entity positions
    
    //Constuctor
    public MapManager(OrthographicCamera camera)
    {
        this.camera = camera;
        this.tiledMap = new TmxMapLoader().load(Constants.MAPPATH);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.blockLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Blocks");
        this.floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Floor");
        this.bombLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Bombs");
        this.itemLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Items");

        Constants.MAPTEXTUREWIDTH = blockLayer.getTileWidth();
        Constants.MAPTEXTUREHEIGHT = blockLayer.getTileWidth();
        
        findAllItemFields();
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
    
    private void findAllItemFields()
    {
        for(int mapY=0; mapY < floorLayer.getHeight(); mapY++)
        {
            for(int mapX=0; mapX < floorLayer.getWidth(); mapX++)
            {
                try
                {
                    if(floorLayer.getCell(mapX, mapY).getTile().getProperties().containsKey("Item-Spawner"))
                    {
                        itemSpawnerPositions.add(new Vector2(mapX * Constants.MAPTEXTUREWIDTH, mapY * Constants.MAPTEXTUREHEIGHT));
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
    }
    
    /**-------------------Getter & Setter-------------------**/
    public void setNewMap(String path)
    {
        this.tiledMap = new TmxMapLoader().load(path);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        this.blockLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Blocks");
        this.floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Floor");
        this.bombLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Bombs");
        this.itemLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Items");

        Constants.MAPTEXTUREWIDTH = blockLayer.getTileWidth();
        Constants.MAPTEXTUREHEIGHT = blockLayer.getTileWidth();
        
        findAllItemFields();
    }
    
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
    
    public Array<Vector2> getSpawnerPositionsArray()
    {
        return this.itemSpawnerPositions;
    }
    
}
