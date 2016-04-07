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
import com.badlogic.gdx.math.Vector2;
import com.gdx.bomberman.Main;
import gui.Constants;
import gui.TextureManager;
import java.util.ArrayList;
import networkClient.Client;

/**
 *
 * @author qubasa
 */
public class MainPlayer extends Entity{
    

    private float stateTime;
    private String lastMovementKeyPressed = "UP";
    private Client client;
    private ArrayList Bombs;
    
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

    
    public MainPlayer(Vector2 pos, Vector2 direction, int player) throws Exception 
    {
        super(null, pos, direction);
        
        try
        {
            this.client = Main.client;
            
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
                    this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                    this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                    this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                    this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                    
                    this.staticUp = TextureManager.p2StaticUp;
                    this.staticDown = TextureManager.p2StaticDown;
                    this.staticLeft = TextureManager.p2StaticLeft;
                    this.staticRight = TextureManager.p2StaticRight;
                    break;

                case 4:
                    this.walkAnimUp = TextureManager.p2WalkingUpAnim;
                    this.walkAnimDown = TextureManager.p2WalkingDownAnim;
                    this.walkAnimLeft = TextureManager.p2WalkingLeftAnim;
                    this.walkAnimRight = TextureManager.p2WalkingRightAnim;
                    
                    this.staticUp = TextureManager.p2StaticUp;
                    this.staticDown = TextureManager.p2StaticDown;
                    this.staticLeft = TextureManager.p2StaticLeft;
                    this.staticRight = TextureManager.p2StaticRight;
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
    public void update() {
        
    }
    
    /**
     * Draws the player to screen
     * @param sb 
     */
    @Override
    public void render(SpriteBatch sb)
    {
        inputMovePlayer(sb);          
    }
    
    
    /**
     * Moves the player if keyboard input is received
     * @param sb 
     */
    private void inputMovePlayer(SpriteBatch sb)
    {
        //Changes the position of the texture 
        pos.add(direction);
        String moveCommand ="";
        
        //Input handling and moving the player
        if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.LEFT))
        {
            //Set the speed the texture moves in x and y axis
            //this is the method inherited from Entity.java class
            setDirection(-150, 0);

            //Draw the walking animation
            sb.draw(getFrame(walkAnimLeft), pos.x, pos.y);
            
            lastMovementKeyPressed = "LEFT";
            //General: moveEnemyPlayer|playerId|direction|target
            moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|LEFT|*";
            
            //Send data to server
            client.sendData(moveCommand);
            
        }else if(Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT))
        {
            setDirection(150, 0);  
            
            sb.draw(getFrame(walkAnimRight), pos.x, pos.y);
            
            lastMovementKeyPressed = "RIGHT";
            moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|RIGHT|*";
            
            //Send data to server
            client.sendData(moveCommand);
            
        }else if(Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.UP))
        {
            setDirection(0, 150);
            
            sb.draw(getFrame(walkAnimUp), pos.x, pos.y);
            
            lastMovementKeyPressed = "UP";
            moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|UP|*";
            
            //Send data to server
            client.sendData(moveCommand);
            
        }else if(Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DOWN))
        {
            setDirection(0, -150);
            
            sb.draw(getFrame(walkAnimDown), pos.x, pos.y);
            
            lastMovementKeyPressed = "DOWN";
            moveCommand = "moveEnemyPlayer|" + Integer.toString(Constants.PLAYERID) + "|DOWN|*";
            
            //Send data to server
            client.sendData(moveCommand);
            
        }else if (Gdx.input.isKeyPressed(Keys.E) || Gdx.input.isKeyPressed(Keys.E))
        {
            setDirection(0,0);
            Bomb b1 =new Bomb(super.getPosition(),direction,1);   
            sb.draw(b1.getB1StaticBurns(),pos.x,pos.y);
            
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
            }
            
        }else
        {
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
        this.stateTime += Gdx.graphics.getDeltaTime(); 
        
        /*
        Obtains the current frame. This is given by the animation for the current time. 
        The second variable is the looping. 
        Passing in true, will tell the animation to restart after it reaches the last frame.
        */
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        
        return currentFrame;
    }
    
}
