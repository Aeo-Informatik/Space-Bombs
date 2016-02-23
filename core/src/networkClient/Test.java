/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkClient;

/**
 *
 * @author qubasa
 */
public class Test {
    
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 4444);
        
        ClientInterface.DATASEND.add("Test");
        client.sendData();
    }
    
}
