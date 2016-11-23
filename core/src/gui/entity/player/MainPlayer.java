/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import client.SendCommand;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Constants;
import gui.AnimEffects;
import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import gui.entity.item.Teleport;
import gui.map.MapCellCoordinates;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class MainPlayer extends Player
{
    
    //Variables DO NOT CHANGE
    private String lastMovementKeyPressed = "UP";
    private boolean sendStopOnce = true;
    private String sendMoveOnce = "";
    private float godModeTimer = 0;
    private float sendStopTimer = 0;
    private boolean godMode = false;
    private int playerId = 0;
    private int chosenBomb = 1;
    private float coinTimer = 0;
    
    //Objects
    private AnimEffects animEffects = new AnimEffects();
    
    //Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    // Bomb prices
    private int[] bombPrices = {Constants.BOMB1, Constants.BOMB2, Constants.BOMB3, Constants.BOMB4, Constants.BOMB5, Constants.BOMB6, Constants.BOMB7, Constants.BOMB8, Constants.BOMB9};
    

    // Player settings CAN BE CHANGED
    private float sendStopTime = 2f; // How often, if the player doesnt move, his position gets send, in seconds.
    private int life = Constants.DEFAULTLIFE;
    private float godModeDuration = Constants.GODMODEDURATION; // How long the player is invulnerable after beeing hit by a bomb
    private int coins = Constants.STARTCOINS;
    private int maxBombPlacing = Constants.DEFAULTBOMBPLACE;
    private int bombRange = Constants.DEFAULTBOMBRANGE;
    private float maxZoomOut = Constants.DEFAULTMAXZOOMOUT;
    private float maxZoomIn = Constants.DEFAULTMAXZOOMIN;
    
    //Constructor
    public MainPlayer(ThinGridCoordinates pos, ThinGridCoordinates direction, int playerId, OrthographicCamera camera, MapLoader map, EntityManager entityManager) 
    {
        super(pos, direction, map, entityManager, camera);

        //Set camera position to player
        camera.position.set(pos.getX(), pos.getY(), 0);
        
        //Object setter
        this.playerId = playerId;
        
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


    /**
     * Draws the player to screen
     * @param sb 
     */
    @Override
    public void render()
    {
        renderObject.setProjectionMatrix(camera.combined);
        renderObject.begin();
            
            // Gives out every couple of seconds some coins to the player
            coinBonus();
        
            //Adding direction to position
            pos.add(this.direction);
            
            //Set the camera to follow the player
            cameraFollowPlayer(pos);
            
            //Move player actions
            inputMovePlayer();
            
            //Special actions
            inputDoPlayer();

            //Hit detection
            hitByBomb();
        renderObject.end();
    }
    
    public void coinBonus()
    {
        if(this.coinTimer >= Constants.COINBONUSTIMER)
        {
            coins += Constants.COINBONUS;
            SendCommand.setPlayerCoins(playerId, coins);
            coinTimer = 0;
        }else
        {
            coinTimer += Constants.DELTATIME;
        }
    }
    
    /**
     * Execute on player death
     */
    public void onDeath()
    {
        entityManager.getItemManager().spawnTombstone(new MapCellCoordinates(pos), coins, playerId);
        
        SendCommand.playerDied(playerId, new MapCellCoordinates(pos.getX(), pos.getY()));
    }
    

    /**
     * Player gets hit by bomb
     */
    Thread flashThread;
    long id = -1;
    public void hitByBomb() 
    {  
        //If player touches explosion
        if(touchesDeadlyBlock() && godMode == false)
        {
            
            //Reduce player life
            life -= 1;
            
            //Set invulnerability to true
            godMode = true;
            
            //Send playerLife command to server
            SendCommand.setPlayerLife(playerId, life);
            
            //Execute hit sound
            if(id == -1)
            {
                id = AudioManager.hit.play();
                AudioManager.hit.setVolume(id, Constants.SOUNDVOLUME);
            }
               
            //Debug
            if(Constants.CLIENTDEBUG)
            {
                System.out.println("Invulnerability activated");
            }

            //Lets the player flash and saves the thread object to be able to stop it manually
            flashThread = animEffects.flashing(godModeDuration, 3, renderObject);
        }
        
        //Timer for godmode length after hit
        if(godModeTimer < godModeDuration && godMode == true)
        {
            godModeTimer += Constants.DELTATIME;
            
        }else if(godMode == true)
        {
            godMode = false;
            godModeTimer = 0;
            id = -1;
            
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

    
    /**
     * Moves the player if keyboard input is received.
     */
    private void inputMovePlayer()
    {
        /*------------------WALKING LEFT------------------*/
        if((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)))
        {
            if(!collidesLeft() && !collidesLeftBomb())
            {
                //Smoothly changes the direction
                goLeft();

                //Draw the player with animation
                renderObject.draw(animEffects.getFrame(walkAnimLeft), pos.getX(), pos.getY());

                //Block of code that gets executed just once upon button press
                if(sendMoveOnce.equals("LEFT") == false)
                {
                    //Send current player position to reduce lag
                    SendCommand.stopMoving(playerId, pos);
                    
                    //Saves position so that the static image looks in the correct direction if player stands still
                    lastMovementKeyPressed = "LEFT";
                    
                    //Ensures that stop command gets send only once
                    sendStopOnce = true;

                    //Send data to server
                    SendCommand.movePlayer(playerId, "LEFT");
                }

                //Ensures that walk command gets send only once
                sendMoveOnce = "LEFT";
            }else
            {
                //Ensures that stop command gets send only once
                sendStopOnce = true;
                
                //Sets moving direction to 0
                stopMoving();
                
                //Draw the player with animation
                renderObject.draw(animEffects.getFrame(walkAnimLeft), pos.getX(), pos.getY());
            }
             
        /*------------------WALKING RIGHT------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)))
        {
            if(!collidesRight() && !collidesRightBomb())
            {
                //Smoothly changes the direction
                goRight();
                
                //Draw the player with animation
                renderObject.draw(animEffects.getFrame(walkAnimRight), pos.getX(), pos.getY());
                
                //Block of code that gets executed just once upon button press
                if(sendMoveOnce.equals("RIGHT") == false)
                {
                    //Send current player position to reduce lag
                    SendCommand.stopMoving(playerId, pos);
                    
                    //Saves position so that the static image looks in the correct direction if player stands still
                    lastMovementKeyPressed = "RIGHT";
                    
                    //Ensures that stop command gets send only once
                    sendStopOnce = true;
                    
                    //Send walk command to server
                    SendCommand.movePlayer(playerId, "RIGHT");
                }
                
                //Ensures that walk command gets send only once
                sendMoveOnce = "RIGHT";
            }else
            {
                //Ensures that stop command gets send only once
                sendStopOnce = true;
                
                //Sets moving direction to 0
                stopMoving();
                
                //Draw the player with animation
                renderObject.draw(animEffects.getFrame(walkAnimRight), pos.getX(), pos.getY());
            }
            
        /*------------------WALKING UP------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)))
        {
            if(!collidesTop() && !collidesTopBomb())
            {
                //Smoothly changes the direction
                goUp();

                //Draw the player with animation
                renderObject.draw(animEffects.getFrame(walkAnimUp), pos.getX(), pos.getY());

                //Block of code that gets executed just once upon button press
                if(sendMoveOnce.equals("UP") == false)
                {
                    //Send current player position to reduce lag
                    SendCommand.stopMoving(playerId, pos);
                    
                    //Saves position so that the static image looks in the correct direction if player stands still
                    lastMovementKeyPressed = "UP";
                    
                    //Ensures that stop command gets send only once
                    sendStopOnce = true;

                    //Send walk command to server
                    SendCommand.movePlayer(playerId, "UP");
                }
                
                //Ensures that walk command gets send only once
                sendMoveOnce = "UP";
            }else
            {
                //Ensures that stop command gets send only once
                sendStopOnce = true;
                
                //Sets moving direction to 0
                stopMoving();
                
                //Draw the player with animation
                renderObject.draw(animEffects.getFrame(walkAnimUp), pos.getX(), pos.getY());
            }
            
        /*------------------WALKING DOWN------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)))
        {
            if(!collidesBottom() && !collidesBottomBomb())
            {
                goDown();
                
                renderObject.draw(animEffects.getFrame(walkAnimDown), pos.getX(), pos.getY());
                
                //Block of code that gets executed just once upon button press
                if(sendMoveOnce.equals("DOWN") == false)
                {
                    //Send current player position to reduce lag
                    SendCommand.stopMoving(playerId, pos);
                    
                    //Saves position so that the static image looks in the correct direction if player stands still
                    lastMovementKeyPressed = "DOWN";
                    
                    //Ensures that stop command gets send only once
                    sendStopOnce = true;
                    
                    //Send walk command to server
                    SendCommand.movePlayer(playerId, "DOWN");
                }
                
                //Ensures that walk command gets send only once
                sendMoveOnce = "DOWN";
            }else
            {
                //Ensures that stop command gets send only once
                sendStopOnce = true;
                
                //Sets moving direction to 0
                stopMoving();
                
                //Draw the player with animation
                renderObject.draw(animEffects.getFrame(walkAnimDown), pos.getX(), pos.getY());
            }
            
        /*------------------NO MOVEMENT------------------*/    
        }else
        {
            if(sendStopOnce == true)
            {
                //Send stop command to server
                SendCommand.stopMoving(playerId, pos);
                
                //Ensures that stop command gets send only once
                sendStopOnce = false;
                
                //Reset walk command to be able to walk in every direction after stopping player
                sendMoveOnce = "";
            }

            if(sendStopTimer >= sendStopTime)
            {
                //Send command again
                sendStopOnce = true;
                
                //Reset timer
                sendStopTimer = 0;
            }else
            {
                sendStopTimer += Constants.DELTATIME;
            }
            
            //Sets moving direction to 0
            stopMoving();
            
            //Draws the player if he stands still
            switch(lastMovementKeyPressed)
            {
                case "LEFT":
                    renderObject.draw(walkAnimLeft.getKeyFrame(0), pos.getX(), pos.getY());
                    break;

                case "RIGHT":
                    renderObject.draw(walkAnimRight.getKeyFrame(0), pos.getX(), pos.getY());
                break;

                case "UP":
                    renderObject.draw(walkAnimUp.getKeyFrame(0), pos.getX(), pos.getY());
                    break;

                case "DOWN":
                    renderObject.draw(walkAnimDown.getKeyFrame(0), pos.getX(), pos.getY());
                    break;
                
                default:
                    System.err.println("ERROR: In MainPlayer inputMovePlayer() wrong value in lastMovementKeyPressed: " + lastMovementKeyPressed);
            }
        }
    }
    
    
    /**
     * Action the player can make like placing a bomb
     */
    private void inputDoPlayer()
    {        
        /*------------------PLACE BOMB------------------*/
        if (Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            ThinGridCoordinates feetPosition = new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2, pos.getY() + Constants.PLAYERHEIGHT / 3);
            String bombType;
            
            switch (chosenBomb)
            {
                case(1):

                    bombType = "default";
                    
                    //Checks if there is already a bomb
                    if(!map.isBombPlaced(new MapCellCoordinates(feetPosition)) && maxBombPlacing > entityManager.getBombManager().getBombArrayMain().size && coins >= Constants.BOMB1)
                    {
                        coins -= Constants.BOMB1;
                            
                        //Send bomb command to server
                        SendCommand.placeBomb(playerId, feetPosition, bombType);
                
                        //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                        entityManager.getBombManager().spawnNormalBomb(feetPosition, playerId, bombRange);
                    }
                    break;
                    
                case(2):
                    
                    bombType = "dynamite";
                    
                    //Checks if there is already a bomb
                    if(!map.isBombPlaced(new MapCellCoordinates(feetPosition)) && maxBombPlacing > entityManager.getBombManager().getBombArrayMain().size && coins>= Constants.BOMB2)
                    {
                        coins -= Constants.BOMB2;
                        
                        //Send bomb command to server
                        SendCommand.placeBomb(playerId, feetPosition, bombType);
                         
                        //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                        entityManager.getBombManager().spawnDynamite(feetPosition, playerId, bombRange);
                    }
                    break;
                    
                case(3):
                    
                    bombType = "infinity";
                    
                    //Checks if there is already a bomb
                    if(!map.isBombPlaced(new MapCellCoordinates(feetPosition)) && maxBombPlacing > entityManager.getBombManager().getBombArrayMain().size && coins>= Constants.BOMB3)
                    {
                        coins -= Constants.BOMB3;
                        
                        //Send bomb command to server
                        SendCommand.placeBomb(playerId, feetPosition, bombType);
                         
                        //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                        entityManager.getBombManager().spawnInfinity(feetPosition, playerId, bombRange, Constants.INFINITYREPRODUCTIONCHANCE);
                    }
                    break;
                        
                case(4):
                    
                    bombType = "X3";
            
                    //Checks if there is already a bomb
                    if(!map.isBombPlaced(new MapCellCoordinates(feetPosition)) && maxBombPlacing > entityManager.getBombManager().getBombArrayMain().size && coins>= Constants.BOMB4)
                    {
                        coins -= Constants.BOMB4;
                        
                        //Send bomb command to server
                        SendCommand.placeBomb(playerId, feetPosition, bombType);
                        
                        //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                        entityManager.getBombManager().spawnX3(feetPosition, playerId, bombRange, 1);
                    }
                    break;
                    
                case(9):
                    
                    bombType = "Teleport";
            
                    //Checks if there is already a bomb
                    if(!map.isBombPlaced(new MapCellCoordinates(feetPosition)) && maxBombPlacing > entityManager.getBombManager().getBombArrayMain().size && coins>= Constants.BOMB9)
                    {
                        coins -= Constants.BOMB9;
                        
                        Teleport item = new Teleport(new MapCellCoordinates(feetPosition),map,entityManager);
                        item.itemEffect();
                    }
                    break;
            }
 
        }
        
        /*------------------CHOOSE BOMB------------------*/
        if (Gdx.input.isKeyJustPressed(Keys.NUM_1) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_1))
        {
            chosenBomb = 1;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.NUM_2) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_2))
        {
            chosenBomb = 2;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.NUM_3) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_3))
        {
            chosenBomb = 3;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.NUM_4) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_4))
        {
            chosenBomb = 4;
        }
         
        if (Gdx.input.isKeyJustPressed(Keys.NUM_5) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_5))
        {
            chosenBomb = 5;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.NUM_6) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_6))
        {
            chosenBomb = 6;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.NUM_7) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_7))
        {
            chosenBomb = 7;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.NUM_8) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_8))
        {
            chosenBomb = 8;
        }
        
        if (Gdx.input.isKeyJustPressed(Keys.NUM_9) || Gdx.input.isKeyJustPressed(Keys.NUMPAD_9))
        {
            chosenBomb = 9;
        }
        
        /*------------------ZOOM OUT OF GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.O) || Gdx.input.isKeyPressed(Keys.MINUS))
        {
            if(camera.zoom < maxZoomOut)
            {
                camera.zoom += 0.02;
                camera.position.set(pos.getX(), pos.getY(), 0);
            }
        }

        /*------------------ZOOM INTO GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.I) || Gdx.input.isKeyPressed(Keys.PLUS))
        {
            if(camera.zoom > maxZoomIn)
            {
                camera.zoom -= 0.02;
                camera.position.set(pos.getX(), pos.getY(), 0);
            }
        }
        
        // Scroll wheel with anonymous event listener
        Gdx.input.setInputProcessor(new InputAdapter () 
        {
            @Override
            public boolean scrolled(int i) 
            {
                if(i == 1)
                {
                    if(chosenBomb < 9)
                    {
                        chosenBomb++;
                    }
                }else if(i == -1)
                {
                    if(chosenBomb > 1)
                    {
                        chosenBomb--;
                    }
                }

                return false;
            }
        });
    }

    /*------------------ GETTER & SETTER ------------------*/  
    public int getBombPrice(int bombNumber)
    {
        return bombPrices[bombNumber -1];
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
        return this.chosenBomb;
    }
}
