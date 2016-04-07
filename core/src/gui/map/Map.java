/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author qubasa
 */
public abstract class Map 
{
    public abstract void create();
    
    //Update is the same as render only that it doesn't have the SpriteBatch Object
    public abstract void update();
    
    public abstract void render(SpriteBatch sb);
    
    public abstract void resize(int width, int height);
    
    public abstract void dispose();
    
    public abstract void pause();
    
    public abstract void resume();
}
