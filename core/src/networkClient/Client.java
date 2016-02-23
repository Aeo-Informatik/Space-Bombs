/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.net.Socket;

public class Client implements ClientInterface {
    
    //Global Variables & Objects
    private Socket socket;
    
    
    //Constructor
    public Client(String host, int port){
        try{
            this.socket = new Socket(host, port);
            
        }catch(Exception e){
            
        }
    }
    
    //Send & receive data
    @Override
    public void sendData() {
        try{
            
            SendThread sendThread = new SendThread(socket);
            Thread send = new Thread(sendThread);
            send.start();
            
        }catch(Exception e){
           
        }
    }
    

    @Override
    public void receiveData() {
        try{
            
            RecieveThread recieveThread = new RecieveThread(socket);
            Thread receive = new Thread(recieveThread);
            receive.start();
            
        }catch(Exception e){
           
        }
    }

}
