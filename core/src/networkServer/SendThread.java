
package networkServer;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


class SendThread implements Runnable
{       
        //Global variables & objects
        ArrayList<Socket> sock;
        ArrayList<String> dataToSend;
	
        
        //Constructor
	public SendThread(ArrayList<Socket> sock, ArrayList<String> dataToSend)
	{
                this.sock = sock;
                this.dataToSend = dataToSend;
	}
        
        
        @Override
	public void run() 
        {
            try
            {   
                
                //Iterates through the message array list 
                for(String msgToClient : dataToSend)
                {
                    //Debug
                    if(Server.getDebug())
                        System.out.println("Send: " + msgToClient);
                            
                    //Iterate through connected client list
                    for(int i=0; i < sock.size(); i++)
                    {
                        //Check if client is connected
                        if(sock.get(i).isConnected())
                        {
                            //Debug
                            if(Server.getDebug())
                                System.out.println("To: " + sock.get(i).getInetAddress().getHostAddress());
                                    
                            //Create object to send data
                            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sock.get(i).getOutputStream()));

                            //Send message to client
                            printWriter.println(msgToClient);
                            printWriter.flush();
                                    
                        }else
                        {
                            System.err.println("SendThread(): Client disconnected with ip: " + sock.get(i).getInetAddress().getHostAddress());
                        }
                    }
                }//Quits thread after all messages have been send
       
            }catch(Exception e)
            {
                e.printStackTrace();
            }	
	}
}

