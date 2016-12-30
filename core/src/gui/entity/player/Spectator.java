/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.gdx.bomberman.Constants;

import gui.AnimEffects;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import inputHandling.InputHandler;

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
    private AnimEffects animEffects = new AnimEffects();
    private InputHandler inputHandler;
    
    private float changePlayerTimer = 0;
    private float changePlayerTimerEnd = 0.5f;
    
    //Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    public EnemyPlayer getCurrentEnemyPlayer() {
        return currentEnemyPlayer;
    }

    public void setCurrentEnemyPlayer(EnemyPlayer currentEnemyPlayer) {
        this.currentEnemyPlayer = currentEnemyPlayer;
    }
    
    // Constructor
    public Spectator(ThinGridCoordinates pos, ThinGridCoordinates direction, int playerId, OrthographicCamera camera, MapLoader map, Array <EnemyPlayer> enemies, EntityManager entityManager) 
    {
        super(pos, direction, map, entityManager, camera);
        
        this.blockLayer = map.getBlockLayer();
        this.enemies = enemies;
        this.renderObject.setProjectionMatrix(camera.combined);
        this.inputHandler = entityManager.getInputHandler();
        
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
        changePlayerTimer += Constants.DELTATIME;
        
        //Move spectators cam at its own will if freeCam = true
        if(freeCam)
        {
            renderObject.setProjectionMatrix(camera.combined);
            
            renderObject.begin();
            cameraFollowPlayer(pos);
            inputMoveSpectator();
            renderObject.end();
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
        renderObject.setColor(1.0f, 1.0f, 1.0f, 0.4f);
        
        /*------------------WALKING LEFT------------------*/
        if(inputHandler.isGoLeftKey())
        {
            if(!collidesLeft())
            {
                renderObject.draw(animEffects.getFrame(walkAnimLeft, true), pos.getX(), pos.getY());
                
                goLeft();

            }else
            {
                renderObject.draw(animEffects.getFrame(walkAnimLeft, true), pos.getX(), pos.getY());
                
                goNone();
            }
             
        /*------------------WALKING RIGHT------------------*/
        }else if(inputHandler.isGoRightKey())
        {
            if(!collidesRight())
            {
                renderObject.draw(animEffects.getFrame(walkAnimRight, true), pos.getX(), pos.getY());
                
                goRight();

            }else
            {
                renderObject.draw(animEffects.getFrame(walkAnimRight, true), pos.getX(), pos.getY());
                
                goNone();
            }
            
        /*------------------WALKING UP------------------*/
        }else if(inputHandler.isGoUpKey())
        {
            if(!collidesTop())
            {
                renderObject.draw(animEffects.getFrame(walkAnimUp, true), pos.getX(), pos.getY());
                
                goUp();
                
            }else
            {
                renderObject.draw(animEffects.getFrame(walkAnimUp, true), pos.getX(), pos.getY());
                
                goNone();
            }
            
        /*------------------WALKING DOWN------------------*/
        }else if(inputHandler.isGoDownKey())
        {
            if(!collidesBottom())
            {
                renderObject.draw(animEffects.getFrame(walkAnimDown, true), pos.getX(), pos.getY());
                
                goDown();
                
            }else
            {
                renderObject.draw(animEffects.getFrame(walkAnimDown, true), pos.getX(), pos.getY());
                
                goNone();
            }
            
        /*------------------NO MOVEMENT------------------*/    
        }else
        {
            goNone();
            
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
    }
    
    
    /**
     * Other actions the player can do with his keyboard.
     */
    private void inputDoSpectator()
    {
        /*------------------ZOOM OUT GAME------------------*/
        if(inputHandler.isZoomOutKey())
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
        if(inputHandler.isZoomInKey())
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
        if(inputHandler.isEnterKey())
        {
            
            changePlayerTimer = 0;
            camera.position.set(pos.getX(), pos.getY(), 0);
            freeCam = true;
        }
        
        
        /*------------------CAMERA STICKS TO OTHER PLAYER------------------*/
        if(inputHandler.isPlaceBombKey())
        {
            if(changePlayerTimer >= changePlayerTimerEnd)
            {
                changePlayerTimer = 0;
                //If enemies are present
                if(currentPlayerIndex < enemies.size && enemies.size > 0)
                {
                    freeCam = false;
                    currentEnemyPlayer = enemies.get(currentPlayerIndex);
                    currentPlayerIndex += 1;
                }else
                {
                    currentEnemyPlayer = null;
                    currentPlayerIndex = 0;
                    freeCam = true;
                }
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
        return map.isCellGhostBlocked(new MapCellCoordinates(this.getCollisionPosMiddleLeft()));

    }
    
    /**
     * Checks if entity collides with a blocked field on his right if so it returns true
     * @return boolean
     */
    @Override
    protected boolean collidesRight()
    {
        return map.isCellGhostBlocked(new MapCellCoordinates(this.getCollisionPosMiddleRight()));

    }
    
    /**
     * Checks if entity collides with a blocked field on his top if so it returns true
     * @return boolean
     */
    @Override
    protected boolean collidesTop()
    {
        //Checks at players half on the left and right if there is a block or bomb located
        return map.isCellGhostBlocked(new MapCellCoordinates(getCollisionPosTopHalfLeft()))
                || map.isCellGhostBlocked(new MapCellCoordinates(getCollisionPosTopHalfRight()));
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
