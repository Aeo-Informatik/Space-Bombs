package client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.gdx.bomberman.Constants;

import gui.map.MapCellCoordinates;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class SendCommand
{
    private Client client;
    
    /* 
    ------Server commands------
    spawnCoin
    deleteItems
    spawnPlayers
    registerAmountPlayers
    registerMainPlayerId
    setGameMap
    spawnItem
    triggerRemoteExplosion
    spawnBombOnItemfield
    */
    
    public SendCommand(Client client)
    {
        this.client = client;
    }
    
    public void setBombCost(int bombId, int bombCost, int playerId)
    {
        //General:enemyPlayerSetBombCost|bombId|bombCost|playerId|senderId
        client.sendData("enemyPlayerSetBombCost|" + bombId + "|" + bombCost + "|" + playerId +  "|"+ Constants.PLAYERID);
    }
    
    public void setChoosenBomb(int bombSlot, int playerId)
    {
        //General:enemyPlayerChangeBombSelection|bombSlot|playerId|senderId
        client.sendData("enemyPlayerSetChoosenBomb|" + bombSlot + "|" + playerId +  "|"+ Constants.PLAYERID);
    }
    
    public void triggerRemoteBomb(int playerId)
    {
        client.sendData("triggerRemoteBomb|" + playerId + "|" + Constants.PLAYERID);
    }
    
    public void registerItemFields(int numberOfItemFields)
    {
        client.sendData("registerItemFields|" + numberOfItemFields + "|SERVER");
    }
    
    public void setPlayerCoins(int playerId, int coins)
    {
        client.sendData("enemyPlayerSetCoins|" + playerId + "|" + coins + "|" + Constants.PLAYERID);
    }
    
    public void hitByBombAnimation(int playerId)
    {
        client.sendData("enemyHitByBomb|" + playerId + "|" + Constants.PLAYERID);
    }
    
    public void setPlayerLife(int playerId, int life)
    {
        client.sendData("enemyPlayerLife|" + playerId + "|" + life + "|" + Constants.PLAYERID);
    }
    
    public void stopMoving(int playerId, ThinGridCoordinates pos)
    {
        client.sendData("moveEnemyPlayer|" + playerId + "|" + pos.getX() + "|" + pos.getY() +  "|" + Constants.NONE + "|" + Constants.PLAYERID);
    }
    
    public void movePlayer(int playerId, ThinGridCoordinates pos, String direction)
    {
        if(direction.equalsIgnoreCase(Constants.LEFT))
        {
            client.sendData("moveEnemyPlayer|" + playerId + "|" + pos.getX() + "|" + pos.getY() +  "|" + Constants.LEFT + "|" + Constants.PLAYERID);
        } else if(direction.equalsIgnoreCase(Constants.RIGHT))
        {
            client.sendData("moveEnemyPlayer|" + playerId + "|" + pos.getX() + "|" + pos.getY() + "|"+ Constants.RIGHT + "|" + Constants.PLAYERID);
        } else if(direction.equalsIgnoreCase(Constants.UP))
        {
             client.sendData("moveEnemyPlayer|" + playerId + "|" + pos.getX() + "|" + pos.getY() +"|"+ Constants.UP +"|" + Constants.PLAYERID);
        } else if(direction.equalsIgnoreCase(Constants.DOWN))
        {
            client.sendData("moveEnemyPlayer|" + playerId + "|" + pos.getX() + "|" + pos.getY() + "|"+ Constants.DOWN +"|" + Constants.PLAYERID);
        }
    }
    
    public void placeBomb(int playerId, ThinGridCoordinates pos, String bombType)
    {
        client.sendData("placeEnemyBomb|" + pos.getX() + "|" + pos.getY() + "|" + playerId + "|" + bombType + "|" + Constants.PLAYERID);
    }
    
    public void placeInfinityBomb(int playerId, ThinGridCoordinates pos, int explodePath)
    {
        client.sendData("placeInfinityBomb|" + pos.getX() + "|" + pos.getY() + "|" + playerId + "|" + explodePath + "|" + Constants.PLAYERID);
    }
    
    public void spawnCoin(MapCellCoordinates cellPos)
    {
        client.sendData("spawnCoin|" + cellPos.getX() + "|" + cellPos.getY() + "|" + Constants.PLAYERID);
    }
    
    public void setMaxPlaceBombs(int playerId, int maxBombPlace)
    {
        client.sendData("enemyPlayerSetMaxBombs|" + playerId + "|" + maxBombPlace + "|" + Constants.PLAYERID);
    }
    
    public void setPlayerSpeed(int playerId, int speed)
    {
        client.sendData("enemyPlayerSetSpeed|" + playerId + "|" + speed + "|" + Constants.PLAYERID);
    }
    
    public void setPlayerRange(int playerId, int bombRange)
    {
        client.sendData("enemyPlayerSetRange|" + playerId + "|" + bombRange + "|" + Constants.PLAYERID);
    }
    
    public void setPlayerCubicRange(int playerId, int cubicRange)
    {
        client.sendData("enemyPlayerSetCubicRange|" + playerId + "|" + cubicRange + "|" + Constants.PLAYERID);
    }
}
