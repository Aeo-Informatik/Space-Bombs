/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import gui.entity.bombs.Bomb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import static com.gdx.bomberman.Main.client;
import static com.gdx.bomberman.Main.game;
import gui.AnimEffects;
import gui.AudioManager;
import gui.TextureManager;
import gui.camera.OrthoCamera;
import gui.map.MapManager;
import gui.screen.MenuScreen;
import java.io.IOException;

/**
 *
 * @author qubasa
 */
public class MainPlayer extends Entity
{
    
    //General Variables DO NOT CHANGE
    private String lastMovementKeyPressed = "UP";
    private boolean sendStopOnce = true;
    private String sendMoveOnce = "";
    private float godModeTimer = 0;
    private boolean godmode = false;
    private int playerId = 0;
    
    //General objects
    private OrthoCamera camera;
    private Array <Bomb> bombArray;
    private AnimEffects animEffects = new AnimEffects();
    
    //Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    //Player settings CAN BE CHANGED
    private int life = 3;
    private float godModeDuration = 2f; // How long the player is invulnerable after beeing hit by a bomb
    private int coins = 0;
    private int maxBombPlacing = 2;
    private int BombRange = 2;
    private float speed = 1.0f;
    
    //Constructor
    public MainPlayer(Vector2 pos, Vector2 direction, int playerId, OrthoCamera camera, MapManager map, Array<Bomb> bombArray, EntityManager entityManager) 
    {
        super(pos, direction, map, entityManager);

        //Set camera position to players position
        camera.setPosition(pos.x, pos.y);

        this.camera = camera;
        this.playerId = playerId;
        this.bombArray = bombArray;
        
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
    public void render(SpriteBatch renderObject)
    {
        //Adding direction to position
        pos.add(this.direction);
        
        //Keyboard interception
        inputMovePlayer(renderObject);
        inputDoPlayer(renderObject);
        
        hitByBomb(renderObject);
    }
    
    
    /**
     * Execute on player death
     */
    public void onDeath()
    {
        System.out.println("YOU DIED!");
        entityManager.spawnTombstone((int)(pos.x / Constants.MAPTEXTUREWIDTH),(int)(pos.y / Constants.MAPTEXTUREHEIGHT));
    }
    
    
    /**
     * Player gets hit by bomb
     */
    Thread flashThread;
    long id = -1;
    public void hitByBomb(SpriteBatch renderObject) 
    {  
        //If player touches explosion
        if(touchesDeadlyBlock() && godmode == false)
        {
            life -= 1;
            godmode = true;
            client.sendData("enemyPlayerLife|" + Constants.PLAYERID + "|" + life + "|*");
            
            System.out.println("Life has been reduced to: " + life);
          
            if(id == -1)
            {
                id = AudioManager.hit.play();
                AudioManager.hit.setVolume(id, Constants.SOUNDVOLUME);
            }
                
            if(Constants.CLIENTDEBUG)
            {
                System.out.println("Invulnerability activated");
            }

            //Lets the player flash and saves the thread object to be able to stop it manually
            flashThread = animEffects.flashing(godModeDuration, 3, renderObject);
        }
        
        //Timer for godmode length after hit
        if(godModeTimer < godModeDuration && godmode == true)
        {
            godModeTimer += Constants.DELTATIME;
            //System.out.println("Increasing timer by: " + godModeTimer);
        }else if(godmode == true)
        {
            godmode = false;
            godModeTimer = 0;
            
            //Stops the blinkAnimation thread, it is more precise than using only the godModeDuration
            flashThread.interrupt();
            
            if(Constants.CLIENTDEBUG)
            {
                System.out.println("Invulnerability deactivated");
            }
        }
    }

    
    /**
     * Moves the player if keyboard input is received.
     */
    
    
    private void inputMovePlayer(SpriteBatch renderObject)
    {
        String moveCommand;
        float cameraSpeed = 2.51f * speed;
        
        /*------------------WALKING LEFT------------------*/
        if((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)))
        {
            if(!collidesLeft() && !collidesLeftBomb())
            {
                //Set the speed the texture moves in x and y axis
                //This will be added to the position every render cycle
                setDirection(-150 * speed, 0);

                //Move camera x,y
                camera.translate( -1 * cameraSpeed,0);

                //Draw the walking animation
                renderObject.draw(animEffects.getFrame(walkAnimLeft), pos.x, pos.y);

                //Block of code that gets executed just once upon button press
                if(sendMoveOnce.equals("LEFT") == false)
                {
                    client.sendData("stopEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|" + pos.x + "|" + pos.y + "|*");
                    
                    //Saves position so that the static image looks in the right direction if player doesnt move
                    lastMovementKeyPressed = "LEFT";
                    
                    //Ensures that stop command gets send only once
                    sendStopOnce = true;

                    //General: moveEnemyPlayer|playerId|direction|target
                    moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|LEFT|*";

                    //Send data to server
                    client.sendData(moveCommand);
                }

                //Ensures that walk command gets send only once
                sendMoveOnce = "LEFT";
            }else
            {
                //Stop player
                sendStopOnce = true;
                setDirection(0,0);
                renderObject.draw(animEffects.getFrame(walkAnimLeft), pos.x, pos.y);
                camera.translate(0, 0);
            }
             
        /*------------------WALKING RIGHT------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)))
        {
            if(!collidesRight() && !collidesRightBomb())
            {
                setDirection(150* speed, 0);
                
                camera.translate(cameraSpeed,0);
                
                renderObject.draw(animEffects.getFrame(walkAnimRight), pos.x, pos.y);
                
                if(sendMoveOnce.equals("RIGHT") == false)
                {
                    client.sendData("stopEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|" + pos.x + "|" + pos.y + "|*");
                    
                    lastMovementKeyPressed = "RIGHT";
                    sendStopOnce = true;
                    moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|RIGHT|*";
                    client.sendData(moveCommand);
                }
                sendMoveOnce = "RIGHT";
            }else
            {
                sendStopOnce = true;
                setDirection(0,0);
                renderObject.draw(animEffects.getFrame(walkAnimRight), pos.x, pos.y);
                camera.translate(0, 0);
            }
            
        /*------------------WALKING UP------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)))
        {
            if(!collidesTop() && !collidesTopBomb())
            {
                setDirection(0, 150* speed);

                camera.translate(0, cameraSpeed);

                renderObject.draw(animEffects.getFrame(walkAnimUp), pos.x, pos.y);

                if(sendMoveOnce.equals("UP") == false)
                {
                    client.sendData("stopEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|" + pos.x + "|" + pos.y + "|*");
                    
                    lastMovementKeyPressed = "UP";
                    sendStopOnce = true;
                    moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|UP|*";
                    client.sendData(moveCommand);
                }
                sendMoveOnce = "UP";
            }else
            {
                sendStopOnce = true;
                setDirection(0,0);
                renderObject.draw(animEffects.getFrame(walkAnimUp), pos.x, pos.y);
                camera.translate(0, 0);
            }
            
        /*------------------WALKING DOWN------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)))
        {
            if(!collidesBottom() && !collidesBottomBomb())
            {
                setDirection(0, -150* speed);
                camera.translate(0, -1 * cameraSpeed);
                
                renderObject.draw(animEffects.getFrame(walkAnimDown), pos.x, pos.y);
                
                if(sendMoveOnce.equals("DOWN") == false)
                {
                    client.sendData("stopEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|" + pos.x + "|" + pos.y + "|*");
                    
                    lastMovementKeyPressed = "DOWN";
                    sendStopOnce = true;
                    moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|DOWN|*";
                    client.sendData(moveCommand);
                }
                sendMoveOnce = "DOWN";
            }else
            {
                sendStopOnce = true;
                setDirection(0,0);
                renderObject.draw(animEffects.getFrame(walkAnimDown), pos.x, pos.y);
                camera.translate(0, 0);
            }
            
        /*------------------NO MOVEMENT------------------*/    
        }else
        {
            if(sendStopOnce)
            {
                moveCommand = "stopEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|" + pos.x + "|" + pos.y + "|*";

                //Send data to server
                client.sendData(moveCommand);
                
                sendStopOnce = false;
                sendMoveOnce = "";
            }

            //Sets the texture to no movement
            setDirection(0, 0);
            
            //Draws the player if he stands still
            switch(lastMovementKeyPressed)
            {
                case "LEFT":
                    renderObject.draw(walkAnimLeft.getKeyFrame(0), pos.x, pos.y);
                    break;

                case "RIGHT":
                    renderObject.draw(walkAnimRight.getKeyFrame(0), pos.x, pos.y);
                break;

                case "UP":
                    renderObject.draw(walkAnimUp.getKeyFrame(0), pos.x, pos.y);
                    break;

                case "DOWN":
                    renderObject.draw(walkAnimDown.getKeyFrame(0), pos.x, pos.y);
                    break;
                
                default:
                    System.err.println("ERROR: In MainPlayer inputMovePlayer() wrong value in lastMovementKeyPressed: " + lastMovementKeyPressed);
            }
        }
    }
    
    
    /**
     * Action the player can make like placing a bomb
     */
    private void inputDoPlayer(SpriteBatch renderObject)
    {        
        /*------------------PLACE BOMB------------------*/
        if (Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            float x = pos.x + Constants.PLAYERWIDTH / 2;
            float y = pos.y + Constants.PLAYERHEIGHT / 3;
            String bombType = "default";
            
            //Checks if there is already a bomb
            if(!map.isBombPlaced(x, y) && maxBombPlacing > bombArray.size)
            {
                client.sendData("placeEnemyBomb|" + Float.toString(x) + "|" + Float.toString(y) + "|" + Integer.toString(Constants.PLAYERID) + "|" + bombType + "|*");
                
                //Create Bomb Object (Add always a new Vector2 object or else it will constantly update the position to the player position)
                Bomb bomb = new Bomb(new Vector2(pos.x + Constants.PLAYERWIDTH / 2 , pos.y + Constants.PLAYERHEIGHT / 3), new Vector2(pos.x, pos.y), map, playerId, BombRange, entityManager); 
                bombArray.add(bomb);
            }
        }
        
        /*------------------ZOOM OUT GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.O))
        {
            if(camera.zoom < 2.0)
            {
                camera.zoom += 0.02;
                camera.setPosition(pos.x, pos.y);
            }
        }

        /*------------------ZOOM INTO GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.I))
        {
            if(camera.zoom > 0.5)
            {
                camera.zoom -= 0.02;
                camera.setPosition(pos.x, pos.y);
            }
        }
        
        /*------------------CAMERA CENTERS PLAYER------------------*/
        if (Gdx.input.isKeyPressed(Keys.ENTER))
        {
            camera.setPosition(pos.x, pos.y);
        }
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
        {
            try 
            {
                client.closeConnection();
                
            } catch (IOException ex) 
            {
                System.err.println("Unexpected exception in ESC Quit game in mainplayer on closing connetion with server.");
            }
            game.setScreen(new MenuScreen());
        }
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        if(Gdx.input.isKeyPressed(Keys.F12))
        {
            if(Gdx.graphics.isFullscreen())
            {
                Gdx.graphics.setWindowedMode(Constants.SCREENWIDTH, Constants.SCREENHEIGHT);
            }else
            {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }
    }

    /*------------------ GETTER & SETTER ------------------*/    
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

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getMaxBombPlacing() {
        return maxBombPlacing;
    }

    public void setMaxBombPlacing(int maxBombPlacing) {
        this.maxBombPlacing = maxBombPlacing;
    }

    public int getBombRange() {
        return BombRange;
    }

    public void setBombRange(int BombRange) {
        this.BombRange = BombRange;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


    
    
}
