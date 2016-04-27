/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Main;
import gui.Constants;
import gui.TextureManager;
import gui.camera.OrthoCamera;
import gui.map.MapManager;
import networkClient.Client;

/**
 *
 * @author qubasa
 */
public class MainPlayer extends Entity
{
    
    //General Variables
    private float stateTime;
    private String lastMovementKeyPressed = "UP";
    private Client client;
    private SpriteBatch sb;
    private boolean sendStopOnce = true;
    private String sendMoveOnce = "";
    private OrthoCamera camera;
    private int player;
    private Array <Bomb> bombArray;
    
    //Collision detection
    private MapManager map;
    private TiledMapTileLayer blockLayer;
    private TiledMapTileLayer bombLayer;
    
    //Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    //Player values
    private int life = 1;
    private boolean godmode = false;
    private int coins = 0;
    
    
    public MainPlayer(Vector2 pos, Vector2 direction, int player, OrthoCamera camera, MapManager map, Array<Bomb> bombArray) throws Exception 
    {
        super(null, pos, direction);
        this.player = player;
        this.bombArray = bombArray;
        
        //Set camera position to players position
        camera.setPosition(pos.x, pos.y);

        this.client = Main.client;
        this.camera = camera;
        this.sb = Main.sb;
        this.map = map;
        this.blockLayer = map.getBlockLayer();
        this.bombLayer = map.getBombLayer();
        
        //Get apropriate player texture based on player id
        switch(player)
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
     * Update is the same as render only that it doesn't have the SpriteBatch Object.
     * It is used for game logic updates.
     */
    @Override
    public void update() 
    {  
        //LEFT
        if(direction.x < 0)
        {
        }
            
        //RIGHT
        if(direction.x > 0)
        {
        }

        //DOWN
        if(direction.y < 0)
        {
        }

        //UP
        if(direction.y > 0)
        {
        }
    }
    
    
    /**
     * Draws the player to screen
     * @param sb 
     */
    @Override
    public void render(SpriteBatch sb)
    {
        //Adding direction to position
        pos.add(this.direction);
        
        //Keyboard interception
        inputMovePlayer();
        inputDoPlayer();
        
        if(touchesDeadlyBlock())
        {
            System.out.println("Touched deadly tile!");
            life -= 1;
        }
        
    }
    
    
    private boolean touchesDeadlyBlock()
    {
        float margin = 3f;
        
        //Checks from the walking right texture a collision on the down left, down right
        if(isCellDeadly(pos.x + margin, pos.y) || isCellDeadly(pos.x + walkAnimRight.getKeyFrame(0).getRegionWidth() - margin, pos.y) )
        {
            return true;
        }
        
        return false;
    }
    
    
    private boolean isCellDeadly(float x, float y)
    {
        Cell cell = bombLayer.getCell((int) (x / bombLayer.getTileWidth()), (int) (y / bombLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("deadly");
    }
    
    /**
     * Checks if the block on given entity coordinates is blocked.
     * @param x
     * @param y
     * @return boolean
     */
    private boolean isCellBlocked(float x, float y)
    {
        Cell cell = blockLayer.getCell((int) (x / blockLayer.getTileWidth()), (int) (y / blockLayer.getTileHeight()));
        //System.out.println("X: " + (int) (x / blockLayer.getTileWidth()) + " Y: " + (int) (y / blockLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("blocked");
    }
    
    
    private boolean collidesLeft()
    {
        if(isCellBlocked(pos.x - 2, pos.y))
            return true;

        return false;
    }
    
    private boolean collidesRight()
    {
        if(isCellBlocked(pos.x + walkAnimRight.getKeyFrame(0).getRegionWidth() + 2, pos.y))
            return true;

        return false;
    }
    
    private boolean collidesTop()
    {
        if(isCellBlocked(pos.x + 3, pos.y + walkAnimRight.getKeyFrame(0).getRegionHeight() / 2 + 3) || isCellBlocked(pos.x  + walkAnimRight.getKeyFrame(0).getRegionWidth() - 3, pos.y + walkAnimRight.getKeyFrame(0).getRegionHeight() / 2 + 3))
            return true;

        return false;
    }
    
    private boolean collidesBottom()
    {
        //Checks at the players feet on the left if there is a block and on the right
        if(isCellBlocked(pos.x + 3, pos.y - 3) || isCellBlocked(pos.x  + walkAnimRight.getKeyFrame(0).getRegionWidth() -3, pos.y - 3))
            return true;

        return false;
    }
    
    /**
     * Moves the player if keyboard input is received.
     */
    private void inputMovePlayer()
    {
        String moveCommand = "";
        float cameraSpeed = 2.51f;
        
        /*------------------WALKING LEFT------------------*/
        if((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT)))
        {
            if(!collidesLeft())
            {
                //Set the speed the texture moves in x and y axis
                //This will be added to the position every render cycle
                setDirection(-150, 0);

                //Move camera x,y
                camera.translate( -1 * cameraSpeed,0);

                //Draw the walking animation
                sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);

                //Block of code that gets executed just once upon button press
                if(sendMoveOnce.equals("LEFT") == false)
                {
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
                sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);
                camera.translate(0, 0);
            }
             
        /*------------------WALKING RIGHT------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)))
        {
            if(!collidesRight())
            {
                setDirection(150, 0);
                
                camera.translate(cameraSpeed,0);
                
                sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
                
                if(sendMoveOnce.equals("RIGHT") == false)
                {
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
                sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
                camera.translate(0, 0);
            }
            
        /*------------------WALKING UP------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP)))
        {
            if(!collidesTop())
            {
                setDirection(0, 150);

                camera.translate(0, cameraSpeed);

                sb.draw(getFrame(walkAnimUp), pos.x, pos.y);

                if(sendMoveOnce.equals("UP") == false)
                {
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
                sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
                camera.translate(0, 0);
            }
            
        /*------------------WALKING DOWN------------------*/
        }else if((Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN)))
        {
            if(!collidesBottom())
            {
                setDirection(0, -150);
                camera.translate(0, -1 * cameraSpeed);
                sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
                if(sendMoveOnce.equals("DOWN") == false)
                {
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
                sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
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
                    sb.draw(walkAnimLeft.getKeyFrame(0), pos.x, pos.y);
                    break;

                case "RIGHT":
                    sb.draw(walkAnimRight.getKeyFrame(0), pos.x, pos.y);
                break;

                case "UP":
                    sb.draw(walkAnimUp.getKeyFrame(0), pos.x, pos.y);
                    break;

                case "DOWN":
                    sb.draw(walkAnimDown.getKeyFrame(0), pos.x, pos.y);
                    break;
                
                default:
                    System.err.println("ERROR: In MainPlayer inputMovePlayer() wrong value in lastMovementKeyPressed: " + lastMovementKeyPressed);
            }
        }

    }
    
    
    /**
     * Action the player can make like placing a bomb
     * @param sb 
     */
    private void inputDoPlayer()
    {
        /*------------------PLACE BOMB------------------*/
        if (Gdx.input.isKeyJustPressed(Keys.SPACE))
        {
            
            //Create Bomb Object
            Bomb bomb = new Bomb(pos.x, pos.y, direction, map, player); 
            bombArray.add(bomb); 
            
        }
        
        /*------------------ZOOM OUT GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.Z))
        {
            if(camera.zoom < 2.0)
            {
                camera.zoom += 0.02;
                camera.setPosition(pos.x, pos.y);
            }
            
        }

        /*------------------ZOOM INTO GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.U))
        {
            if(camera.zoom > 0.5)
            {
                camera.zoom -= 0.02;
                camera.setPosition(pos.x, pos.y);
            }
        }
        
        /*------------------CAMERA CENTERS PLAYER------------------*/
        if (Gdx.input.isKeyPressed(Keys.P))
        {
            camera.setPosition(pos.x, pos.y);
        }
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
        {
            System.out.println("Quit game with Keyboard [ESC]");
            Gdx.app.exit();
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
    

    /**
     * Gets the frame out of the animation
     * @param animation
     * @return 
     */
    private TextureRegion getFrame(Animation animation)
    {
        /* Adds the time elapsed since the last render to the stateTime.*/
        this.stateTime += Constants.DELTATIME; 
        
        /*
        Obtains the current frame. This is given by the animation for the current time. 
        The second variable is the looping. 
        Passing in true, will tell the animation to restart after it reaches the last frame.
        */
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        
        return currentFrame;
    }
 
    
    /*------------------ GETTER & SETTER ------------------*/
    @Override
    public Vector2 getPosition()
    {
        return pos;
    }
    
    public Vector2 getDirection()
    {
        return direction;
    }
    
    public void setPosition(Vector2 pos)
    {
        this.pos = pos;
    }
    
    public int getLife()
    {
        return this.life;
    }
    
    public void setLife(int life)
    {
        this.life = life;
    }

}
