/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import client.Client;
import com.badlogic.gdx.Game;

/**
 *
 * @author qubasa
 */
public class Screens 
{
    protected Game game;
    protected Client client;
    
    public Screens(Game game, Client client)
    {
        this.game = game;
        this.client = client;
    }
    
    
}
