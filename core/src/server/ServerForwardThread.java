/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;



import com.gdx.bomberman.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class ServerForwardThread implements Runnable 
{
    private Socket socket;  
    private int playerId;
    private ServerProcessData processData = new ServerProcessData();
    
    public ServerForwardThread(Socket socket, int playerId)
    {
        this.socket = socket;
        this.playerId = playerId;
    }
    
    @Override
    public void run() 
    {
        try
        {
            //Set timeout exception after 15 seconds or 0 = never
            socket.setSoTimeout(0);

            //Get data from server and parse it into an Object BufferedReader
            BufferedReader receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String dataReceived;

            //Opens tcp session to client if client disconnects readLine() returns null
            while((dataReceived = receive.readLine())!= null)
            {   
                //Kill thread
                if(Thread.currentThread().isInterrupted())
                {
                    break;
                }

                //Check if received data is an server instruction
                processData.checkForServerInstructions(dataReceived);
                
                //Debug
                if(ServerConstants.SERVERDEBUG)
                {
                    System.out.println("SERVER: Received: " + dataReceived);
                    System.out.println("SERVER: From: " + socket.getInetAddress().getHostAddress());
                }

                //Send received message to every client
                for(int i = 0; i < Server.getClientList().size(); i++)
                {

                    //Create object to send data
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(Server.getClient(i).getOutputStream()));

                    //Send message to client
                    printWriter.println(dataReceived);
                    printWriter.flush();

                    //Debug
                    if(ServerConstants.SERVERDEBUG)
                    {
                        System.out.println("SERVER: Send: " + dataReceived);
                        System.out.println("SERVER: To: " + Server.getClient(i).getInetAddress().getHostAddress()); 
                    }
                }

            }

             //If clients disconnects
            System.out.println("SERVER: Client " + socket.getInetAddress().getHostAddress() + " disconnected from server.");

            //Send death message of disconnecting player to everyone
            ArrayList<String> dataToSend = new ArrayList<>();
            dataToSend.add("enemyPlayerLife|" + playerId + "|0|*");
            ServerSendThread send = new ServerSendThread(Server.getClientList(), dataToSend);
            Thread thread = new Thread(send);
            thread.start();
            
        }catch(SocketException | SocketTimeoutException e)
        {
            System.err.println("ServerForwardThread(): Closed connection with exception: " + e);

        }catch(Exception e)
        {
            System.err.println("ERROR: Server Unexpected error occurred in forward thread " +e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}

