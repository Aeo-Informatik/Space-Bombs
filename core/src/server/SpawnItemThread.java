/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
    
    public SpawnItemThread(int numberOfItemFields)
    {
        this.itemFields = numberOfItemFields;
    }
    
    @Override
    public void run() 
    {
        while(!Thread.currentThread().isInterrupted())
        {
            //Spawn an item for every field
            for(int i=0; i < itemFields; i++)
            {
                //Exit thread
                if(Thread.currentThread().isInterrupted())
                {
                    break;
                }
                
                //Generate random number
                int number = random.nextInt(100);//0-99

                //Select item to spawn
                switch(number)
                {
                    case 0:
                        break;
                }
            }
            
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
