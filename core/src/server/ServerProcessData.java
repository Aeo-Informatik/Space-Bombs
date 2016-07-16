/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


/**
 *
 * @author qubasa
 */
public class ServerProcessData 
{
    private int itemFields = 0;
    
    public void executeInstruction(String [] parameters)
    {
        try
        {
            //Check if it targets this device with keyword SERVER
            if (parameters[parameters.length - 1].equals("SERVER"))
            {
                switch (parameters[0]) 
                {
                    case "registerItemFields":
                        break;
                }
            }else
            {
                System.err.println("SERVER ERROR: Execute Instruction not meant to be for server");
            }
            
        }catch(Exception e)
        {
            System.err.println("ERROR: ServerProcessData() Something went wrong " + e);
            e.printStackTrace();
        }
    }
}
