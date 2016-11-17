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
    private int currentMap = 0;
    
    public AvailableMaps()
    {
        // First added Map is always the test map
        addMap("Test-Map_(26x26)");
        
        // Normal maps
        addMap("The-Iron-Block_(25x25)");
    }
    
    public final void addMap(String name)
    {
        mapList.add("maps/" + name + ".tmx");
    }
    
    public void nextMap()
    {
        if(currentMap < mapList.size())
        {
            currentMap++;
        }else
        {
            currentMap = 1;
        }
    }
    
    public void previousMap()
    {
        if(currentMap > 0)
        {
            currentMap++;
        }else
        {
            currentMap = mapList.size() -1;
        }
    }
    
    public String getCurrentMap()
    {
        return mapList.get(currentMap);
    }
    
    public String getMap(int i)
    {
        return mapList.get(i);
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
