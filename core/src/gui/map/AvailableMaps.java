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
    
    public AvailableMaps()
    {
        // First added Map is always the default map
        addMap("Test-Map_(26x26)");
        addMap("The-Iron-Block_(25x25)");
    }
    
    public final void addMap(String name)
    {
        mapList.add("maps/" + name + ".tmx");
    }
    
    public ArrayList<String> getMapList()
    {
        return mapList;
    }
    
}
