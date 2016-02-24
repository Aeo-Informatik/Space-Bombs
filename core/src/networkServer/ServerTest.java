/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkServer;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author qubasa
 */
public class ServerTest 
{
    public static void main(String[] args) 
    {
        try
        {
            //Variables & objects
            final int port = 4444;
            ServerSocket serversocket = new ServerSocket(port);
            Server server = new Server();   
                
            server.setDebug(true);
                    
                    
            //Accept all client connections and get them as socket object
            ArrayList<Socket> connections = server.AcceptConnections(serversocket, 1, 20000);
               
            //Open receive thread for every client
            ReceiveThread receive = new ReceiveThread(connections);
            Thread thread = new Thread(receive);
            thread.start();
            
            /*//Opens for each client a separate receive thread
            for(Socket socket : connections)
            {
                //Debug
                System.out.println("IP: " + socket.getInetAddress().getHostAddress());
                System.out.println("-----------End IP-----------");
                        
                //Open receive thread for every client
                ReceiveThread receive = new ReceiveThread(socket);
                Thread thread = new Thread(receive);
                thread.start();
            }*/
                    
            //Opens one thread to send back the data to every client
            //SendThread send = new SendThread(connections);
            //Thread thread2 = new Thread(send);
            //thread2.start();
                    
        }catch(Exception e)
        {
            System.err.println("BombermanServer: " + e);
        }
    }
}
