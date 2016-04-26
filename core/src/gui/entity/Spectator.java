/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import gui.Constants;
import gui.TextureManager;
import gui.camera.OrthoCamera;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class Spectator extends Entity
{

    private OrthoCamera camera;
    
    //Collision detection
    private MapManager map;
    private TiledMapTileLayer blockLayer;
    private final Animation walkAnimRight;
    
    public Spectator(Vector2 pos, Vector2 direction, OrthoCamera camera, MapManager map) 
    {
        super(null, pos, direction);
        
        this.blockLayer = map.getBlockLayer();
        this.camera = camera;
        this.walkAnimRight = TextureManager.p1WalkingRightAnim; // only for player size purposes
    }

    @Override
    public void update() 
    {
        
    }
    
    /**
     * Checks if the block on given entity coordinates is blocked.
     * @param x
     * @param y
     * @return boolean
     */
    private boolean isCellBlocked(float x, float y)
    {
        TiledMapTileLayer.Cell cell = blockLayer.getCell((int) (x / blockLayer.getTileWidth()), (int) (y / blockLayer.getTileHeight()));
        //System.out.println("X: " + (int) (x / blockLayer.getTileWidth()) + " Y: " + (int) (y / blockLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("ghost-blocked");
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
    
    public void render(SpriteBatch sb)
    {
        //Adding direction to position
        pos.add(this.direction);
        inputMoveSpectator();
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
        if (Gdx.input.isKeyPressed(Input.Keys.Z))
        {
            if(camera.zoom < 2.0)
            {
                camera.zoom += 0.02;
                camera.setPosition(pos.x, pos.y);
            }
            
        }

        /*------------------ZOOM INTO GAME------------------*/
        if (Gdx.input.isKeyPressed(Input.Keys.U))
        {
            if(camera.zoom > 0.5)
            {
                camera.zoom -= 0.02;
                camera.setPosition(pos.x, pos.y);
            }
        }
        
        /*------------------CAMERA CENTERS PLAYER------------------*/
        if (Gdx.input.isKeyPressed(Input.Keys.P))
        {
            camera.setPosition(pos.x, pos.y);
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
