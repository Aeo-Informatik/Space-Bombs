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
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;

import client.SendCommand;


/**
 *
 * @author qubasa
 */
public class MapLoader
{
    //Objects
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer blockLayer; 
    private TiledMapTileLayer floorLayer;
    private TiledMapTileLayer bombLayer;
    private TiledMapTileLayer itemLayer;
    private SendCommand sendCommand;
    
    //Variables
    private Array<ThinGridCoordinates> itemSpawnerPositions = new Array<>(); // In entity positions
    
    //Constuctor
    public MapLoader(OrthographicCamera camera, SendCommand sendCommand)
    {
        this.camera = camera;
        this.tiledMap = new TmxMapLoader().load(Constants.TESTMAPPATH);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        this.blockLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Blocks");
        this.floorLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Floor");
        this.bombLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Bombs");
        this.itemLayer = (TiledMapTileLayer) tiledMap.getLayers().get("Items");
        this.sendCommand = sendCommand;
        
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
    public boolean isCellBlocked(MapCellCoordinates cellCoordiantes)
    {
        TiledMapTileLayer.Cell cell = blockLayer.getCell(cellCoordiantes.getX(), cellCoordiantes.getY());
        return cell != null && cell.getTile().getProperties().containsKey("blocked");
    }
    
    public boolean isCellGhostBlocked(MapCellCoordinates cellCoordiantes)
    {
        TiledMapTileLayer.Cell cell = blockLayer.getCell(cellCoordiantes.getX(), cellCoordiantes.getY());
        return cell != null && cell.getTile().getProperties().containsKey("ghost-blocked");
    }
    
    public boolean isBombPlaced(MapCellCoordinates cellCoordiantes)
    {
        TiledMapTileLayer.Cell cell = bombLayer.getCell(cellCoordiantes.getX(), cellCoordiantes.getY());
        return cell != null && cell.getTile().getProperties().containsKey("bomb");
    }
    
    public boolean isCellDeadly(MapCellCoordinates cellCoordiantes)
    {
        TiledMapTileLayer.Cell cell = bombLayer.getCell(cellCoordiantes.getX(), cellCoordiantes.getY());
        return cell != null && cell.getTile().getProperties().containsKey("deadly");
    }
    
    private void findAllItemFields()
    {
        itemSpawnerPositions.clear();
        
        for(int mapY=0; mapY < floorLayer.getHeight(); mapY++)
        {
            for(int mapX=0; mapX < floorLayer.getWidth(); mapX++)
            {
                try
                {
                    if(floorLayer.getCell(mapX, mapY).getTile().getProperties().containsKey("Item-Spawner"))
                    {
                        itemSpawnerPositions.add(new ThinGridCoordinates(mapX, mapY));
                    }
                }catch(NullPointerException e)
                {

                }
            }
        }
        
        //Send to server the amount of item fields
        sendCommand.registerItemFields(itemSpawnerPositions.size);
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
    
    public Array<ThinGridCoordinates> getSpawnerPositionsArray()
    {
        return this.itemSpawnerPositions;
    }
    
}
