/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;



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
    private ArrayList<Socket> clientConnections;
    
    public ServerForwardThread(Socket socket, ArrayList<Socket> clientConnections)
    {
        this.socket = socket;
        this.clientConnections = clientConnections;
    }
    
    @Override
    public void run() 
    {
        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                //Set timeout exception after 15 seconds or 0 = never
                socket.setSoTimeout(0);
                    
                //Get data from server and parse it into an Object BufferedReader
                BufferedReader receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                String dataReceived;

                //Read all lines received from client
                while((dataReceived = receive.readLine())!= null)
                {   
                    //Debug
                    if(Server.DEBUG)
                        System.out.println("Received from client: " + dataReceived);
                        System.out.println("From: " + socket.getInetAddress().getHostAddress());
                           
                    //Send received message to every client
                    for(int i = 0; i < clientConnections.size(); i++)
                    {

                        //Create object to send data
                        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(clientConnections.get(i).getOutputStream()));

                        //Send message to client
                        printWriter.println(dataReceived);
                        printWriter.flush();

                        //Debug
                        if(Server.DEBUG)
                            System.out.println("Send to client: " + dataReceived);
                            System.out.println("To: " + clientConnections.get(i).getInetAddress().getHostAddress()); 
                        }
                    }

            }catch(SocketException | NullPointerException | SocketTimeoutException e)
            {
                System.err.println("ERROR: ReceiveThread() Client disconnected closing receive thread. " + e);
                Thread.currentThread().interrupt();
                
            }catch(Exception e)
            {
                System.err.println("ERROR: Unexpected error occurred in forward thread " +e);
                e.printStackTrace();
                System.exit(0);
            }
        }
            
    }
    
}