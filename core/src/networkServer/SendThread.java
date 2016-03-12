
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
        
        //Constructor 2 for sending to a specific client
        boolean oneClientOnly = false;
        Socket socket1;
        String message1;
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
                        if(Server.DEBUG)
                            System.out.println("Send: " + msgToClient);

                        //Iterate through connected client list
                        for(int i=0; i < sock.size(); i++)
                        {

                            //Debug
                            if(Server.DEBUG)
                                System.out.println("To: " + sock.get(i).getInetAddress().getHostAddress());

                            //Create object to send data
                            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(sock.get(i).getOutputStream()));

                            //Send message to client
                            printWriter.println(msgToClient);
                            printWriter.flush();
                        }
                    }//Quits thread after all messages have been send
                    
                }else
                {

                    //Debug
                    if(Server.DEBUG)
                        System.out.println("Send: " + message1);
                        System.out.println("To: " + socket1.getInetAddress().getHostAddress());
                            
                    //Create object to send data
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket1.getOutputStream()));

                    //Send message to client
                    printWriter.println(message1);
                    printWriter.flush();    
                }
                    
            }catch(Exception e)
            {
                try 
                {
                    throw e;
                    
                } catch (Exception ex) 
                {
                    System.err.println("Couldnt throw exception in SendThread printed it instead.");
                    ex.printStackTrace();
                }
            }	
	}
}

