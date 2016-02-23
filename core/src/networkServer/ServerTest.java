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
                    //Parameters: ServerSocket ss, int minConnections, int maxConnections, int timeout (in milliseconds)
                    ArrayList<Socket> connections = Server.AcceptConnections(serversocket, 4, 40000);

                    for(Socket c : connections){
                        System.out.println("IP: " + c.getInetAddress().getHostAddress());
                        System.out.println("-----------End IP-----------");
                        
                        RecieveThread recieve = new RecieveThread(c);
                        Thread thread = new Thread(recieve);
                        thread.start();
                        
                    }
                    
                    SendThread send = new SendThread(connections);
                    Thread thread2 = new Thread(send);
                    thread2.start();
                    
         
        }catch(Exception e)
        {
            System.err.println("BombermanServer: " + e);
        }
        
    }
    
}
