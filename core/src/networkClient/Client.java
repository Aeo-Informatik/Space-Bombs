/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements ClientInterface {
    
    //Global Variables & Objects
    private Socket socket;
    
    //Static Varibales
    public static boolean DEBUG = false;
    public static ArrayList<String> DATASEND = new ArrayList<>(); 
    
    
    //Constructor
    public Client(String host, int port) throws Exception
    {
        try
        {
            this.socket = new Socket(host, port);
            
        }catch(ConnectException e)
        {
            System.err.println("Error: Client() Couldn't connect to server");
                    
        }catch(Exception e)
        {
            throw e;
        }
    }
    
    

    /**
     * Opens a thread where it sends everything 
     * from an arraylist named: ClientInterface.DATASEND
     */
    @Override
    public void sendData() {
        try{
            Client.DATASEND.add("EXIT");
            
            SendThread sendThread = new SendThread(socket);
            Thread send = new Thread(sendThread);
            send.start();
            
        }catch(Exception e)
        {
           throw e;
        }
    }
    

    @Override
    /**
     * Opens a thread where it stores all incoming data into 
     * an arraylist named: ClientInterface.DATARECEIVE
     */
    public void receiveData() {
        try{

            ReceiveThread recieveThread = new ReceiveThread(socket);
            Thread receive = new Thread(recieveThread);
            receive.start();
            
        }catch(Exception e){
           throw e;
        }
    }
}
