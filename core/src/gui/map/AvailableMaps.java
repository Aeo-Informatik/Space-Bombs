package gui.map;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class AvailableMaps 
{
    // Filled with the path of the maps
    private final ArrayList<Map> mapList = new ArrayList<>();
    private int currentMap = 0;
  
    private int filterByPlayerNumber;
    
    public AvailableMaps(int fileterByPlayerNumber)
    {
        this.filterByPlayerNumber = fileterByPlayerNumber;
        
        // Normal maps (name, supported player number)
        addMap("Square-Dungeon",2,3,4);
        addMap("The-Cross", 2,3,4);
        addMap("The-Iron-Block_(23x23)", 2, 4);
        addMap("The_Circle", 2,3,4);
        addMap("Center-Brawl_(21x25)",2,3,4);
        addMap("TriangleMap_(43x21)", 3);
        addMap("City(21x25)", 2,3,4);
        addMap("Tunnel_(43x21)", 2);
        addMap("The_Tournament",2,3,4);
        
        
    }
    
    public int searchMapByName(String mapName)
    {
        for(int i=0; i < mapList.size(); i++)
        {
            
            if(mapList.get(i).getPath().contains(mapName))
            {
                return i;
            }
        }
        
        return -1;
    }
    
    private void addMap(String mapName, Integer... numberOfPlayers)
    {
        int[] playerNumber = new int[numberOfPlayers.length +1];

        // Resembling filter setting "ALL" maps
        playerNumber[0] = -1; 
        
        // Copying Integer array to int array
        for(int i=1; i < playerNumber.length; i++)
        {
            playerNumber[i] = numberOfPlayers[i-1];
        }
        
        // Creating map object and save it in arrayList
        mapList.add(new Map(mapName, playerNumber, "maps/" + mapName + ".tmx", "maps/preview/" + mapName +".png"));
    }
    
    public void goToNextCompatibleMap()
    {
        for(int numberOfPlayers : mapList.get(currentMap).getNumberOfPlayers())
        {
            if(getFilterByPlayerNumber() == numberOfPlayers)
            {
                return;
            }
        }
        nextMap();
    }
    
    public boolean isCurrentMapCompatible(int currentPlayers)
    {
        for(int numberOfPlayers : mapList.get(currentMap).getNumberOfPlayers())
        {
            if(currentPlayers == numberOfPlayers)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public void nextMap()
    {
        if(currentMap < mapList.size()-1)
        {
            currentMap++;
            for(int numberOfPlayers : mapList.get(currentMap).getNumberOfPlayers())
            {
                if(getFilterByPlayerNumber() == numberOfPlayers)
                {
                    return;
                }
            }
            
            nextMap();
        }else
        {
            currentMap = 0;
            for(int numberOfPlayers : mapList.get(currentMap).getNumberOfPlayers())
            {
                if(getFilterByPlayerNumber() == numberOfPlayers)
                {
                    return;
                }
            }
            
            nextMap();
        }
    }
    
    
    
    public void previousMap()
    {
        if(currentMap > 0)
        {
            currentMap--;
            for(int numberOfPlayers : mapList.get(currentMap).getNumberOfPlayers())
            {
                if(getFilterByPlayerNumber() == numberOfPlayers)
                {
                    return;
                }
            }
            
            previousMap();
        }else
        {
            currentMap = mapList.size() -1;
            for(int numberOfPlayers : mapList.get(currentMap).getNumberOfPlayers())
            {
                if(getFilterByPlayerNumber() == numberOfPlayers)
                {
                    return;
                }
            }
            
            previousMap();
        }
    }
    
    public String getCurrentMapPreviewPath()
    {
        return  mapList.get(currentMap).getPreview();
    }
    
    public String getCurrentMapName()
    {
        return mapList.get(currentMap).getName();
    }
    
    public String getCurrentMap()
    {
        return mapList.get(currentMap).getPath();
    }
    
    public int getNumberOfMaps()
    {
        return mapList.size();
    }

    /**
     * @return the filterByPlayerNumber
     */
    public int getFilterByPlayerNumber() {
        return filterByPlayerNumber;
    }

    /**
     * @param filterByPlayerNumber the filterByPlayerNumber to set
     */
    public void setFilterByPlayerNumber(int filterByPlayerNumber) {
        this.filterByPlayerNumber = filterByPlayerNumber;
    }

    /**
     * @param currentMap the currentMap to set
     */
    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }
    
}

