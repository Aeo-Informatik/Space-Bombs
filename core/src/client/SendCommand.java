package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import gui.map.MapCellCoordinates;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class SendCommand
{
    private Client client;
    
    public SendCommand(Client client)
    {
        this.client = client;
    }
    
    
    public void registerItemFields(int numberOfItemFields)
    {
        client.sendData("registerItemFields|" + numberOfItemFields + "|SERVER");
    }
    
    public void setPlayerCoins(int playerId, int coins)
    {
        client.sendData("enemyPlayerSetCoins|" + playerId + "|" + coins + "|*");
    }
    
    public void setPlayerLife(int playerId, int life)
    {
        client.sendData("enemyPlayerLife|" + playerId + "|" + life + "|*");
    }
    
    public void stopMoving(int playerId, ThinGridCoordinates pos)
    {
        client.sendData("stopEnemyPlayer|" + playerId + "|" + pos.getX() + "|" + pos.getY() + "|*");
    }
    
    public void movePlayer(int playerId, String direction)
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
    
    public void placeBomb(int playerId, ThinGridCoordinates pos, String bombType)
    {
        client.sendData("placeEnemyBomb|" + pos.getX() + "|" + pos.getY() + "|" + playerId + "|" + bombType + "|*");
    }
    
    public void spawnCoin(MapCellCoordinates cellPos)
    {
        client.sendData("spawnCoin|" + cellPos.getX() + "|" + cellPos.getY() + "|*");
    }
    
    public void setMaxPlaceBombs(int playerId, int maxBombPlace)
    {
        client.sendData("enemyPlayerSetMaxBombs|" + playerId + "|" + maxBombPlace + "|*");
    }
    
    public void setPlayerSpeed(int playerId, float speed)
    {
        client.sendData("enemyPlayerSetSpeed|" + playerId + "|" + speed + "|*");
    }
    
    public void setPlayerRange(int playerId, int bombRange)
    {
        client.sendData("enemyPlayerSetRange|" + playerId + "|" + bombRange + "|*");
    }
}
