/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.gdx.bomberman.Constants;

/**
 *
 * @author qubasa
 */
public class AudioManager 
{
    //Generate Sounds with: http://www.bfxr.net/
    
    //Volume
    private static float soundVolume = Constants.DEFAULTSOUNDVOLUME;
    private static float musicVolume = Constants.DEFAULTMUSICVOLUME;
    
    //Menu Sounds
    private static Sound clickSound;
    private static Sound normalExplosion;
    private static Sound singleCoin;
    private static Sound gameOver;
    private static Sound bombFuse;
    private static Sound bigBombExplosion;
    private static Sound alienTimerSound;
    private static Sound granadeClipDrop;
    private static Sound teleport;
    private static Sound barrelPlace;
    private static Sound remoteBombPlace;
    private static Sound error;
    private static Sound blackHole;
    private static Sound blackHoleImplosion;
    
    //Menu Music
    private static Music menuMusic;
    private static Music startScreenMusic;
    private static Music looserMusic;
    private static Music winnerMusic;
    
    //Ingame Music
    private static Music Amys_Toxic_Cave;
    private static Music Flight_of_the_Battery;
    private static Music Disregard_Farm;      
    private static Music Moshua_Jorse;
    private static Music Bit_Shifted;
    private static Music Mr_Smilez_Theme;
    
    private static Music currentMusic = null;
    
    //Look for ben briggs music!http://benbriggs.net/
    //His music is available under this license: https://creativecommons.org/licenses/by/3.0/
    public static void load() 
    {      
        // Sounds
        clickSound = loadSound("audio/sounds/click.wav");
        normalExplosion = loadSound("audio/sounds/8-bit-explosion-stakkato.wav");
        singleCoin = loadSound("audio/sounds/coin.wav");
        gameOver = loadSound("audio/sounds/game-over.wav");
        bombFuse = loadSound("audio/sounds/bomb-fuse.ogg");
        bigBombExplosion = loadSound("audio/sounds/big-bomb-explosion.wav");
        alienTimerSound = loadSound("audio/sounds/alien-bomb-timer.wav");
        granadeClipDrop = loadSound("audio/sounds/granade-clip-drop.wav");
        teleport = loadSound("audio/sounds/teleport.wav");
        barrelPlace = loadSound("audio/sounds/barrel-place.wav");
        remoteBombPlace = loadSound("audio/sounds/bomb-arming.wav");
        error = loadSound("audio/sounds/error.wav");
        blackHole = loadSound("audio/sounds/black-hole.mp3");
        blackHoleImplosion = loadSound("audio/sounds/big-imploding-bomb.mp3");
        
        // Menu Music
        menuMusic = loadMusic("audio/music/The_Briggs_Effect/Mystery_Gift_1.mp3");
        startScreenMusic = loadMusic("audio/music/Eric_Matyas/8-bit-King.mp3");
        looserMusic = loadMusic("audio/music/Eric_Matyas/Sneaky-Cat.mp3");
        winnerMusic = loadMusic("audio/music/Eric_Matyas/The-Hard-Luck-Gang.mp3");
        
        // Ingame Music
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
                return getAmys_Toxic_Cave();
                
            case 1:
                return getFlight_of_the_Battery();
                
            case 2:
                return getDisregard_Farm();
                
            case 3:
                return getMoshua_Jorse();
                
            case 4:
                return getBit_Shifted();
                
            case 5:
                return getMr_Smilez_Theme();
        }
        
        return null;
    }


    /**
     * @return the normalExplosion
     */
    public static Sound getNormalExplosion() {
        return normalExplosion;
    }

    public static void playClickSound()
    {
        long id = clickSound.play();
        clickSound.setVolume(id, AudioManager.getSoundVolume() * Constants.CLICKSOUNDSMOD);
    }
    
    /**
     * @return the singleCoin
     */
    public static Sound getSingleCoin() {
        return singleCoin;
    }

    /**
     * @return the gameOver
     */
    public static Sound getGameOver() {
        return gameOver;
    }

    /**
     * @return the bombFuse
     */
    public static Sound getBombFuse() {
        return bombFuse;
    }

    /**
     * @return the bigBombExplosion
     */
    public static Sound getBigBombExplosion() {
        return bigBombExplosion;
    }

    /**
     * @return the alienTimerSound
     */
    public static Sound getAlienTimerSound() {
        return alienTimerSound;
    }

    /**
     * @return the menuMusic
     */
    public static Music getMenuMusic() {
        return menuMusic;
    }

    /**
     * @return the startScreenMusic
     */
    public static Music getStartScreenMusic() {
        return startScreenMusic;
    }

    /**
     * @return the looserMusic
     */
    public static Music getLooserMusic() {
        return looserMusic;
    }

    /**
     * @return the winnerMusic
     */
    public static Music getWinnerMusic() {
        return winnerMusic;
    }

    /**
     * @return the Amys_Toxic_Cave
     */
    public static Music getAmys_Toxic_Cave() {
        return Amys_Toxic_Cave;
    }

    /**
     * @return the Flight_of_the_Battery
     */
    public static Music getFlight_of_the_Battery() {
        return Flight_of_the_Battery;
    }

    /**
     * @return the Disregard_Farm
     */
    public static Music getDisregard_Farm() {
        return Disregard_Farm;
    }

    /**
     * @return the Moshua_Jorse
     */
    public static Music getMoshua_Jorse() {
        return Moshua_Jorse;
    }

    /**
     * @return the Bit_Shifted
     */
    public static Music getBit_Shifted() {
        return Bit_Shifted;
    }

    /**
     * @return the Mr_Smilez_Theme
     */
    public static Music getMr_Smilez_Theme() {
        return Mr_Smilez_Theme;
    }

    /**
     * @return the granadeClipDrop
     */
    public static Sound getGranadeClipDrop() {
        return granadeClipDrop;
    }

    /**
     * @return the teleport
     */
    public static Sound getTeleport() {
        return teleport;
    }

    /**
     * @return the barrelPlace
     */
    public static Sound getBarrelPlace() {
        return barrelPlace;
    }

    /**
     * @return the remoteBombPlace
     */
    public static Sound getRemoteBombPlace() {
        return remoteBombPlace;
    }

    /**
     * @param aRemoteBombPlace the remoteBombPlace to set
     */
    public static void setRemoteBombPlace(Sound aRemoteBombPlace) {
        remoteBombPlace = aRemoteBombPlace;
    }

    /**
     * @return the error
     */
    public static Sound getError() {
        return error;
    }

    /**
     * @return the soundVolume
     */
    public static float getSoundVolume() {
        return soundVolume;
    }

    /**
     * @param soundVolume the soundVolume to set
     */
    public static void setSoundVolume(float soundVolume) {
        AudioManager.soundVolume = soundVolume;
    }

    /**
     * @return the musicVolume
     */
    public static float getMusicVolume() {
        return musicVolume;
    }

    /**
     * @param musicVolume the musicVolume to set
     */
    public static void  setMusicVolume(float musicVolume) 
    {
        AudioManager.musicVolume = musicVolume;
    }

    /**
     * @return the blackHole
     */
    public static Sound getBlackHole() {
        return blackHole;
    }

    /**
     * @return the blackHoleImplosion
     */
    public static Sound getBlackHoleImplosion() {
        return blackHoleImplosion;
    }

    /**
     * @return the currentMusic
     */
    public static Music getCurrentMusic() {
        return currentMusic;
    }

    /**
     * @param aCurrentMusic the currentMusic to set
     */
    public static void setCurrentMusic(Music aCurrentMusic) {
        currentMusic = aCurrentMusic;
    }
    
}

