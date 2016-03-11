/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;


/**
 *
 * @author qubasa
 */
public class Client {
    
    private Socket socket;
    public static boolean DEBUG = false;
    
    public Client(String host, int port)
    {
        try{
            SocketHints options = new SocketHints();
            options.keepAlive = true;
            options.connectTimeout = 15000;

            this.socket = Gdx.net.newClientSocket(Protocol.TCP, host, port, options);
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong on connectiong to server " + e);
            throw e;
        }
    }
        
    
    public void sendData(String dataToSend)
    {
        try
        {
            ClientSendThread sendThread = new ClientSendThread(socket, dataToSend);
            Thread send = new Thread(sendThread);
            send.start();
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong by sending some data " + e);
            throw e;
        }
    }
    
    
    public void receiveData()
    {
        try
        {
            ClientReceiveThread recieveThread = new ClientReceiveThread(socket);
            Thread receive = new Thread(recieveThread);
            receive.start();
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong by receiving some data " + e);
            throw e;
        }
    }
}
