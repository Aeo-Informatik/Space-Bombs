
package server;

import com.gdx.bomberman.Constants;
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
        
        //Constructor 2 for sending to a specific client
        boolean oneClientOnly = false;
        Socket socket1;
        String message1;
        
        //Constructor 2
        public SendThread(Socket socket1, String message1)
        {
            this.oneClientOnly = true;
            this.socket1 = socket1;
            this.message1 = message1;
            
        }
        
        
        @Override
	public void run() 
        {
            try
            {   
                if(oneClientOnly == false)
                {
                    //Iterates through the message array list 
                    for(String msgToClient : dataToSend)
                    {
                        //Debug
                        if(Constants.SERVERDEBUG)
                            System.out.println("SERVER: Send: " + msgToClient);

                        //Iterate through connected client list
                        for(int i=0; i < sock.size(); i++)
                        {

                            //Debug
                            if(Constants.SERVERDEBUG)
                                System.out.println("SERVER: To: " + sock.get(i).getInetAddress().getHostAddress());

                            //Create object to send data
                            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sock.get(i).getOutputStream()));

                            //Send message to client
                            printWriter.println(msgToClient);
                            printWriter.flush();
                        }
                        
                        Thread.sleep(100);
                    }//Quits thread after all messages have been send
                    
                }else
                {

                    //Debug
                    if(Constants.SERVERDEBUG)
                    {
                        System.out.println("SERVER: Send: " + message1);
                        System.out.println("SERVER: To: " + socket1.getInetAddress().getHostAddress());
                    }        
                     
                    //Create object to send data
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket1.getOutputStream()));

                    //Send message to client
                    printWriter.println(message1);
                    printWriter.flush();    
                }
                    
            }catch(Exception e)
            {
                System.err.println("ERROR: Unexpected error in sendThread " +e);
                e.printStackTrace();
                System.exit(1);
            }	
	}
}

