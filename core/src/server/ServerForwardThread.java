/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import com.gdx.bomberman.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class ServerForwardThread implements Runnable 
{
    private ClientConnection clientconnection;  
    private Server server;
    
    public ServerForwardThread(ClientConnection clientconnection, Server server)
    {
        this.clientconnection = clientconnection;
        this.server = server;
    }
    
    @Override
    public void run() 
    {
        try
        {
            // or 0 = never
            clientconnection.getSocket().setSoTimeout(0);

            //Get data from server and parse it into an Object BufferedReader
            BufferedReader receive = new BufferedReader(new InputStreamReader(this.clientconnection.getSocket().getInputStream()));
            String dataReceived;

            //Opens tcp session to client if client disconnects readLine() returns null
            while((dataReceived = receive.readLine()) != null)
            {   
                String[] parameters = dataReceived.split("\\|");

                //Kill thread
                if(Thread.currentThread().isInterrupted())
                {
                    break;
                }

                //Check if it targets this device with keyword SERVER
                if (parameters[parameters.length - 1].equals("SERVER"))
                {
                    //Check if received data is an server instruction
                    server.getServerProcessData().executeInstruction(parameters);
                    continue;
                }


                //Debug
                if(Constants.SERVERDEBUG)
                {
                    System.out.println("SERVER: Received: " + dataReceived);
                    System.out.println("SERVER: From: " + clientconnection.getPlayerId());
                }

                //Send received message to every client
                for(int i=0; i < Server.getClientConnectionArraySize(); i++)
                {
                    ClientConnection clientConnection = Server.getClient(i);
                    
                    // If playerId of command is equals the same client forward thread
                    if(Integer.parseInt(parameters[parameters.length - 1]) == clientConnection.getPlayerId())
                    {
                        continue;
                    }

                    //Create object to send data
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(clientConnection.getSocket().getOutputStream()));

                    //Send message to client
                    printWriter.println(dataReceived);
                    printWriter.flush();

                    //Debug
                    if(Constants.SERVERDEBUG)
                    {
                        System.out.println("SERVER: Send: " + dataReceived);
                        System.out.println("SERVER: To playerId: " + clientConnection.getPlayerId()); 
                    }
                }
            }
            
            
             //If clients disconnects
            System.out.println("SERVER: Client " + clientconnection.getPlayerId() + " disconnected from server.");

            //Send death message of disconnecting player to everyone
            ArrayList<String> dataToSend = new ArrayList<>();
            dataToSend.add("enemyPlayerLife|" + clientconnection.getPlayerId() + "|0|*");
            ServerSendThread send = new ServerSendThread(dataToSend);
            Thread thread = new Thread(send);
            thread.start();
            
        }catch(SocketException e)
        {
            System.err.println("Server closed game!");
        }catch(IOException | NumberFormatException e)
        {
            System.err.println("ERROR: Server Unexpected error occurred in forward thread " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}

