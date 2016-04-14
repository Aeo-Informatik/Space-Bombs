/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
    private OrthoCamera camera;
    private MapManager map;
    
    //Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    //Player Static image when he stands still
    private final TextureRegion staticUp;
    private final TextureRegion staticDown;
    private final TextureRegion staticRight;
    private final TextureRegion staticLeft;

    
    public MainPlayer(Vector2 pos, Vector2 direction, int player, OrthoCamera camera, MapManager map) throws Exception 
    {
        super(null, pos, direction);
        
        //Set camera position to players position
        camera.setPosition(pos.x, pos.y);
        try
        {
            this.client = Main.client;
            this.camera = camera;

            this.sb = Main.sb;
            this.map = map;
            

        }catch(Exception e)
        {
           throw e; 
        }
        
            switch(player)
            {
                case 1:
                    this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                    this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                    this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                    this.walkAnimRight = TextureManager.p1WalkingRightAnim;
                    
                    this.staticUp = TextureManager.p1StaticUp;
                    this.staticDown = TextureManager.p1StaticDown;
                    this.staticLeft = TextureManager.p1StaticLeft;
                    this.staticRight = TextureManager.p1StaticRight;
                    break;

                case 2:
                    this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                    this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                    this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                    this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                    
                    this.staticUp = TextureManager.p2StaticUp;
                    this.staticDown = TextureManager.p2StaticDown;
                    this.staticLeft = TextureManager.p2StaticLeft;
                    this.staticRight = TextureManager.p2StaticRight;
                    break;

                case 3:
                    this.walkAnimUp = TextureManager.p3WalkingUpAnim;
                    this.walkAnimDown = TextureManager.p3WalkingDownAnim;
                    this.walkAnimLeft = TextureManager.p3WalkingLeftAnim;
                    this.walkAnimRight = TextureManager.p3WalkingRightAnim;
                    
                    this.staticUp = TextureManager.p3StaticUp;
                    this.staticDown = TextureManager.p3StaticDown;
                    this.staticLeft = TextureManager.p3StaticLeft;
                    this.staticRight = TextureManager.p3StaticRight;
                    break;

                case 4:
                    this.walkAnimUp = TextureManager.p4WalkingUpAnim;
                    this.walkAnimDown = TextureManager.p4WalkingDownAnim;
                    this.walkAnimLeft = TextureManager.p4WalkingLeftAnim;
                    this.walkAnimRight = TextureManager.p4WalkingRightAnim;
                    
                    this.staticUp = TextureManager.p4StaticUp;
                    this.staticDown = TextureManager.p4StaticDown;
                    this.staticLeft = TextureManager.p4StaticLeft;
                    this.staticRight = TextureManager.p4StaticRight;
                    break;
                
                default:
                    System.err.println("ERROR: Wrong player number in MainPlayer. Using default p1");
                    this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                    this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                    this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                    this.walkAnimRight = TextureManager.p1WalkingRightAnim;
                    
                    this.staticUp = TextureManager.p1StaticUp;
                    this.staticDown = TextureManager.p1StaticDown;
                    this.staticLeft = TextureManager.p1StaticLeft;
                    this.staticRight = TextureManager.p1StaticRight;
            }
    }

    
    /**
     * Update is the same as render only that it doesn't have the SpriteBatch Object.
     * It is used for game logic updates.
     */
    @Override
    public void update() 
    {
        float oldY = pos.y;
        float oldX = pos.x;
        
        switch(getMovingDirection())
        {
            
            case "LEFT":
                break;
            
            case "RIGHT":
                break;
                
            case "UP":
                break;
                
            case "DOWN":
                break;
            
            case "NOMOVEMENT":
                break;
                
            default:
                System.err.println("ERROR in MainPlayer Update() wrong switch variable " + getMovingDirection());
        }
        
    }
    
    /**
     * Draws the player to screen
     * @param sb 
     */
    @Override
    public void render(SpriteBatch sb)
    {
        pos.add(this.direction);
        inputMovePlayer();   
        inputDoPlayer();
    }
    
    
    public String getMovingDirection()
    {
        if(direction.x < 0)
        {
            return "LEFT";

        }else if(direction.x > 0)
        {
            return "RIGHT";
            
        }else if(direction.y < 0)
        {
            return "DOWN";
            
        }else if(direction.y > 0)
        {
            return "UP";
        }
        
        return "NOMOVEMENT";
    }
    
    /**
     * Moves the player if keyboard input is received
     * @param sb 
     */
    private void inputMovePlayer()
    {
        String moveCommand = "";
        float cameraSpeed = 2.51f; // DO NOT CHANGE
        
        /*------------------WALKING LEFT------------------*/
        if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
        {
            //Set the speed the texture moves in x and y axis
            //this is the method inherited from Entity.java class
            setDirection(-150, 0);
            
            //Move camera x,y
            camera.translate( -1 * cameraSpeed,0);
            
            //Draw the walking animation
            sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);
            
            //Block of code that gets executed just once upon button press
            if(Gdx.input.isKeyJustPressed(Keys.A) || Gdx.input.isKeyJustPressed(Keys.LEFT))
            {
                lastMovementKeyPressed = "LEFT";
                sendStopOnce = true;
                
                //General: moveEnemyPlayer|playerId|direction|target
                moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|LEFT|*";

                //Send data to server
                client.sendData(moveCommand);
            }
           
        /*------------------WALKING RIGHT------------------*/
        }else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
        {
            setDirection(150, 0);
            
            camera.translate(cameraSpeed,0);
            
            //Draw the walking animation
            sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
            
            //Block of code that gets executed just once upon button press
            if(Gdx.input.isKeyJustPressed(Keys.D) || Gdx.input.isKeyJustPressed(Keys.RIGHT))
            {
                lastMovementKeyPressed = "RIGHT";
                sendStopOnce = true;
                
                moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|RIGHT|*";

                //Send data to server
                client.sendData(moveCommand);
            }
            
        /*------------------WALKING UP------------------*/
        }else if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP))
        {
            setDirection(0, 150);
            
            camera.translate(0, cameraSpeed);
            
            //Draw the walking animation
            sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
            
            //Block of code that gets executed just once upon button press
            if(Gdx.input.isKeyJustPressed(Keys.W) || Gdx.input.isKeyJustPressed(Keys.UP))
            {
                lastMovementKeyPressed = "UP";
                sendStopOnce = true;
                
                moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|UP|*";

                //Send data to server
                client.sendData(moveCommand);
            }
        
        /*------------------WALKING DOWN------------------*/
        }else if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN))
        {
            setDirection(0, -150);
            
            camera.translate(0, -1 * cameraSpeed);
            
            //Draw the walking animation
            sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
            
            //Block of code that gets executed just once upon button press
            if(Gdx.input.isKeyJustPressed(Keys.S) || Gdx.input.isKeyJustPressed(Keys.DOWN))
            {
                lastMovementKeyPressed = "DOWN";
                sendStopOnce = true;
                
                moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|DOWN|*";

                //Send data to server
                client.sendData(moveCommand);
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
            }
            
            //Sets the texture to no movement
            setDirection(0, 0);
            
            //Draws the player if he stands still
            switch(lastMovementKeyPressed)
            {
                case "LEFT":
                    sb.draw(staticLeft, pos.x, pos.y);
                    break;

                case "RIGHT":
                    sb.draw(staticRight, pos.x, pos.y);
                break;

                case "UP":
                    sb.draw(staticUp, pos.x, pos.y);
                    break;

                case "DOWN":
                    sb.draw(staticDown, pos.x, pos.y);
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
        if (Gdx.input.isKeyPressed(Keys.SPACE))
        {
            //Create Bomb Object
            Bomb bomb = new Bomb(super.getPosition(),direction,1); 
            
            //Draw bomb
            sb.draw(bomb.getB1StaticBurns(),pos.x,pos.y);
            
            //Block of code that gets executed just once upon button press
            if(Gdx.input.isKeyJustPressed(Keys.SPACE))
            {
                
            }
        }
        
        
        /*------------------ZOOM OUT GAME------------------*/
        if (Gdx.input.isKeyPressed(Keys.Z))
        {
            if(camera.zoom < 2.5)
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
    }
    
    
    /**
     * Gets the frame out of the animation
     * @param animation
     * @return 
     */
    private TextureRegion getFrame(Animation animation)
    {
        /* Adds the time elapsed since the last render to the stateTime.*/
        this.stateTime += Gdx.graphics.getDeltaTime(); 
        
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

}
