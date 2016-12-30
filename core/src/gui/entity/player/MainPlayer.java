/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.bomberman.Constants;

import java.util.Random;

import client.SendCommand;
import gui.AnimEffects;
import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.bomb.Barrel;
import gui.entity.bomb.BlackHole;
import gui.entity.bomb.Dynamite;
import gui.entity.bomb.Infinity;
import gui.entity.bomb.Mine;
import gui.entity.bomb.NormalBomb;
import gui.entity.bomb.RemoteBomb;
import gui.entity.bomb.Teleport;
import gui.entity.bomb.Turret;
import gui.entity.bomb.X3;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import gui.screen.MainPlayerHud;
import inputHandling.InputHandler;

/**
 *
 * @author qubasa
 */
public class MainPlayer extends Player
{
    /**------------------VARIABLES------------------**/
    // Player settings
    private float coinBonusTimer = Constants.COINBONUSTIMER;
    private int coinBonus = Constants.COINBONUS;
    private int life = Constants.DEFAULTLIFE;
    private float godModeDuration = Constants.GODMODEDURATION; // How long the player is invulnerable after beeing hit by a bomb
    private int coins = Constants.STARTCOINS;
    private int maxBombPlacing = Constants.DEFAULTBOMBPLACE;
    private int bombRange = Constants.DEFAULTBOMBRANGE;
    private int cubicRange = Constants.DEFAULTCUBICRANGE;
    private float maxZoomOut = Constants.DEFAULTMAXZOOMOUT;
    private float maxZoomIn = Constants.DEFAULTMAXZOOMIN;
    
    // Bomb prices
    private int[] bombPrices = 
    {
        Constants.BOMB1, 
        Constants.BOMB2, 
        Constants.BOMB3, 
        Constants.BOMB4, 
        Constants.BOMB5, 
        Constants.BOMB6, 
        Constants.BOMB7, 
        Constants.BOMB8, 
        Constants.BOMB9,
        //Constants.BOMB10
    };
    
    // Other variables
    private int playerId = 0;
    private int chosenBomb = 1;
    private float coinTimer = 0;
    private float coinBonusIncreaseTimer;
    private float placeBombTimer = 0;
    private float placeBombTimerEnd = 0.12f;
    private float teleportCooldownTimer = Constants.TELEPORTCOOLDOWNTIMER;
    private float teleportCooldownTimerEnd = Constants.TELEPORTCOOLDOWNTIMER;
    private float errorMessageTimer = 0;
    private float errorMessageTimerEnd = 1;
    private float remoteTriggerTimer = 0;
    private float remoteTriggerTimerEnd = 0.5f;
    private float sendStopCommandTimer = 0;
    private float sendStopCommandTimerEnd = 0.1f;
    private boolean fistCoolDown = false;
    
    // Error sound
    private Sound errorSound;
    private long errorId;
    
    
    //God mode
    private float godModeTimer = 0;
    private boolean godMode = false;
    
    //Objects
    private AnimEffects animEffects = new AnimEffects();
    private SendCommand sendCommand;
    private InputHandler inputHandler;
    
    //Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    /**------------------CONSTRUCTOR------------------**/
    public MainPlayer(ThinGridCoordinates pos, ThinGridCoordinates direction, int playerId, OrthographicCamera camera, MapLoader map, EntityManager entityManager, SendCommand sendCommand) 
    {
        super(pos, direction, map, entityManager, camera);

        //Set camera position to player
        camera.position.set(pos.getX(), pos.getY(), 0);
        
        //Object setter
        this.playerId = playerId;
        this.sendCommand = sendCommand;
        this.inputHandler = entityManager.getInputHandler();
        this.errorSound = AudioManager.getError();
       
        
        //Get apropriate player texture based on player id
        switch(playerId)
        {
            case 1:
                this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p1WalkingRightAnim;
                break;

            case 2:
                this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                break;

            case 3:
                this.walkAnimUp = TextureManager.p3WalkingUpAnim;
                this.walkAnimDown = TextureManager.p3WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p3WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p3WalkingRightAnim;
                break;

            case 4:
                this.walkAnimUp = TextureManager.p4WalkingUpAnim;
                this.walkAnimDown = TextureManager.p4WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p4WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p4WalkingRightAnim;
                break;

            default:
                System.err.println("ERROR: Wrong player number in MainPlayer. Using default p1");
                this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p1WalkingRightAnim;
        }
    }


    @Override
    public void render()
    {
        renderObject.setProjectionMatrix(camera.combined);
        
        errorMessageTimer += Constants.DELTATIME;
        teleportCooldownTimer += Constants.DELTATIME;
        remoteTriggerTimer += Constants.DELTATIME;
        sendStopCommandTimer += Constants.DELTATIME;
        
        if(Constants.COLLIISIONDETECTIONDEBUG)
        {
            collisionTest();
        }
   
        renderObject.begin();

            // Gives out every couple of seconds some coins to the player
            coinBonus();
        
            // Set the camera to follow the player
            cameraFollowPlayer(pos);
            
            // Move player through keyboard
            inputMovePlayer();
            
            // Player actions
            inputDoPlayer();

            // Hit detection
            hitByBomb();

        renderObject.end();
    }

    
    /**------------------OTHER FUNCTIONS------------------**/
    private void coinBonus()
    {
        
        
        if(coinBonusIncreaseTimer >= Constants.INCREASECOINBONUSTIMEINTERVALL)
        {
            if(coinBonus < Constants.MAXCOINBONUS)
            {
                coinBonus += Constants.INCREASECOINBONUS;
                coinBonusIncreaseTimer = 0;
                System.out.println("INCREASED COIN BONUS TO: " + coinBonus);
                MainPlayerHud.printToScreen("Coin bonus +5");
            }
        }else
        {
            coinBonusIncreaseTimer += Constants.DELTATIME;
        }
        
        if(coinTimer >= coinBonusTimer)
        {
            coins += getCoinBonus();
            sendCommand.setPlayerCoins(playerId, coins);
            coinTimer = 0;
        }else
        {
            coinTimer += Constants.DELTATIME;
        }
    }
    
    /**
     * Execute on player death
     */
    long id2 = -1;
    public void onDeath()
    {
        entityManager.getItemManager().spawnTombstone(new MapCellCoordinates(pos), coins, playerId);
        entityManager.getBombManager().triggerRemoteBomb(playerId);
        
        //Execute hit sound
        if(id2 == -1)
        {
            id2 = AudioManager.getGameOver().play();
            AudioManager.getGameOver().setVolume(id2, AudioManager.getSoundVolume());
        }
        
        sendCommand.setPlayerLife(playerId, 0);
    }
    

    /**
     * Player gets hit by bomb
     */
    Thread flashThread;
    private void hitByBomb() 
    {  
        //If player touches explosion
        if(touchesDeadlyBlock() && godMode == false)
        {
            
            //Reduce player life
            life -= 1;
            //Set invulnerability to true
            godMode = true;
            
            
            //Send playerLife command to server
            sendCommand.setPlayerLife(playerId, life);
            sendCommand.hitByBombAnimation(playerId);
            
            //Debug
            if(Constants.CLIENTDEBUG)
            {
                System.out.println("Invulnerability activated");
            }

            //Lets the player flash and saves the thread object to be able to stop it manually
            flashThread = animEffects.flashing(godModeDuration, 3, renderObject);
        }
        
        //Timer for godmode duration after hit
        if(godModeTimer < godModeDuration && godMode == true)
        {
            godModeTimer += Constants.DELTATIME;
            
        }else if(godMode == true)
        {
            godMode = false;
            godModeTimer = 0;
            
            //Stops the blinkAnimation thread, it is more precise than using only the godModeDuration
            if(flashThread != null)
            {
                flashThread.interrupt();
            }else
            {
                System.err.println("ERROR: Someone gave himself godMode!");
            }
            
            if(Constants.CLIENTDEBUG)
            {
                System.out.println("Invulnerability deactivated");
            }
        }
    }

    /**------------------MOVEMENT------------------**/
    private void inputMovePlayer()
    {
        // Go left
        if (inputHandler.isGoLeftKey())
        {
            moveLeft();
            
        }else if (inputHandler.isGoRightKey())
        {
            moveRight();
            
        }else if (inputHandler.isGoUpKey())
        {
            moveUp();
            
        }else if (inputHandler.isGoDownKey())
        {
            
            moveDown();
            
        }else
        {
            stopMoving();
        }
    }
    
    private float sendTimer = 0;
    private float sendTimerEnd = 0.1f;
    private String previousPlayerOrientation = "";
    private void sendMoveCommand()
    {
        if(sendTimer >= sendTimerEnd || !previousPlayerOrientation.equalsIgnoreCase(playerOrientation))
        {
            sendCommand.movePlayer(playerId, pos, super.playerOrientation);
            previousPlayerOrientation = super.playerOrientation;
            sendTimer = 0;
        }else
        {
            sendTimer += Constants.DELTATIME;
        }
    }
    
    private void moveLeft()
    {
        if(!collidesLeft() && !collidesLeftBomb())
        {
           
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimLeft, true), pos.getX(), pos.getY());
            
            // Set direction
            super.goLeft();
            
            sendMoveCommand();
        }else
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimLeft, true), pos.getX(), pos.getY());
        }
    }
    
    private void moveRight()
    {
        if(!collidesRight() && !collidesRightBomb())
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimRight, true), pos.getX(), pos.getY());
            
            // Set direction
            super.goRight();
            
            sendMoveCommand();
        }else
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimRight, true), pos.getX(), pos.getY());
        }
    }
    
    private void moveUp()
    {
        if(!collidesTop() && !collidesTopBomb())
        {

            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimUp, true), pos.getX(), pos.getY());

            //Smoothly changes the direction
            goUp();
            
            sendMoveCommand();
        }else
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimUp, true), pos.getX(), pos.getY());
        }
    }
    
    private void moveDown()
    {
        if(!collidesBottom() && !collidesBottomBomb())
        {

            renderObject.draw(animEffects.getFrame(walkAnimDown, true), pos.getX(), pos.getY());

            goDown();
            
            sendMoveCommand();
        }else
        {
            renderObject.draw(animEffects.getFrame(walkAnimDown, true), pos.getX(), pos.getY());
        }
    }
    
    private void stopMoving()
    {
        if(sendStopCommandTimer >= sendStopCommandTimerEnd)
        {
            sendStopCommandTimer = 0;
            //Send stop command to server
            sendCommand.stopMoving(playerId, pos);
        }
        
        goNone();

        //Draws the player if he stands still
        switch(super.playerOrientation)
        {
           case Constants.LEFT:
                renderObject.draw((TextureRegion) walkAnimLeft.getKeyFrame(0), pos.getX(), pos.getY());
                break;

            case Constants.RIGHT:
                renderObject.draw((TextureRegion) walkAnimRight.getKeyFrame(0), pos.getX(), pos.getY());
                break;

            case Constants.UP:
                renderObject.draw((TextureRegion) walkAnimUp.getKeyFrame(0), pos.getX(), pos.getY());
                break;

            case Constants.DOWN:
                renderObject.draw((TextureRegion) walkAnimDown.getKeyFrame(0), pos.getX(), pos.getY());
                break;
        }
    }
    
    
    
    /**------------------PLAYER ACTIONS------------------**/
    private void inputDoPlayer()
    {    
        /*------------------PLACE BOMB------------------*/
        if ((inputHandler.isPlaceBombKey() || inputHandler.isLeftButton()) && placeBombTimer >= placeBombTimerEnd)
        {
            placeBombTimer = 0;
            String bombType;
            
            //Checks if there is already a bomb
            if(entityManager.getPlayerManager().getPlayerIdInCell(new MapCellCoordinates(this.getPlaceItemLocation())) == playerId && !map.isBombPlaced(new MapCellCoordinates(this.getPlaceItemLocation())) && maxBombPlacing > entityManager.getBombManager().getMainArraySize())
            {
                switch (getChosenBomb())
                {
                    case(1):

                        bombType = "default";

                        // Check if you have enough coins
                        if(coins >= getBombPrice(1))
                        {
                            coins -= getBombPrice(1);

                            //Send bomb command to server
                            sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);

                            entityManager.getBombManager().spawnBomb(new NormalBomb(getPlaceItemLocation(), playerId, bombRange, map, entityManager));
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        break;
                    
                    case(2):

                        bombType = "dynamite";

                        //Checks if there is already a bomb
                        if(coins>= getBombPrice(2))
                        {
                            coins -= getBombPrice(2);

                            //Send bomb command to server
                            sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);

                            entityManager.getBombManager().spawnBomb(new Dynamite(getPlaceItemLocation(), playerId, cubicRange, map, entityManager));
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        break;

                    case(3):
                        //Checks if there is already a bomb
                        if(coins >= getBombPrice(3))
                        {
                            coins -= getBombPrice(3);
                            
                            //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                            int explodePath = new Random().nextInt(4) +1;
                            
                            entityManager.getBombManager().spawnBomb(new Infinity(getPlaceItemLocation(), playerId, bombRange, explodePath, map, entityManager));
        
                            sendCommand.placeInfinityBomb(playerId, this.getPlaceItemLocation(), explodePath);
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        break;

                    case(4):

                        bombType = "X3";

                        //Checks if there is already a bomb
                        if(coins >= getBombPrice(4))
                        {
                            coins -= getBombPrice(4);

                            //Send bomb command to server
                            sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);
                            entityManager.getBombManager().spawnBomb(new X3(getPlaceItemLocation(), playerId, bombRange, 1, maxBombPlacing,  map, entityManager));
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        break;
                    
                    case (5):
                        
                        bombType = "Mine";
                        
                        //Checks if there is already a bomb
                        if(coins>= getBombPrice(5))
                        {
                            coins -= getBombPrice(5);

                            //Send bomb command to server
                            sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);

                            entityManager.getBombManager().spawnBomb(new Mine(getPlaceItemLocation(), playerId, bombRange,  map, entityManager));
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        
                        break;
                        
                    case (6):
                            bombType = "BlackHole";

                            //Checks if there is already a bomb
                            if(coins >= getBombPrice(6))
                            {
                            coins -= getBombPrice(6);

                            //Send bomb command to server
                            sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);

                            //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                            entityManager.getBombManager().spawnBomb(new BlackHole(this.getPlaceItemLocation(), playerId, cubicRange, map, entityManager));
                            }else
                            {
                                errorMessage("Not enough money!", true);
                            }

                        break;    
                        
                    case(7):

                        bombType = "Remote";
                        
                        //Checks if there is already a bomb
                        if(coins>= getBombPrice(7))
                        {
                            coins -= getBombPrice(7);

                            //Send bomb command to server
                            sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);

                            //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                            entityManager.getBombManager().spawnRemote(new RemoteBomb(getPlaceItemLocation(), playerId, bombRange,  map, entityManager));
                
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        break;    
                        
                    case(8):

                        bombType = "Barrel";

                        //Checks if there is already a bomb
                        if(coins >= getBombPrice(8) )
                        {
                            if(entityManager.getBombManager().getMainArraySize() + 2 <= maxBombPlacing )
                            {
                                coins -= getBombPrice(8);

                                //Send bomb command to server
                                sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);

                                entityManager.getBombManager().spawnBomb(new Barrel(getPlaceItemLocation(), playerId, cubicRange,  map, entityManager));
                            }
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        break;
                        
                    case(9):
                        bombType = "Turret";
                        
                        //Checks if there is already a bomb
                        if(coins>= getBombPrice(9))
                        {
                            coins -= getBombPrice(9);

                            //Send bomb command to server
                            sendCommand.placeBomb(playerId, this.getPlaceItemLocation(), bombType);

                            //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                            entityManager.getBombManager().spawnBomb(new Turret(getPlaceItemLocation(), playerId, bombRange,  map, entityManager));
                        }else
                        {
                            errorMessage("Not enough money!", true);
                        }
                        break;
                }
            }
        }else
        {
            placeBombTimer += Constants.DELTATIME;
        }
        
        /*------------------CHOOSE BOMB------------------*/
        if (inputHandler.isSlot1Key())
        {
            setChosenBomb(1);
        }
        
        if (inputHandler.isSlot2Key())
        {
            setChosenBomb(2);
        }
        
        if (inputHandler.isSlot3Key())
        {
            setChosenBomb(3);
        }
        
        if (inputHandler.isSlot4Key())
        {
            setChosenBomb(4);
        }
         
        if (inputHandler.isSlot5Key())
        {
            setChosenBomb(5);
        }
        
        if (inputHandler.isSlot6Key())
        {
            setChosenBomb(6);
        }
        
        if (inputHandler.isSlot7Key())
        {
            setChosenBomb(7);
        }
        
        if (inputHandler.isSlot8Key())
        {
            setChosenBomb(8);
        }
        
        if (inputHandler.isSlot9Key())
        {
            setChosenBomb(9);
        }
        
        /*------------------ZOOM OUT OF GAME------------------*/
        if (inputHandler.isZoomOutKey())
        {
            if(camera.zoom < maxZoomOut)
            {
                camera.zoom += 0.02;
                camera.position.set(pos.getX(), pos.getY(), 0);
            }
        }

        /*------------------ZOOM INTO GAME------------------*/
        if (inputHandler.isZoomInKey())
        {
            if(camera.zoom > maxZoomIn)
            {
                camera.zoom -= 0.02;
                camera.position.set(pos.getX(), pos.getY(), 0);
            }
        }
        
        /*------------------TRIGGER REMOTE BOMB------------------*/
        if(inputHandler.isRemoteKey()|| inputHandler.isRightButton())
        {
            if(remoteTriggerTimer >= remoteTriggerTimerEnd)
            {
                sendCommand.triggerRemoteBomb(playerId);
                remoteTriggerTimer = 0;
                entityManager.getBombManager().triggerRemoteBomb(playerId);
            }
        }
        
        /*------------------INSTANT TELEPORT------------------*/
        
        if(inputHandler.isTeleportKey())
        {
            if(teleportCooldownTimer >= teleportCooldownTimerEnd)
            {
                fistCoolDown = true;
                teleportCooldownTimer = 0;
                entityManager.getBombManager().spawnBomb(new Teleport(this.getPlaceItemLocation(), playerId, map, entityManager));
            }else
            {
                teleportCooldownTimer += Constants.DELTATIME;
                if(fistCoolDown)
                {
                    fistCoolDown = false;
                    errorMessage((int) (teleportCooldownTimerEnd - teleportCooldownTimer) + "s cooldown!", false);
                }else
                {
                    errorMessage((int) (teleportCooldownTimerEnd - teleportCooldownTimer) + "s cooldown!", true);
                }
            }
        }
        
        /*------------------CHOOSE BOMB WITH MOUSE WHEEL------------------*/
        if(inputHandler.isScrolledUp())
        {
            if(getChosenBomb() < 9)
            {
                setChosenBomb(getChosenBomb() + 1);
            }else
            {
                setChosenBomb(1);
            }
            
            sendCommand.setChoosenBomb(chosenBomb, playerId);
        }
        
        if(inputHandler.isScrolledDown())
        {
            if(getChosenBomb() > 1)
            {
                setChosenBomb(getChosenBomb() - 1);
            }else
            {
                setChosenBomb(9);
            }
            
            sendCommand.setChoosenBomb(chosenBomb, playerId);
        }
    }

    private void errorMessage(String message, boolean playErrorSound)
    {
        if(errorMessageTimer >= errorMessageTimerEnd)
        {
            errorMessageTimer = 0;
            MainPlayerHud.printToScreen(message);
            if(playErrorSound)
            {
                errorId = errorSound.play();
                errorSound.setVolume(errorId, AudioManager.getSoundVolume());
            }
        }
    }
    
    
    
    /*------------------ GETTER & SETTER ------------------*/  
    public int getBombPrice(int bombNumber) // Used by main player hud
    {
        return bombPrices[bombNumber -1];
    }
    
    public void setBombPrice(int bombNumber, int price)
    {
        bombPrices[bombNumber -1] = price;
        sendCommand.setBombCost(bombNumber, price, playerId);
    }
    
    public float getMaxZoomIn()
    {
        return maxZoomIn;
    }
    
    public void setMaxZoomIn(float maxZoomIn)
    {
        this.maxZoomIn = maxZoomIn;
    }
    
    public float getMaxZoomOut()
    {
        return maxZoomIn;
    }
    
    public void setMaxZoomOut(float maxZoomOut)
    {
        this.maxZoomOut = maxZoomOut;
    }
    
    public int getLife()
    { 
        return this.life;
    }
    
    public void setLife(int life)
    {
        this.life = life;
    }
    
    public int getMaxBombs()
    {
        return this.maxBombPlacing;
    }

    public int getCoins()
    {
        return this.coins;
    }

    public int getPlayerId() 
    {
        return playerId;
    }

    public void setPlayerId(int playerId) 
    {
        this.playerId = playerId;
    }

    public int getMaxBombPlacing() 
    {
        return maxBombPlacing;
    }

    public void setMaxBombPlacing(int maxBombPlacing) 
    {
        this.maxBombPlacing = maxBombPlacing;
    }

    public int getBombRange() 
    {
        return bombRange;
    }

    public void setBombRange(int BombRange) 
    {
        this.bombRange = BombRange;
    }

    public void setCoins(int coins) 
    {
        this.coins = coins;
    }
    
    public int getChoosenBomb()
    {
        return this.getChosenBomb();
    }

    public int getCubicRange() {
        return cubicRange;
    }

    public void setCubicRange(int cubicRange) {
        this.cubicRange = cubicRange;
    }

    public void setZoom(float zoom)
    {
        camera.zoom = zoom;
        camera.position.set(pos.getX(), pos.getY(), 0);
    }

    /**
     * @return the coinBonus
     */
    public int getCoinBonus() {
        return coinBonus;
    }

    /**
     * @param coinBonus the coinBonus to set
     */
    public void setCoinBonus(int coinBonus) {
    this.coinBonus = coinBonus;
    }

    /**
     * @return the chosenBomb
     */
    public int getChosenBomb() {
        return chosenBomb;
    }

    /**
     * @param chosenBomb the chosenBomb to set
     */
    public void setChosenBomb(int chosenBomb) 
    {
        this.chosenBomb = chosenBomb;
    }
    
    
}
