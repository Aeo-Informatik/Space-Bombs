
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
        
        boolean oneClientOnly = false;
        Socket socket1;
        String message1;
        
        //Constructor 2 for sending to a spefic client
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
                                System.err.println("SendThread(): Cannot reach client with ip: " + sock.get(i).getInetAddress().getHostAddress());
                            }
                        }
                    }//Quits thread after all messages have been send
                    
                }else
                {
                    if(socket1.isConnected())
                    {
                        //Debug
                        if(Server.getDebug())
                            System.out.println("One Message To: " + socket1.getInetAddress().getHostAddress());
                            System.out.println("Message: " + message1);
                        
                        //Create object to send data
                        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket1.getOutputStream()));

                        //Send message to client
                        printWriter.println(message1);
                        printWriter.flush();    
                            
                    }else
                        {
                            System.err.println("SendThread(): Cannot reach client with ip: " + socket1.getInetAddress().getHostAddress());
                        }
                }
                    
            }catch(Exception e)
            {
                e.printStackTrace();
            }	
	}
}

