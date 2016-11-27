/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 *
 * @author qubasa
 */
public class AudioManager 
{
    //Generate Sounds with: http://www.bfxr.net/
    
    //Menu Sounds
    public static Sound clickSound;
    public static Sound normalExplosion;
    public static Sound singleCoin;
    public static Sound gameOver;
    public static Sound bombFuse;
    public static Sound bigBombExplosion;
    
    //Menu Music
    public static Music menuMusic;
    public static Music startScreenMusic;
    public static Music looserMusic;
    public static Music winnerMusic;
    
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
        //Sounds
        clickSound = loadSound("audio/sounds/click.wav");
        normalExplosion = loadSound("audio/sounds/8-bit-explosion-stakkato.wav");
        singleCoin = loadSound("audio/sounds/coin.wav");
        gameOver = loadSound("audio/sounds/game-over.wav");
        bombFuse = loadSound("audio/sounds/bomb-fuse.ogg");
        bigBombExplosion = loadSound("audio/sounds/big-bomb-explosion.wav");
        
        //Menu Music
        menuMusic = loadMusic("audio/music/The_Briggs_Effect/Mystery_Gift_1.mp3");
        startScreenMusic = loadMusic("audio/music/8-bit-King.mp3");
        looserMusic = loadMusic("audio/music/Sneaky-Cat.mp3");
        winnerMusic = loadMusic("audio/music/The-Hard-Luck-Gang.mp3");
        
        //Ingame Music
        Amys_Toxic_Cave = loadMusic("audio/music/The_Briggs_Effect/Amys_Toxic_Cave.mp3");
        Flight_of_the_Battery = loadMusic("audio/music/The_Briggs_Effect/Flight_of_the Battery.mp3");
        Disregard_Farm = loadMusic("audio/music/The_Briggs_Effect/Disregard_Farm.mp3");
        Moshua_Jorse = loadMusic("audio/music/The_Briggs_Effect/Moshua_Jorse.mp3");
        Bit_Shifted = loadMusic("audio/music/The_Briggs_Effect/Bit_Shifted.mp3");
        Mr_Smilez_Theme = loadMusic("audio/music/The_Briggs_Effect/Mr_Smilez_Theme.mp3");
    }
    
    public static Music loadMusic (String file) 
    {
        return Gdx.audio.newMusic(Gdx.files.internal(file));
    } 
    
    public static Sound loadSound(String file)
    {
        return Gdx.audio.newSound(Gdx.files.internal(file));
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

