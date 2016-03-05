/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.net.Socket;

/**
 *
 * @author qubasa
 */
public class AnalyseData {
    
    private Socket socket;
    
    public AnalyseData(Socket socket){
        this.socket = socket;
    }
    
    
    protected void analyse(String receivedData){
        
        try
        {
            /*
            Syntax for receiving data: funktion|arguments|player
            */
            String[] parameters = receivedData.split("|");
            String playerIdString = Integer.toString(ClientInterface.PLAYERID);

            //Check if the minimum arguments are given
            if (parameters.length < 3)
            {
                System.err.println("ERROR: received data with invalid length");
                
            //Check if it targets this device with playerId or astrix
            } else if (parameters[parameters.length - 1].equals(playerIdString) || parameters[parameters.length - 1].equals("*"))
            {

                switch (parameters[0]) 
                {
                    //Message to receive: registerPlayerId|1|ipAddress|*
                    case "registerPlayerId":
                        //Check if the received ip address matches with the local one
                        if(socket.getInetAddress().getHostAddress().equals(parameters[2]))
                        {
                            ClientInterface.PLAYERID = Integer.parseInt(parameters[1]);
                        }
                        break;

                }  
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
