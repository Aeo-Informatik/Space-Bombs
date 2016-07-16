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
                if(number <= 40)
                {
                    itemSpawnCommands.add("");
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
    }
    
}
