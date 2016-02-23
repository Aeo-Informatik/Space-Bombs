/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package networkServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author hebendanz_l
 */
public interface ServerInterface {
    
    public ArrayList<Socket> AcceptConnections(ServerSocket ss,  int maxConnections, int timeout) throws Exception;
    
    public void setDebug(boolean debug);
    
    public boolean getDebug();
    
    public void setStopConnection(boolean stop);
    
    public static ArrayList<String> DATARECEIVED = new ArrayList<>();
}
