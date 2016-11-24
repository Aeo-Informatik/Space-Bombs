/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.gdx.bomberman.Constants;
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
    private long itemSpawnTime = Constants.ITEMTIMER * 1000;
    private Server server;
    
    public SpawnItemThread(int numberOfItemFields, Server server)
    {
        this.itemFields = numberOfItemFields;
        this.server = server;
    }
    
    @Override
    public void run() 
    {
        System.out.println("New Item thread with item fields: " + itemFields);
        
        while(!Thread.currentThread().isInterrupted())
        {            
            ArrayList<String> itemSpawnCommands = new ArrayList<>();
            ArrayList<String> deleteCommand = new ArrayList<>();
            
            deleteCommand.add("deleteItems|*");
            server.sendToAll(deleteCommand);
        
            try 
                {
                    
                    Thread.sleep(1000); 
                    
                } catch (InterruptedException ex) 
                {
                    
                }
            
            //Spawn an item for every field
            for(int i=0; i < itemFields; i++)
            {
                //Exit thread
                if(Thread.currentThread().isInterrupted() == true)
                {
                    break;
                }
                
                //Generate random number
                int number = random.nextInt(100) +1;//1-100
                
                
                //Select item to spawn
                if(number <= 25) // 30%
                {
                    //Spawn nothing
                    
                }else if(number > 25 && number <= 30) //BOMBUP 10%
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|CubicRangeUp|" + i + "|*");
                    
                }else if(number > 30 && number <= 40) //BOMBUP 10%
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|BombUp|" + i + "|*");
                    
                }else if(number > 40 && number <= 65) //COINBAG 25%
                {
                    itemSpawnCommands.add("spawnItem|CoinBag|" + i + "|*");
                    
                }else if(number > 65 && number <= 70) //LIFEUP 5%
                {
                    itemSpawnCommands.add("spawnItem|LifeUp|" + i + "|*");
                    
                }else if(number > 70 && number <= 80) //RANGEUP 10%
                {
                    itemSpawnCommands.add("spawnItem|RangeUp|" + i + "|*");
                    
                }else if(number > 80 && number <= 100) //SPEEDUP 20%
                {
                    itemSpawnCommands.add("spawnItem|SpeedUp|" + i + "|*");
                    
                }
            }
            
            //Send to every client the item spawn commands
            server.sendToAll(itemSpawnCommands);
            
            //Exit thread
            if(Thread.currentThread().isInterrupted() == true)
            {
                break;
            }
            
            //Wait till next item spawn
            try 
            {
                if(itemFields <= 3)
                {
                    Thread.sleep((int)(itemSpawnTime * 1.5f));
                }else
                {
                    Thread.sleep((int)(itemSpawnTime * itemFields/2.0f));
                }
                
            } catch (InterruptedException ex) 
            {
               
            }
        }
    }
    
}
