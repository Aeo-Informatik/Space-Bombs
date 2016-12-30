/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.badlogic.gdx.utils.Timer;
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
    
    private int cubicRange = Constants.STARTITEMFIELCUBICRANGE;
    private int normalRange = Constants.STARTITEMFIELDNORMALBOMBRANGE;
    
    public SpawnItemThread(int numberOfItemFields, Server server)
    {
        this.itemFields = numberOfItemFields;
        this.server = server;
        
        /*--------------INCREASE BOMB RANGE--------------*/
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run() 
                {
                    if(cubicRange < Constants.MAXCUBICRANGE)
                    {
                        cubicRange += 1;
                    }
                    
                    if(normalRange < Constants.MAXBOMBRANGE)
                    {
                        normalRange += 1;
                    }
                }
            }
             ,60 // first execute delay
             ,60  // delay between executes     
            );
    }
    
    @Override
    public void run() 
    {
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
                if(number <= 25) // 25%
                {
                    int bombNum = random.nextInt(7) +1;
                    
                    if(bombNum <= 2)
                    {
                        //General:spawnBombOnItemfield|bombType|itemField|range|senderId
                        itemSpawnCommands.add("spawnBombOnItemfield|dynamite|" + i + "|" + cubicRange + "|*");
                    }else if(bombNum <= 4)
                    {
                        //General:spawnBombOnItemfield|bombType|itemField|range|senderId
                        itemSpawnCommands.add("spawnBombOnItemfield|infinity|" + i + "|" + normalRange +"|*");
                    }else if(bombNum <= 6)
                    {
                        itemSpawnCommands.add("spawnBombOnItemfield|X3|" + i + "|" + normalRange +"|*");
                    }else
                    {
                        // No bomb
                    }
                            
                }else if(number > 25 && number <= 30) //CUBICRANGEUP 5%
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|CubicRangeUp|" + i + "|*");
                    
                }else if(number > 30 && number <= 44) //BOMBUP 14%
                {
                    //General:spawnItem|itemType|itemField|target
                    itemSpawnCommands.add("spawnItem|BombUp|" + i + "|*");
                    
                }else if(number > 44 && number <= 69) //COINBAG 25%
                {
                    itemSpawnCommands.add("spawnItem|CoinBag|" + i + "|*");
                }else if(number > 69 && number <= 70) //LIFEUP 1%
                {
                    itemSpawnCommands.add("spawnItem|LifeUp|" + i + "|*");
                    
                }else if(number > 70 && number <= 85) //RANGEUP 15%
                {
                    itemSpawnCommands.add("spawnItem|RangeUp|" + i + "|*");
                    
                }else if(number > 85 && number <= 100) //SPEEDUP 15%
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
