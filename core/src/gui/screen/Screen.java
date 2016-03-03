/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author qubasa
 */
public abstract class Screen {
    
    /**
     * WICHTIG:
     * Diese Klasse trägt zur Übersichtlichkeit bei indem sie die Lifecycle Methoden
     * von Main.java übernimmt und an alle weiteren Screen Methoden (MenuScreen, GameScreen ecc.)
     * weitergibt. Das ist praktisch damit nicht jeder Funktionsaufruf in der Main.java passieren muss sondern
     * die Main.java nur diese hier definierten Funktionen triggern muss. 
     * Die hier genannten Funktionen sind dann in den Screen Methoden implementiert und 
     * werden in den entsprechenden Klassen auch ausgeführt.
     */
    
    public abstract void create();
    
    public abstract void update();
    
    public abstract void render(SpriteBatch sb);
    
    public abstract void resize(int width, int height);
    
    public abstract void dispose();
    
    public abstract void pause();
    
    public abstract void resume();
    
}
