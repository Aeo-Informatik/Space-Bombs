/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;


import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


/**
 *
 * @author qubasa
 */
public class Client 
{
    
    //Objects
    private Socket socket = null;
    private Thread receiveThread = null;
    private SendCommand sendCommand;
    
    public Client()
    {
        this.sendCommand = new SendCommand(this);
    }
    
    public void setHost(String host, int port) throws Exception
    {
        InetAddress address = InetAddress.getByName(host);
        
        //Check if host ip is up and running else it creates a loop on creating the socket object
        if(address.isReachable(2000))
        {
            this.socket = new Socket(host, 13199);
        }else
        {
            throw new UnknownHostException("Connection refused");
        }

        //Create thread objects
        ClientReceiveThread recieve = new ClientReceiveThread(socket);
        receiveThread = new Thread(recieve);
    }
    
    
    
    //Start temporary thread to send some data to server
    protected void sendData(String dataToSend)
    {
        ClientSendThread sendThread = new ClientSendThread(socket, dataToSend);
        Thread send = new Thread(sendThread);
        send.start();
    }
    
    //Start permanent connection thread to server and gets data
    public void connectToServer() throws Exception
    {
        //Start tcp connection
        receiveThread.start();
        
        //Wait till thread is running
        while(!receiveThread.isAlive())
        {
            System.out.print("Waiting.");
            Thread.sleep(100);
        }
    }
    
    public void closeConnection() throws IOException
    {
        if(receiveThread != null)
        {
            receiveThread.interrupt();
        }

        socket.close();
    }
    
    public boolean isConnectedToServer()
    {
        //If recievethread or pingthread is down
        if(!receiveThread.isAlive())
        {
            return false;
        }
        
        return true;
    }
    
    public InetAddress getCurrentIp() throws SocketException
    {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) 
            {
                NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                
                while(nias.hasMoreElements()) 
                {
                    InetAddress ia= (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) 
                    {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) 
        {
            throw e;
        }
        return null;
    }

    /**
     * @return the sendCommand
     */
    public SendCommand getSendCommand() {
        return sendCommand;
    }
}
