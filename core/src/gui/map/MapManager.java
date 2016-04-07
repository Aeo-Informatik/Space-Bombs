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
    public static Map currentMap = null;
    
    public static void setMap(Map map){
        
        if(currentMap != null)
        {
            currentMap.dispose();
        }
        
        currentMap = map;
    }
    
    
    public static Map getCurrentMap()
    {
        return currentMap;
    }   
}
