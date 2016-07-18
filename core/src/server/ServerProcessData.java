/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


/**
 *
 * @author qubasa
 */
public class ServerProcessData 
{
    private Thread itemSpawnerThread;
    private int itemFields = 0;
    private Server server;
    
    public ServerProcessData(Server server)
    {
        this.server = server;
    }
    
    public void stopSpawnItemThread()
    {
        if(itemSpawnerThread != null)
        {
            while(itemSpawnerThread.isAlive())
            {
                itemSpawnerThread.interrupt();
            }
        }
    }
    
    public void executeInstruction(String [] parameters)
    {
        try
        {
            //Check if it targets this device with keyword SERVER
            if (parameters[parameters.length - 1].equalsIgnoreCase("SERVER"))
            {
                switch (parameters[0]) 
                {
                    /**------------------REGISTER NUMBER OF ITEM FIELDS------------------**/
                    //General: registerItemFields|numberOfFields|SERVER
                    case "registerItemFields":
                        if(parameters.length == 3)
                        {
                            //If number of fields has changed
                            if(itemFields != Integer.parseInt(parameters[1]))
                            {
                                itemFields = Integer.parseInt(parameters[1]);
                                
                                //Interrupt old thread
                                if(itemSpawnerThread != null)
                                {
                                    while(itemSpawnerThread.isAlive())
                                    {
                                        itemSpawnerThread.interrupt();
                                    }
                                }
                                                                
                                //Create new thread with correct number of fields
                                SpawnItemThread spawnItem = new SpawnItemThread(itemFields, server); 
                                itemSpawnerThread = new Thread(spawnItem);
                                itemSpawnerThread.start();
                            }
                        }else
                            System.err.println("SERVER ERROR: registerItemFields wrong number of parameters");
                        break;
                }
            }else
            {
                System.err.println("SERVER ERROR: Execute Instruction not meant to be for server");
            }
            
        }catch(Exception e)
        {
            System.err.println("ERROR: ServerProcessData() Something went wrong " + e);
            e.printStackTrace();
        }
    }
}
