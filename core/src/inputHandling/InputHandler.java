/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author qubasa
 */
public class InputHandler implements InputProcessor
{
    public InputHandler()
    {
        
    }


    protected  static boolean scrolledUp = false;
    protected  static boolean scrolledDown = false;
    protected  static boolean leftButton = false;
    protected  static boolean rightButton = false;
    protected  static boolean goLeftKey = false;
    protected  static boolean goUpKey = false;
    protected  static boolean goDownKey = false;
    protected  static boolean goRightKey = false;
    protected  static boolean teleportKey = false;
    protected  static boolean remoteKey = false;
    protected  static boolean escKey = false;
    protected  static boolean enterKey = false;
    protected  static boolean zoomInKey = false;
    protected  static boolean zoomOutKey = false;
    protected  static boolean placeBombKey = false;
    protected  static boolean fullscreenKey = false;
    protected  static boolean anyKey = false;
    protected  static boolean slot1Key = false;
    protected  static boolean slot2Key = false;
    protected  static boolean slot3Key = false;
    protected  static boolean slot4Key = false;
    protected  static boolean slot5Key = false;
    protected  static boolean slot6Key = false;
    protected  static boolean slot7Key = false;
    protected  static boolean slot8Key = false;
    protected  static boolean slot9Key = false;

    private InputMultiplexer multiInput;
    private KeyboardInputProcessor keyboardInput;
    private XboxInputProcessor xboxInput;
    
    public void setInputSource(Stage stage)
    {
        multiInput = new InputMultiplexer();
        keyboardInput = new KeyboardInputProcessor();
        xboxInput = new XboxInputProcessor();
        
        multiInput.addProcessor(stage);
        multiInput.addProcessor(keyboardInput);
        multiInput.addProcessor(xboxInput);
        
        Controllers.addListener(xboxInput);
        Gdx.input.setInputProcessor(multiInput);
    }
    
    
    /**
     * @return the anyKey
     */
    public boolean isAnyKey() {
        return anyKey;
    }

    /**
     * @return the escKey
     */
    public boolean isEscKey() 
    {
        if(escKey)
        {
            escKey = false;
            return true;
        }
        return escKey;
    }

    /**
     * @return the fullScreenkey
     */
    public boolean isFullScreenkey() 
    {
        if(fullscreenKey)
        {
            fullscreenKey = false;
            return true;
        }
        return fullscreenKey;
    }
    
        @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    
    /*------------------GETTER------------------*/
    /**
     * @return the leftButton
     */
    public boolean isLeftButton() {
        return leftButton;
    }

    /**
     * @return the rightButton
     */
    public boolean isRightButton() {
        return rightButton;
    }

    /**
     * @return the scrolledUp
     */
    public boolean isScrolledUp() 
    {
        if(scrolledUp)
        {
            scrolledUp = false;
            return true;
        }
        
        return false;
    }

    /**
     * @return the scrolledDown
     */
    public boolean isScrolledDown() {
        
        if(scrolledDown)
        {
            scrolledDown = false;
            return true;
        }
        
        return false;
    }

    /**
     * @return the goLeftKey
     */
    public boolean isGoLeftKey() {
        return goLeftKey;
    }

    /**
     * @return the goUpKey
     */
    public boolean isGoUpKey() {
        return goUpKey;
    }

    /**
     * @return the goDownKey
     */
    public boolean isGoDownKey() {
        return goDownKey;
    }

    /**
     * @return the goRightKey
     */
    public boolean isGoRightKey() {
        return goRightKey;
    }

    /**
     * @return the teleportKey
     */
    public boolean isTeleportKey() {
        return teleportKey;
    }

    /**
     * @return the remoteKey
     */
    public boolean isRemoteKey() {
        return remoteKey;
    }

    /**
     * @return the zoomInKey
     */
    public boolean isZoomInKey() {
        return zoomInKey;
    }

    /**
     * @return the zoomOutKey
     */
    public boolean isZoomOutKey() {
        return zoomOutKey;
    }

    /**
     * @return the placeBombKey
     */
    public boolean isPlaceBombKey() {
        return placeBombKey;
    }

    /**
     * @return the slot1Key
     */
    public boolean isSlot1Key() {
        return slot1Key;
    }

    /**
     * @return the slot2Key
     */
    public boolean isSlot2Key() {
        return slot2Key;
    }

    /**
     * @return the slot3Key
     */
    public boolean isSlot3Key() {
        return slot3Key;
    }

    /**
     * @return the slot4Key
     */
    public boolean isSlot4Key() {
        return slot4Key;
    }

    /**
     * @return the slot5Key
     */
    public boolean isSlot5Key() {
        return slot5Key;
    }

    /**
     * @return the slot6Key
     */
    public boolean isSlot6Key() {
        return slot6Key;
    }

    /**
     * @return the slot7Key
     */
    public boolean isSlot7Key() {
        return slot7Key;
    }

    /**
     * @return the slot9Key
     */
    public boolean isSlot9Key() {
        return slot9Key;
    }

    /**
     * @return the slot8Key
     */
    public boolean isSlot8Key() {
        return slot8Key;
    }

    public boolean isEnterKey() {
        return enterKey;
    }

    /*----------------------SETTER---------------------*/
    public static void setScrolledUp(boolean scrolledUp) {
        InputHandler.scrolledUp = scrolledUp;
    }

    public static void setScrolledDown(boolean scrolledDown) {
        InputHandler.scrolledDown = scrolledDown;
    }

    public static void setLeftButton(boolean leftButton) {
        InputHandler.leftButton = leftButton;
    }

    public static void setRightButton(boolean rightButton) {
        InputHandler.rightButton = rightButton;
    }

    public static void setGoLeftKey(boolean goLeftKey) {
        InputHandler.goLeftKey = goLeftKey;
    }

    public static void setGoUpKey(boolean goUpKey) {
        InputHandler.goUpKey = goUpKey;
    }

    public static void setGoDownKey(boolean goDownKey) {
        InputHandler.goDownKey = goDownKey;
    }

    public static void setGoRightKey(boolean goRightKey) {
        InputHandler.goRightKey = goRightKey;
    }

    public static void setTeleportKey(boolean teleportKey) {
        InputHandler.teleportKey = teleportKey;
    }

    public static void setRemoteKey(boolean remoteKey) {
        InputHandler.remoteKey = remoteKey;
    }

    public static void setEscKey(boolean escKey) {
        InputHandler.escKey = escKey;
    }

    public static void setEnterKey(boolean enterKey) {
        InputHandler.enterKey = enterKey;
    }

    public static void setZoomInKey(boolean zoomInKey) {
        InputHandler.zoomInKey = zoomInKey;
    }

    public static void setZoomOutKey(boolean zoomOutKey) {
        InputHandler.zoomOutKey = zoomOutKey;
    }

    public static void setPlaceBombKey(boolean placeBombKey) {
        InputHandler.placeBombKey = placeBombKey;
    }

    public static void setFullscreenKey(boolean fullscreenKey) {
        InputHandler.fullscreenKey = fullscreenKey;
    }

    public static void setAnyKey(boolean anyKey) {
        InputHandler.anyKey = anyKey;
    }

    public static void setSlot1Key(boolean slot1Key) {
        InputHandler.slot1Key = slot1Key;
    }

    public static void setSlot2Key(boolean slot2Key) {
        InputHandler.slot2Key = slot2Key;
    }

    public static void setSlot3Key(boolean slot3Key) {
        InputHandler.slot3Key = slot3Key;
    }

    public static void setSlot4Key(boolean slot4Key) {
        InputHandler.slot4Key = slot4Key;
    }

    public static void setSlot5Key(boolean slot5Key) {
        InputHandler.slot5Key = slot5Key;
    }

    public static void setSlot6Key(boolean slot6Key) {
        InputHandler.slot6Key = slot6Key;
    }

    public static void setSlot7Key(boolean slot7Key) {
        InputHandler.slot7Key = slot7Key;
    }

    public static void setSlot8Key(boolean slot8Key) {
        InputHandler.slot8Key = slot8Key;
    }

    public static void setSlot9Key(boolean slot9Key) {
        InputHandler.slot9Key = slot9Key;
    }

    public static void resetMovementKeys()
    {
        InputHandler.goDownKey = false;
        InputHandler.goUpKey = false;
        InputHandler.goRightKey = false;
        InputHandler.goLeftKey = false;
    }

    @Override
    public boolean keyTyped(char character) {
       return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
    public boolean scrolled(int amount) {
        return false;
    }
    
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
}
