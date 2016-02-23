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
public class ServerTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
        
            final int port = 4444;

                    System.out.println("Server waiting for connection on port " + port);
                    ServerSocket serversocket = new ServerSocket(port);
                    
                    Server.setDebug(true);
                    
                    
                    //Accept all client connections and get them as socket object
                    ArrayList<Socket> connections = Server.AcceptConnections(serversocket, 4, 40000);
                    
                    //Opens for each client a separate receive thread
                    for(Socket socket : connections)
                    {
                        //Debug
                        System.out.println("IP: " + socket.getInetAddress().getHostAddress());
                        System.out.println("-----------End IP-----------");
                        
                        //Open receive thread for every client
                        RecieveThread recieve = new RecieveThread(socket);
                        Thread thread = new Thread(recieve);
                        thread.start();
                    }
                    
                    //Opens one thread to send back the data to every client
                    SendThread send = new SendThread(connections);
                    Thread thread2 = new Thread(send);
                    thread2.start();
                    
         
        }catch(Exception e)
        {
            System.err.println("BombermanServer: " + e);
        }
        
    }
    
}
