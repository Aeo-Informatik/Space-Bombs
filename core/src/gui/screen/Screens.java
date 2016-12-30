/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.gdx.bomberman.Constants;

import client.Client;
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
    private float changeTimer = 0;
    private float changeTimerEnd = 0.5f;
    
    public Screens(final Game game, final Client client,final Server server)
    {
        this.game = game;
        this.server = server;
        this.client = client;
        
        if(client == null || server == null)
        {
            throw new NullPointerException("Server or Client object are empty!");
        }
    }
    
    protected void changeToFullScreenOnF12()
    {
        if(changeTimer >= changeTimerEnd)
        {
            if(Gdx.input.isKeyJustPressed(Input.Keys.F12))
            {
                changeTimer = 0;
                
                if(Gdx.graphics.isFullscreen())
                {
                    Gdx.graphics.setWindowedMode(Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
                }else
                {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
            }
        }else
        {
            changeTimer += Constants.DELTATIME;
        }
    }
}