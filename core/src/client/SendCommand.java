/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import static com.gdx.bomberman.Main.client;
import gui.map.MapCellCoordinates;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class SendCommand
{

    public static void registerItemFields(int numberOfItemFields)
    {
        client.sendData("registerItemFields|" + numberOfItemFields + "|SERVER");
    }
    
    public static void setPlayerCoins(int playerId, int coins)
    {
        client.sendData("enemyPlayerSetCoins|" + playerId + "|" + coins + "|*");
    }
    
    public static void playerDied(int playerId, MapCellCoordinates cell)
    {
        client.sendData("enemyPlayerDied|" + playerId + "|" + cell.getX() + "|" + cell.getY() + "|*");
    }
    
    public static void setPlayerLife(int playerId, int life)
    {
        client.sendData("enemyPlayerLife|" + playerId + "|" + life + "|*");
    }
    
    public static void stopMoving(int playerId, ThinGridCoordinates pos)
    {
        client.sendData("stopEnemyPlayer|" + playerId + "|" + pos.getX() + "|" + pos.getY() + "|*");
    }
    
    public static void movePlayer(int playerId, String direction)
    {
        if(direction.equalsIgnoreCase("LEFT"))
        {
            client.sendData("moveEnemyPlayer|" + playerId + "|LEFT|*");
        } else if(direction.equalsIgnoreCase("RIGHT"))
        {
            client.sendData("moveEnemyPlayer|" + playerId + "|RIGHT|*");
        } else if(direction.equalsIgnoreCase("UP"))
        {
             client.sendData("moveEnemyPlayer|" + playerId + "|UP|*");
        } else if(direction.equalsIgnoreCase("DOWN"))
        {
            client.sendData("moveEnemyPlayer|" + playerId + "|DOWN|*");
        }
    }
    
    public static void placeBomb(int playerId, ThinGridCoordinates pos, String bombType)
    {
        client.sendData("placeEnemyBomb|" + pos.getX() + "|" + pos.getY() + "|" + playerId + "|" + bombType + "|*");
    }
    
    public static void spawnCoin(MapCellCoordinates cellPos)
    {
        client.sendData("spawnCoin|" + cellPos.getX() + "|" + cellPos.getY() + "|*");
    }
    
    public static void setMaxPlaceBombs(int playerId, int maxBombPlace)
    {
        client.sendData("enemyPlayerSetMaxBombs|" + playerId + "|" + maxBombPlace + "|*");
    }
    
    public static void setPlayerSpeed(int playerId, float speed)
    {
        client.sendData("enemyPlayerSetSpeed|" + playerId + "|" + speed + "|*");
    }
    
    public static void setPlayerRange(int playerId, int bombRange)
    {
        client.sendData("enemyPlayerSetRange|" + playerId + "|" + bombRange + "|*");
    }
}
