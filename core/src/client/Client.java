/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 *
 * @author qubasa
 */
public class Client {
    
    //Objects
    private Socket socket;
    private final Thread receiveThread;
    
    //Constructor
    public Client(String host, int port) throws Exception
    {
        InetAddress address = InetAddress.getByName(host);
        
        //Check if host ip is up and running else it creates a loop on creating the socket object
        if(address.isReachable(2000))
        {
            this.socket = new Socket(host, 13199);
        }else
        {
            throw new UnknownHostException("Connection refused");
        }

        //Create thread objects
        ClientReceiveThread recieve = new ClientReceiveThread(socket);
        receiveThread = new Thread(recieve);
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
            System.out.print("Waiting.");
            Thread.sleep(100);
        }
    }
    
    public void closeConnection() throws IOException
    {
        if(receiveThread != null)
        {
            receiveThread.interrupt();
        }

        socket.close();
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