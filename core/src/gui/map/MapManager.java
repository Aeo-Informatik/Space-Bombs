/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;

/**
 *
 * @author qubasa
 */
public class MapManager 
{
    public static Map currentMap;
    
    public static void setMap(Map map){
        
        if(map != null)
        {
            currentMap.dispose();
        }
        
        currentMap = map;
        currentMap.create();
    }
    
    
    public static Map getCurrentMap()
    {
        return currentMap;
    }   
}
