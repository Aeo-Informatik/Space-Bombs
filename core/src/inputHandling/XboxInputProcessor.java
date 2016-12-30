/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputHandling;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.gdx.bomberman.Constants;

/**
 *
 * @author qubasa
 */
public class XboxInputProcessor extends InputHandler implements ControllerListener
{

    public XboxInputProcessor()
    {
         
    }
    
    @Override
    public void connected(Controller cntrlr) 
    {
        System.out.println("Connected!");
    }

    @Override
    public void disconnected(Controller cntrlr) {
    }

    @Override
    public boolean buttonDown(Controller cntrlr, int buttonCode) 
    {
        anyKey = true;
        
        if(Constants.CONTROLLERKEYDEBUG)
        {
            System.out.println("Button pressed " + buttonCode);
        }
        
        if(CompleteXboxKeys.L_BUMPER == buttonCode)
        {
            scrolledDown = true;
            
        }else if(CompleteXboxKeys.R_BUMPER == buttonCode)
        {
            scrolledUp = true;
        }
        
        if(CompleteXboxKeys.A == buttonCode)
        {
            placeBombKey = true;
        }
        
        if(CompleteXboxKeys.BACK  == buttonCode || CompleteXboxKeys.START == buttonCode)
        {
            escKey = true;
        }
        
        if(CompleteXboxKeys.DPAD_DOWN == buttonCode)
        {
            goDownKey = true;
        }
        
        if(CompleteXboxKeys.DPAD_UP == buttonCode)
        {
            goUpKey = true;
        }
        
        if(CompleteXboxKeys.DPAD_RIGHT == buttonCode)
        {
            goRightKey = true;
        }
        
        if(CompleteXboxKeys.DPAD_LEFT == buttonCode)
        {
            goLeftKey = true;
        }
        
        if(CompleteXboxKeys.B == buttonCode)
        {
            remoteKey = true;
        }
        
        if(CompleteXboxKeys.X == buttonCode)
        {
            teleportKey = true;
        }
        
        return false;
    }

    @Override
    public boolean buttonUp(Controller cntrlr, int buttonCode) 
    {
        anyKey = false;
        
        if(CompleteXboxKeys.A == buttonCode)
        {
            placeBombKey = false;
        }
        
        if(CompleteXboxKeys.DPAD_DOWN == buttonCode)
        {
            goDownKey = false;
        }
        
        if(CompleteXboxKeys.BACK  == buttonCode || CompleteXboxKeys.START == buttonCode)
        {
            escKey = false;
        }
        
        if(CompleteXboxKeys.DPAD_UP == buttonCode)
        {
            goUpKey = false;
        }
        
        if(CompleteXboxKeys.DPAD_RIGHT == buttonCode)
        {
            goRightKey = false;
        }
        
        if(CompleteXboxKeys.DPAD_LEFT == buttonCode)
        {
            goLeftKey = false;
        }
        
        if(CompleteXboxKeys.B == buttonCode)
        {
            remoteKey = false;
        }
        
        if(CompleteXboxKeys.X == buttonCode)
        {
            teleportKey = false;
        }
        
        return false;
    }

    @Override
    public boolean axisMoved(Controller cntrlr, int buttonCode, float direction) 
    {
//        setAllMovementFalse();
        
        if(Constants.CONTROLLERKEYDEBUG)
        {
            System.out.println("Axis moved! Button Code: " + buttonCode + " direction: " + direction);
        }
        
//        // Wenn x Achse
//        if(CompleteXboxKeys.L_STICK_HORIZONTAL_AXIS == buttonCode)
//        {
//            // If direction positive go right
//            if(direction > 0)
//            {
//                goRightKey = true;
//            }else
//            {
//                goLeftKey = true;
//            }
//            
//        // Wenn y Achse
//        }else if(CompleteXboxKeys.L_STICK_VERTICAL_AXIS == buttonCode)
//        {
//            // If direction positive go up
//            if(direction > 0)
//            {
//                goUpKey = true;
//            }else
//            {
//                goDownKey = true;
//            }
//        }
        
        return false;
    }

    @Override
    public boolean povMoved(Controller cntrlr, int i, PovDirection pd) 
    {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller cntrlr, int i, boolean bln) 
    {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller cntrlr, int i, boolean bln) 
    {
        return false;
    }
    
    @Override
    public boolean accelerometerMoved(Controller cntrlr, int i, Vector3 vctr) 
    {
        return false;
    }
}
