/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;

import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class AvailableMaps 
{
    // Filled with the path of the maps
    private final ArrayList<String> mapList = new ArrayList<>();
    private int currentMap = 1;
    
    public AvailableMaps()
    {
        // First added Map is always the test map
        addMap("Test-Map_(24x24)");
        
        // Normal maps
        addMap("The-Iron-Block_(23x23)");
        addMap("Center-Brawl_(21x25)");
        addMap("TriangleMap_(43x21)");
    }
    
    public final void addMap(String name)
    {
        mapList.add("maps/" + name + ".tmx");
    }
    
    public void nextMap()
    {
        if(currentMap < mapList.size()-1)
        {
            currentMap++;
        }else
        {
            currentMap = 1;
        }
    }
    
    public void previousMap()
    {
        if(currentMap > 1)
        {
            currentMap--;
        }else
        {
            currentMap = mapList.size() -1;
        }
    }
    
    public String getCurrentMapPreviewPath()
    {
        return "maps/preview/" + mapList.get(currentMap).replaceAll("maps/", "").replaceAll(".tmx", "") + ".png";
    }
    
    public String getCurrentMapName()
    {
        return mapList.get(currentMap).replaceAll("maps/", "").replaceAll(".tmx", "").replaceAll("-", " ").replaceAll("_", " ");
    }
    
    public String getCurrentMap()
    {
        return mapList.get(currentMap);
    }
    
    public int getNumberOfMaps()
    {
        return mapList.size();
    }
    
    public String getTestMap()
    {
        return mapList.get(0);
    }
}
