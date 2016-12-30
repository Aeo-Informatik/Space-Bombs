/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.gdx.bomberman.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author qubasa
 */
public class ClientSendThread implements Runnable {

    private String dataToSend;
    private Socket socket;
    
    public ClientSendThread(Socket socket, String dataToSend)
    {
        this.dataToSend = dataToSend;
        this.socket = socket;
    }
    
    @Override
    public void run() 
    {
        try
        {
            PrintWriter print = new PrintWriter(socket.getOutputStream(), true);	

            //Debug
            if(Constants.CLIENTDEBUG)
                System.out.println("CLIENT: Send to server: " + dataToSend);

            //WARNING Could be the source of a bug. It is a remedation against a bug in mainPlayer where stopEnemyPlayer would be send after move
            if(dataToSend.startsWith("moveEnemyPlayer"))
            {
                Thread.sleep(10);
            }
            
            //Send string to server
            print.println(dataToSend);
            print.flush();
  
        }catch(SocketException e)
        {
            System.err.println("ERROR: Connection to server closed in ClientSendThread(). " + e);
            e.printStackTrace();
            System.exit(1);
            
        }catch(IOException | InterruptedException e)
        {
            System.err.println("ERROR: Something in ClientSendThread() went wrong. " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}