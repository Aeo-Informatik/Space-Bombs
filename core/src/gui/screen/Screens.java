/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import client.Client;
import com.badlogic.gdx.Game;
import server.Server;

/**
 *
 * @author qubasa
 */
public class Screens 
{
    protected final Game game;
    protected final Server server;
    protected final Client client;
    
    public Screens(Game game, Client client, Server server)
    {
        this.game = game;
        this.server = server;
        this.client = client;
        
        if(client == null || server == null)
        {
            throw new NullPointerException("Server or Client object are empty!");
        }
    }
}
