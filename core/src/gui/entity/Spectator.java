/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.Constants;
import gui.camera.OrthoCamera;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class Spectator extends Entity
{
    //Camera
    private OrthoCamera camera;
    private boolean freeCam = true;
    private int currentPlayerIndex = 0;
    private EnemyPlayer currentEnemyPlayer;
    
    //Collision detection
    private MapManager map;
    private TiledMapTileLayer blockLayer;
    private Array <EnemyPlayer> enemies;
    
    public Spectator(Vector2 pos, Vector2 direction, OrthoCamera camera, MapManager map, Array <EnemyPlayer> enemies) 
    {
        super(pos, direction, map);
        
        this.blockLayer = map.getBlockLayer();
        this.camera = camera;
        this.enemies = enemies;
    }

    @Override
    public void update() 
    {
        
    }
    
    
    public void render(SpriteBatch sb)
    {
        //Adding direction to position
        pos.add(this.direction);
        
        //Move spectators cam at its own will if freeCam = true
        if(freeCam)
        {
            inputMoveSpectator();
        }else
        {
            //Follow living enemie player
            camera.setPosition(this.currentEnemyPlayer.getPosition().x, this.currentEnemyPlayer.getPosition().y);
        }
        
        inputDoSpectator();
    }
    

    private void inputMoveSpectator()
    {
                
        float cameraSpeed = 2.51f;
        
        if((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)))
        {
            if(!collidesLeft())
            {
                //Set the speed the texture moves in x and y axis
                //This will be added to the position every render cycle
                setDirection(-150, 0);

                //Move camera x,y
                camera.translate( -1 * cameraSpeed,0);

            }else
            {
                camera.translate(0, 0);
                setDirection(0,0);
            }
             
        /*------------------WALKING RIGHT------------------*/
        }else if((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)))
        {
            if(!collidesRight())
            {
                setDirection(150, 0);
                camera.translate(cameraSpeed,0);
                
            }else
            {
                camera.translate(0, 0);
                setDirection(0,0);
            }
            
        /*------------------WALKING UP------------------*/
        }else if((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)))
        {
            if(!collidesTop())
            {
                setDirection(0, 150);
                camera.translate(0, cameraSpeed);
                
            }else
            {
                camera.translate(0, 0);
                setDirection(0,0);
            }
            
        /*------------------WALKING DOWN------------------*/
        }else if((Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)))
        {
            if(!collidesBottom())
            {
                camera.translate(0, -1 * cameraSpeed);
                setDirection(0, -150);
                
            }else
            {
                camera.translate(0, 0);
                setDirection(0,0);
            }
            
        /*------------------NO MOVEMENT------------------*/    
        }else
        {
            camera.translate(0, 0);
            setDirection(0,0);
        }
    }
    
    
    private void inputDoSpectator()
    {
        /*------------------ZOOM OUT GAME------------------*/
        if(Gdx.input.isKeyPressed(Input.Keys.O))
        {
            if(camera.zoom < 2.0)
            {
                camera.zoom += 0.02;
                camera.setPosition(pos.x, pos.y);
            }
            
        }

        /*------------------ZOOM INTO GAME------------------*/
        if(Gdx.input.isKeyPressed(Input.Keys.I))
        {
            if(camera.zoom > 0.5)
            {
                camera.zoom -= 0.02;
                camera.setPosition(pos.x, pos.y);
            }
        }
        
        /*------------------FREE MOVING CAMERA FOR SPECTATOR------------------*/
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            camera.setPosition(pos.x, pos.y);
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
        
        
        /*------------------QUIT GAME------------------*/
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
        {
            System.out.println("Quit game with Keyboard [ESC]");
            Gdx.app.exit();
        }
        
        /*------------------SWITCH TO FULLSCREEN AND BACK------------------*/
        if(Gdx.input.isKeyPressed(Input.Keys.F12))
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
