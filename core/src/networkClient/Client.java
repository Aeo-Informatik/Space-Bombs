/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.badlogic.gdx.Gdx;
import java.net.Socket;


/**
 *
 * @author qubasa
 */
public class Client {
    
    private Socket socket;
    public static boolean DEBUG = false;
    
    public Client(String host, int port)
    {
        try
        {
            this.socket = new Socket(host, port);
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something went wrong on connectiong to server " + e);
            System.exit(1);
        }
    }
        
    
    public void sendData(String dataToSend)
    {
        ClientSendThread sendThread = new ClientSendThread(socket, dataToSend);
        Thread send = new Thread(sendThread);
        send.start();
    }
    
    
    public void receiveData()
    {
        ClientReceiveThread recieveThread = new ClientReceiveThread(socket);
        Thread receive = new Thread(recieveThread);
        receive.start();
    }
}
