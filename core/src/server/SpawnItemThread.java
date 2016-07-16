/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author qubasa
 */
public class SpawnItemThread implements Runnable
{

    private int itemFields;
    private Random random = new Random();
    private long itemSpawnTime = ServerConstants.ITEMSPAWNTIME;
    private Server server;
    
    public SpawnItemThread(int numberOfItemFields, Server server)
    {
        this.itemFields = numberOfItemFields;
        this.server = server;
    }
    
    @Override
    public void run() 
    {
        while(!Thread.currentThread().isInterrupted())
        {
            ArrayList<String> itemSpawnCommands = new ArrayList<>();
            
            //Spawn an item for every field
            for(int i=0; i < itemFields; i++)
            {
                //Exit thread
                if(Thread.currentThread().isInterrupted())
                {
                    break;
                }
                
                //Generate random number
                int number = random.nextInt(100) +1;//1-100
                
                
                //Select item to spawn
                if(number <= 30)
                {
                    //Spawn nothing
                    
                }else if(number > 30 && number <= 40) //BOMBUP
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|BombUp|" + i + "|*");
                    
                }else if(number > 40 && number <= 60) //COINBAG
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|CoinBag|" + i + "|*");
                    
                }else if(number > 60 && number <= 70) //LIFEUP
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|LifeUp|" + i + "|*");
                    
                }else if(number > 70 && number <= 80) //RANGEUP
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|RangeUp|" + i + "|*");
                    
                }else if(number > 80 && number <= 95) //SPEEDUP
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|SpeedUp|" + i + "|*");
                    
                }else if(number > 95 && number <= 100) //YELLOWHEART
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|YellowHeart|" + i + "|*");
                }
            }
            
            //Send to every client the item spawn commands
            server.sendToAll(itemSpawnCommands);
            
            //Exit thread
            if(Thread.currentThread().isInterrupted())
            {
                break;
            }
            
            //Wait till next item spawn
            try 
            {
                Thread.sleep(itemSpawnTime);
                
            } catch (InterruptedException ex) 
            {
               
            }
        }
        
        System.out.println("EXITED SPAWN ITEM THREAD");
    }
    
}
