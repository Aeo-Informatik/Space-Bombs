/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package networkClient;

/**
 *
 * @author hebendanz_l
 */
public interface ClientInterface {
    
    public void sendData(String data);
    
    public String receiveData();
    
    public String encoding(String data);
    
    public String decode(String data);
    
}
