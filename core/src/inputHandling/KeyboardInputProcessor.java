/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputHandling;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;

/**
 *
 * @author qubasa
 */
public class KeyboardInputProcessor extends InputHandler 
{


    @Override
    public boolean scrolled(int amount) {
        
        if(amount > 0)
        {
            scrolledUp = true;
        }
        
        if(amount < 0)
        {
            scrolledDown = true;
        }
        
        return false;
    }
    
    @Override
    public boolean keyDown(int keycode) 
    {
        anyKey = true;
        
        switch(keycode)
        {
            case Keys.A:
                goLeftKey = true;
                break;   
            case Keys.W:
                goUpKey = true;
                break;
            case Keys.S:
                goDownKey = true;
                break;
            case Keys.D:
                goRightKey = true;
                break;   
            case Keys.LEFT:
                goLeftKey = true;
                break; 
            case Keys.UP:
                goUpKey = true;
                break;
            case Keys.DOWN:
                goDownKey = true;
                break;     
            case Keys.RIGHT:
                goRightKey = true;
                break;                 
            case Keys.E:
                teleportKey = true;
                break;
            case Keys.F:
                remoteKey = true;
                break; 
            case Keys.O:
                zoomOutKey = true;
                break;
            case Keys.I:
                zoomInKey = true;
                break;
            case Keys.SPACE:
                placeBombKey = true;
                break;
            case Keys.ENTER:
                enterKey = true;
                break;
            case Keys.F12:    
                fullscreenKey = true;
                break;
            case Keys.ESCAPE:
                escKey = true;
                break;
            case Keys.NUM_1:
                slot1Key = true;
                break;
            case Keys.NUM_2:
                slot2Key = true;
                break; 
            case Keys.NUM_3:
                slot3Key = true;
                break; 
            case Keys.NUM_4:
                slot4Key = true;
                break;     
            case Keys.NUM_5:
                slot5Key = true;
                break;    
            case Keys.NUM_6:
                slot6Key = true;
                break;
            case Keys.NUM_7:
                slot7Key = true;
                break;
            case Keys.NUM_8:
                slot8Key = true;
                break;
            case Keys.NUM_9:
                slot9Key = true;
                break;
            default:
                return false;  
        }
        
        return false;
    }

    @Override
    public boolean keyUp(int keycode) 
    {
        anyKey = false;
        
        switch(keycode)
        {
            case Keys.A:
                goLeftKey = false;
                break;
            case Keys.W:
                goUpKey = false;
                break;
            case Keys.S:
                goDownKey = false;
                break;
            case Keys.D:
                goRightKey = false;
                break;
            case Keys.LEFT:
                goLeftKey = false;
                break; 
            case Keys.UP:
                goUpKey = false;
                break;
            case Keys.DOWN:
                goDownKey = false;
                break;     
            case Keys.RIGHT:
                goRightKey = false;
                break;      
            case Keys.E:
                teleportKey = false;
                break;
            case Keys.F:
                remoteKey = false;
                break;   
            case Keys.O:
                zoomOutKey = false;
                break;
            case Keys.I:
                zoomInKey = false;
                break; 
            case Keys.SPACE:
                placeBombKey = false;
                break;
            case Keys.ENTER:
                enterKey = false;
                break; 
            case Keys.F12:    
                fullscreenKey = false;
                break;    
            case Keys.ESCAPE:
                escKey = false;
                break;
            case Keys.NUM_1:
                slot1Key = false;
                break;
            case Keys.NUM_2:
                slot2Key = false;
                break; 
            case Keys.NUM_3:
                slot3Key = false;
                break; 
            case Keys.NUM_4:
                slot4Key = false;
                break;     
            case Keys.NUM_5:
                slot5Key = false;
                break;    
            case Keys.NUM_6:
                slot6Key = false;
                break;
            case Keys.NUM_7:
                slot7Key = false;
                break;
            case Keys.NUM_8:
                slot8Key = false;
                break;
            case Keys.NUM_9:
                slot9Key = false;
                break;    
            default:
                return false;      
        } 
        
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        anyKey = true;

        if(button == Buttons.LEFT)
        {
            leftButton = true;
        }
        
        if(button == Buttons.RIGHT)
        {
           rightButton = true;
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int buttonCode) {

        anyKey = false;

        if(buttonCode == Buttons.LEFT)
        {
            leftButton = false;
        }
        
        if(buttonCode == Buttons.RIGHT)
        {
           rightButton = false;
        }
        
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

        @Override
    public boolean keyTyped(char character) 
    {
        return false;
    }
}
