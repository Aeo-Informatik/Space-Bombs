/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author qubasa
 */
public class TextureManager {
    
    public static Texture BRICKWALL = new Texture(Gdx.files.internal("Brick.png"));
    
    public static Texture PLAYER = new Texture(Gdx.files.internal("data/character1/walking-right.png"));
}
