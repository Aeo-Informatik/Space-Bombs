/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author qubasa
 */
public class TextureManager {
    
    public static Texture players;
    public static TextureRegion p1;
    public static TextureRegion p2;
    public static TextureRegion p3;
    public static TextureRegion p4;
    
    public static Texture loadTexture (String file) 
    {
	return new Texture(Gdx.files.internal(file));
    }
    
    public static void load () 
    {
        
        
        players = loadTexture("pants_icons.png");
        p1 = new TextureRegion(players, 0, 0, 184/4 , 28);
        p2 = new TextureRegion(players, 46, 0, 184/4 , 28);
        p3 = new TextureRegion(players, 92, 0, 184/4 , 28);
        p4 = new TextureRegion(players, 138, 0, 184/4 , 28);
    }
}
