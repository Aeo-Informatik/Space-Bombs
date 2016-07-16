/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.gdx.bomberman.Constants;

/**
 *
 * @author qubasa
 */
public class ServerProcessData 
{
    public void checkForServerInstructions(String receivedData)
    {
        try
        {
            //Split received data
            String[] parameters = receivedData.split("\\|");

            //Check if it targets this device with keyword SERVER
            if (parameters[parameters.length - 1].equals("SERVER"))
            {
                switch (parameters[0]) 
                {
                    case "registerItemFields":
                        break;
                }
            }
            
        }catch(Exception e)
        {
            System.err.println("ERROR: ServerProcessData() Something went wrong " + e);
            e.printStackTrace();
        }
    }
}
