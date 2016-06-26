/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;


import com.gdx.bomberman.Constants;
import java.io.IOException;
import java.net.Socket;


/**
 *
 * @author qubasa
 */
public class Client {
    
    //Objects
    private Socket socket;
    private final Thread receiveThread;
    private Thread pingThread;
    
    //Constructor
    public Client(String host, int port) throws IOException
    {
        this.socket = new Socket(host, port);
        PingThread ping = new PingThread(socket);
        ClientReceiveThread recieve = new ClientReceiveThread(socket);
        
        receiveThread = new Thread(recieve);
        pingThread = new Thread(ping);
    }
        
    //Start temporary thread to send some data to server
    public void sendData(String dataToSend)
    {
        ClientSendThread sendThread = new ClientSendThread(socket, dataToSend);
        Thread send = new Thread(sendThread);
        send.start();
    }
    
    //Start permanent connection thread to server and gets data
    public void connectToServer() throws Exception
    {
        //Start tcp connection
        receiveThread.start();
        
        //Wait till thread is running
        while(!receiveThread.isAlive())
        {
            System.out.print(".");
            Thread.sleep(100);
        }
    }
    
    public void closeConnection() throws IOException
    {
        if(receiveThread != null)
        {
            receiveThread.interrupt();
        }
        
        if(pingThread != null)
        {

            pingThread.interrupt();
        }

        socket.close();
    }
    
    //Start periodic pinging of server to determin latency
    public void pingThread()
    {
        //Start latency calculator with ping
        pingThread.start();
    }
    
    public boolean isConnectedToServer()
    {
        //If recievethread or pingthread is down
        if(!receiveThread.isAlive())
        {
            return false;
        }
        
        return true;
    }
}
