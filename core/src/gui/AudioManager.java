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
    public static Music normalExplosion;
    public static Music normalExplosion1;
    public static Music normalExplosion2;
    
    //Menu Music
    public static Music menuMusic;
    
    //Ingame Music
    public static Music currentIngameMusic;
    public static Music Amys_Toxic_Cave;
    public static Music Flight_of_the_Battery;
    public static Music Disregard_Farm;      
    public static Music Moshua_Jorse;
    public static Music Bit_Shifted;
    public static Music Mr_Smilez_Theme;
            
    //Look for ben briggs music!http://benbriggs.net/
    //His music is available under this license: https://creativecommons.org/licenses/by/3.0/
    public static void load() 
    {      
        //Menu Sounds
        clickSound = loadAudio("audio/sounds/click.wav");
        normalExplosion = loadAudio("audio/sounds/8-bit-explosion-stakkato.wav");
        
        //Menu Music
        menuMusic = loadAudio("audio/music/The_Briggs_Effect/Mystery_Gift_1.mp3");

        //Ingame Music
        Amys_Toxic_Cave = loadAudio("audio/music/The_Briggs_Effect/Amys_Toxic_Cave.mp3");
        Flight_of_the_Battery = loadAudio("audio/music/The_Briggs_Effect/Flight_of_the Battery.mp3");
        Disregard_Farm = loadAudio("audio/music/The_Briggs_Effect/Disregard_Farm.mp3");
        Moshua_Jorse = loadAudio("audio/music/The_Briggs_Effect/Moshua_Jorse.mp3");
        Bit_Shifted = loadAudio("audio/music/The_Briggs_Effect/Bit_Shifted.mp3");
        Mr_Smilez_Theme = loadAudio("audio/music/The_Briggs_Effect/Mr_Smilez_Theme.mp3");
    }
    
    public static Music loadAudio (String file) 
    {
        return Gdx.audio.newMusic(Gdx.files.internal(file));
    } 
    
    public static Music nextIngameMusic(int index)
    {
        System.out.println("Music Number: " + index);
        
        switch(index)
        {
            case 0:
                return Amys_Toxic_Cave;
                
            case 1:
                return Flight_of_the_Battery;
                
            case 2:
                return Disregard_Farm;
                
            case 3:
                return Moshua_Jorse;
                
            case 4:
                return Bit_Shifted;
                
            case 5:
                return Mr_Smilez_Theme;
        }
        
        return null;
    }
    
}

