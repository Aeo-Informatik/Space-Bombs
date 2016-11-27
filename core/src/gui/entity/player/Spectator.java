/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public class Spectator extends Player
{
    // General objects
    private boolean freeCam = true;
    private int currentPlayerIndex = 0;
    private EnemyPlayer currentEnemyPlayer;
    private Array <EnemyPlayer> enemies;
    
    // Constructor
    public Spectator(ThinGridCoordinates pos, ThinGridCoordinates direction, OrthographicCamera camera, MapLoader map, Array <EnemyPlayer> enemies, EntityManager entityManager) 
    {
        super(pos, direction, map, entityManager, camera);
        
        this.blockLayer = map.getBlockLayer();
        this.enemies = enemies;
        this.renderObject.setProjectionMatrix(camera.combined);
    }

    
    @Override
    public void render()
    {
        //Adding direction to position
        pos.add(this.direction);
        
        //Move spectators cam at its own will if freeCam = true
        if(freeCam)
        {
            inputMoveSpectator();
            cameraFollowPlayer(pos);
        }else
        {
            //Follow living enemie player
            cameraFollowPlayer(currentEnemyPlayer.getPosition());
        }
        
        //Keyboard interception
        inputDoSpectator();
    }
    
    
    /**
     * Moves and renders the spectator on key press.
     */
    private void inputMoveSpectator()
    {
        if((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)))
        {
            if(!collidesLeft())
            {
                //Set the speed the texture moves in x and y axis
                //This will be added to the position every render cycle
                goLeft();

            }else
            {
                stopMoving();
            }
             
        /*------------------WALKING RIGHT------------------*/
        }else if((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)))
        {
            if(!collidesRight())
            {
                goRight();

            }else
            {
                stopMoving();
            }
            
        /*------------------WALKING UP------------------*/
        }else if((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)))
        {
            if(!collidesTop())
            {
                goUp();
                
            }else
            {
                stopMoving();
            }
            
        /*------------------WALKING DOWN------------------*/
        }else if((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)))
        {
            if(!collidesBottom())
            {
                goDown();
                
            }else
            {
                stopMoving();
            }
            
        /*------------------NO MOVEMENT------------------*/    
        }else
        {
            stopMoving();
        }
    }
    
    
    /**
     * Other actions the player can do with his keyboard.
     */
    private void inputDoSpectator()
    {
        /*------------------ZOOM OUT GAME------------------*/
        if(Gdx.input.isKeyPressed(Input.Keys.O) || Gdx.input.isKeyPressed(Input.Keys.MINUS))
        {
            if(camera.zoom < 2.0)
            {
                camera.zoom += 0.02;
                
                if(freeCam)
                {
                    camera.position.set(pos.getX(), pos.getY(), 0);
                }else
                {
                    camera.position.set(currentEnemyPlayer.getPosition().getX(), currentEnemyPlayer.getPosition().getY(), 0);
                }
            }
        }

        /*------------------ZOOM INTO GAME------------------*/
        if(Gdx.input.isKeyPressed(Input.Keys.I) || Gdx.input.isKeyPressed(Input.Keys.PLUS))
        {
            if(camera.zoom > 0.5)
            {
                camera.zoom -= 0.02;
                
                if(freeCam)
                {
                    camera.position.set(pos.getX(), pos.getY(), 0);
                }else
                {
                    camera.position.set(currentEnemyPlayer.getPosition().getX(), currentEnemyPlayer.getPosition().getY(), 0);
                }
            }
        }
        
        /*------------------FREE MOVING CAMERA FOR SPECTATOR------------------*/
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            camera.position.set(pos.getX(), pos.getY(), 0);
            freeCam = true;
        }
        
        
        /*------------------CAMERA STICKS TO OTHER PLAYER------------------*/
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            //If enemies are present
            if(currentPlayerIndex < enemies.size && enemies.size > 0)
            {
                freeCam = false;
                currentEnemyPlayer = enemies.get(currentPlayerIndex);
                currentPlayerIndex += 1;
            }else
            {
                currentPlayerIndex = 0;
                freeCam = true;
            }
        }
    }
    
    
    /**------------------GHOST MOVEMENT------------------**/
    /**
     * Checks if entity collides with a blocked field on his left if so it returns true
     * @return boolean
     */
    @Override
    protected boolean collidesLeft()
    {
        if(map.isCellGhostBlocked(new MapCellCoordinates(this.getCollisionPosMiddleLeft())))
            return true;

        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his right if so it returns true
     * @return boolean
     */
    @Override
    protected boolean collidesRight()
    {
        if(map.isCellGhostBlocked(new MapCellCoordinates(this.getCollisionPosMiddleRight())))
            return true;

        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his top if so it returns true
     * @return boolean
     */
    @Override
    protected boolean collidesTop()
    {
        //Checks at players half on the left and right if there is a block or bomb located
        if(map.isCellGhostBlocked(new MapCellCoordinates(getCollisionPosTopHalfLeft())) 
                || map.isCellGhostBlocked(new MapCellCoordinates(getCollisionPosTopHalfRight())))
            return true;
        return false;
    }
    
    /**
     * Checks if entity collides with a blocked field on his bottom if so it returns true
     * @return boolean
     */
    @Override
    protected boolean collidesBottom()
    {
        //Checks at players feet on the left if there is a block and on the right
        if(map.isCellGhostBlocked(new MapCellCoordinates(getCollisionPosDownLeft())) 
                || map.isCellGhostBlocked(new MapCellCoordinates(getCollisionPosDownRight())))
            return true;
        //else
        return false;
    }
    
    /*------------------Getter & Setter------------------*/

}
