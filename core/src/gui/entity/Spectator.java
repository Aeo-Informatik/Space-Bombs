/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author qubasa
 */
public class Spectator extends Entity
{

    public Spectator(Vector2 pos, Vector2 direction) 
    {
        super(null, pos, direction);
    }

    @Override
    public void update() 
    {
        
    }
    
    public void render(SpriteBatch sb)
    {
        
    }
    
}
