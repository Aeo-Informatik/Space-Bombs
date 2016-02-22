/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Gui;

/**
 *
 * @author pl0614
 */
public interface Menu {

    /**
     *
     */
     int  width = 40;//private  changeable
     int height = 40;//private  changeable
     /**
      * close the programm
      * @return
      */
     public int exit();
     
     /**
      * Joining the host with his ip(which this user must entered)
      * @param IP 
      */
     public void join(String IP);
     
     /**
      * shows the host IP 
      * @return 
      */
     public String getHostIP();
     
     /**
      * this methode starts the server with the localhost ip
      */
     public void host();
    
}
