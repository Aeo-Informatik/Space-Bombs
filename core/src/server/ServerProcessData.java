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
        //Split received data
        String[] parameters = receivedData.split("\\|");

        //Check if it targets this device with keyword SERVER
        if (parameters[parameters.length - 1].equals("SERVER"))
        {
            switch (parameters[0]) 
            {

            }
        }
    }
}
