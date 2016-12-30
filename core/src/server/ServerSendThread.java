
package server;

import com.gdx.bomberman.Constants;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;


class ServerSendThread implements Runnable
{       
        //Constructor
        ArrayList<String> dataToSend;
	public ServerSendThread(ArrayList<String> dataToSend)
	{
            this.dataToSend = dataToSend;
	}
        
        
        //Constructor 2 for sending to a specific client
        boolean sendToOneClientOnly = false;
        ClientConnection singleClient;
        String message;
        public ServerSendThread(ClientConnection singleClient, String message1)
        {
            this.sendToOneClientOnly = true;
            this.singleClient = singleClient;
            this.message = message1;
            
        }
        
        
        @Override
	public void run() 
        {
            try
            {   
                if(sendToOneClientOnly == false)
                {
                    //Iterates through the message array list 
                    for(String msgToClient : dataToSend)
                    {
                        //Debug
                        if(Constants.SERVERDEBUG)
                            System.out.println("SERVER: Send: " + msgToClient);

                        //Iterate through connected client list
                        for(int i=0; i < Server.getClientConnectionArraySize(); i++)
                        {
                            ClientConnection clientConnection = Server.getClient(i);
                            
                            //Debug
                            if(Constants.SERVERDEBUG)
                                System.out.println("SERVER: To playerId: " + clientConnection.getPlayerId());

                            if(clientConnection.getSocket().isClosed())
                            {
                                System.out.println("SendToAll: ServerSendThread: Socket already closed. Abort sending data!");
                                return;
                            }
                            
                            //Create object to send data
                            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(clientConnection.getSocket().getOutputStream()));

                            //Send message to client
                            printWriter.println(msgToClient);
                            printWriter.flush();
                        }
                        
                        Thread.sleep(100);
                    }//Quits thread after all messages have been send
                    
                }else
                {
                    if(singleClient.getSocket().isClosed())
                    {
                        System.out.println("SendToOne: ServerSendThread: Socket already closed. Abort sending data!");
                        return;
                    }
                    
                    //Debug
                    if(Constants.SERVERDEBUG)
                    {
                        System.out.println("SERVER: Send: " + message);
                        System.out.println("SERVER: To: " + singleClient.getPlayerId());
                    }        
                     
                    //Create object to send data
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(singleClient.getSocket().getOutputStream()));

                    //Send message to client
                    printWriter.println(message);
                    printWriter.flush();    
                }
                    
            }catch(SocketException e)
            {
                System.err.println("Send thread aborted because specified client socket closed");
            }catch(IOException | InterruptedException e)
            {
                System.err.println("ERROR: Unexpected error in sendThread " +e);
                e.printStackTrace();
                System.exit(1);
            }	
	}
}

