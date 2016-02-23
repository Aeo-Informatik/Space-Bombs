/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package networkClient;

import java.util.ArrayList;

/**
 *
 * @author hebendanz_l
 */
public interface ClientInterface {
    
    public static ArrayList<String> DATARECEIVED = new ArrayList<>();
    
    public static ArrayList<String> DATASEND = new ArrayList<>();
    
    public void sendData();
    
    public void receiveData();
    
     
}
