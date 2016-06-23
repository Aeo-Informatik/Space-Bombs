/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 *
 * @author qubasa
 */
public class AudioManager 
{
    
    //Menu Sounds
    public static Music clickSound;
    
    //Menu Music
    public static Music menuMusic;
    
    //Ingame Music
    
    
    //Look for ben briggs music!http://benbriggs.net/
    //His music is available under this license: https://creativecommons.org/licenses/by/3.0/
    public static void load() 
    {      
        //Menu Sounds
        clickSound = loadAudio("audio/sounds/click.wav");
        
        //Menu Music
        menuMusic = loadAudio("audio/music/The_Briggs_Effect/Mystery_Gift_1.mp3");

        
        //Ingame Music
        
    }
    
    public static Music loadAudio (String file) 
    {
        return Gdx.audio.newMusic(Gdx.files.internal(file));
    } 
}

