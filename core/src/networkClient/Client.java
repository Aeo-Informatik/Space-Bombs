/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.net.Socket;

public class Client implements ClientInterface{
    
    private Socket socket;
    
    public Client(String host, int port){
        try{
            this.socket = new Socket(host, port);
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    /*
	public static void main(String[] args)
	{
		try {
			Socket sock = new Socket("localhost",444);
			SendThread sendThread = new SendThread(sock);
			Thread thread = new Thread(sendThread);thread.start();
			RecieveThread recieveThread = new RecieveThread(sock);
			Thread thread2 =new Thread(recieveThread);thread2.start();
                        
		} catch(Exception e) 
                {
                    e.printStackTrace();
                } 
	}*/

    @Override
    public void sendData(String msgtoServer) {
        try{
            
            SendThread sendThread = new SendThread(socket, msgtoServer);
            Thread send = new Thread(sendThread);
            send.start();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String receiveData() {
        try{
            
            RecieveThread recieveThread = new RecieveThread(socket);
            Thread receive =new Thread(recieveThread);
            receive.start();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String encode(String data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String decode(String data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
