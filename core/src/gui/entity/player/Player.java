/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdx.bomberman.Constants;

import gui.AudioManager;
import gui.entity.Entity;
import gui.entity.EntityManager;
import gui.entity.bomb.Bomb;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author qubasa
 */
public abstract class Player extends Entity
{
    // Objects
    protected OrthographicCamera camera;
    
    // Variables
    protected float cameraSpeed = Constants.DEFAULTCAMERASPEED;
    private boolean hardLockMovement = false;
    
    //Debug
    private static ShapeRenderer debugRenderer = new ShapeRenderer();

    // Collision
    private int lastBombPlayerStandOnId = -1;

    //Constructor
    public Player(ThinGridCoordinates pos, ThinGridCoordinates direction, MapLoader map, EntityManager entityManager, OrthographicCamera camera)
    {
        super(pos, direction, map, entityManager);
        
        this.camera = camera;
    }
    
    protected void collisionTest()
    {
        // Collide bottom
        ThinGridCoordinates point = this.getCollisionPosDownLeft();
        Player.DrawDebugLine(point, point, 5, Color.RED, camera.combined);
        ThinGridCoordinates point2 = this.getCollisionPosDownRight();
        Player.DrawDebugLine(point2, point2, 5, Color.RED, camera.combined);
        
        // Collide Top
        ThinGridCoordinates point3 = this.getCollisionPosTopHalfLeft();
        Player.DrawDebugLine(point3, point3, 5, Color.RED, camera.combined);
        ThinGridCoordinates point4 = this.getCollisionPosTopHalfRight();
        Player.DrawDebugLine(point4, point4, 5, Color.RED, camera.combined);
        
        // Collide left
        ThinGridCoordinates point5 = this.getCollisionPosMiddleLeft();
        Player.DrawDebugLine(point5, point5, 5, Color.RED, camera.combined);
        // Collide left 2
        ThinGridCoordinates point9 = this.getCollisionPosMiddleLeft2();
        Player.DrawDebugLine(point9, point9, 5, Color.RED, camera.combined);

        // Collide right
        ThinGridCoordinates point6 = this.getCollisionPosMiddleRight();
        Player.DrawDebugLine(point6, point6, 5, Color.RED, camera.combined);
        // Collide right 2
        ThinGridCoordinates point8 = this.getCollisionPosMiddleRight2();
        Player.DrawDebugLine(point8, point8, 5, Color.RED, camera.combined);

        // Place item location
        ThinGridCoordinates point7 = this.getPlaceItemLocation();
        Player.DrawDebugLine(point7, point7, 5, Color.GOLD, camera.combined);
    }
    
    private static void DrawDebugLine(ThinGridCoordinates start, ThinGridCoordinates end, int lineWidth, Color color, Matrix4 projectionMatrix)
    {
        Gdx.gl.glLineWidth(lineWidth);
        debugRenderer.setProjectionMatrix(projectionMatrix);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(color);
        debugRenderer.line(new Vector2(start.getX(), start.getY()), new Vector2(end.getX() +5, end.getY() +1));
        debugRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
    
    /**------------------CAMERA FUNCTIONS------------------**/
    /**
    * Follows the entity with smooth camera movements.
    */
    protected void cameraFollowPlayer(ThinGridCoordinates pos)
    {
        Vector3 cameraPos = camera.position;
        cameraPos.x += (pos.getX() - cameraPos.x) * cameraSpeed * Constants.DELTATIME;
        cameraPos.y += (pos.getY() - cameraPos.y) * cameraSpeed * Constants.DELTATIME;
    }
    
    public void playSoundInDistance(Sound sound, long id, ThinGridCoordinates soundPos, float soundVolumeModificator)
    {
        float volume = AudioManager.getSoundVolume();
        
        if(volume != 0)
        {
            float abstandX = Math.abs(Math.abs(soundPos.getX()) - Math.abs(pos.getX()));
            float abstandY = Math.abs(Math.abs(soundPos.getY()) - Math.abs(pos.getY()));

            // Ist der Abstand zur X Achse groesser
            if(abstandX > abstandY)
            {
                volume = volume - (abstandX / 2500);
            }else
            {
                volume = volume - (abstandY / 2500);
            }

            if(volume < 0)
            {
                volume = 0.01f;
            }
        }
        sound.setVolume(id, volume * soundVolumeModificator);
    }
    
    public void gravitationForce(ThinGridCoordinates gravityCenterPos, float gravityForce, float gravityCenterRadius)
    {
        // Settings
        int iterations = 100;
        
        // Debug
        //Player.DrawDebugLine(pos, gravityCenterPos, 5, Color.RED, camera.combined);
        
        // Den Abstand von deinem jetzigen Punkt bis zum gravitations punkt berechnen
        float abstandX = Math.abs(gravityCenterPos.getX()) - Math.abs(pos.getX());
        float abstandY = Math.abs(gravityCenterPos.getY()) - Math.abs(pos.getY());
        
        // Berechnen des Vorzeichens
        float vorzeichenX = abstandX / Math.abs(abstandX);
        float vorzeichenY = abstandY / Math.abs(abstandY);
        
        // Die schnelligkeit mit dem vorzeichen multiplizieren um die richige gravitations Richtung zu bekommen
        float anziehungskraftRichtungX = gravityForce * vorzeichenX;
        float anziehungskraftRichtungY = gravityForce * vorzeichenY;
        
        // Wenn die Gravitation nicht stark genug ist dann beende Funktion 
        // bzw. wenn Spieler zu weit weg ist vom gravitations Punkt
        if(Math.abs(abstandX) > gravityForce || Math.abs(abstandY) > gravityForce)
        {
            return;
        }
        
        float addX = anziehungskraftRichtungX - abstandX;
        float addY = anziehungskraftRichtungY - abstandY;
        
        // Dont move player if in gravity center
        if(Math.abs(abstandX) <= gravityCenterRadius)
        {
            addX = 0;
        }
        
        if(Math.abs(abstandY) <= gravityCenterRadius)
        {
            addY = 0;
        }
        
        for(int i=0; i < iterations; i++)
        {   
            if(collidesLeft() || collidesLeftBomb() || collidesRight() || collidesRightBomb())
            {
                addX = 0;
            }
            
            if(collidesTop() || collidesTopBomb() || collidesBottom() || collidesBottomBomb())
            {
                addY = 0;
            }
            
            pos.add(new ThinGridCoordinates(addX / iterations * Constants.DELTATIME, addY / iterations * Constants.DELTATIME));
        }
    }
    
    /**------------------BLOCKED COLLISION FUNCTIONS------------------**/
    /**
     * Checks if entity collides with a blocked field on his left if so it returns true
     * @return boolean
     */
    protected boolean collidesLeft()
    {
        return map.isCellBlocked(new MapCellCoordinates(this.getCollisionPosMiddleLeft())) || map.isCellBlocked(new MapCellCoordinates(getCollisionPosMiddleLeft2()));

    }
    
    /**
     * Checks if entity collides with a blocked field on his right if so it returns true
     * @return boolean
     */
    protected boolean collidesRight()
    {
        return map.isCellBlocked(new MapCellCoordinates(this.getCollisionPosMiddleRight())) || map.isCellBlocked(new MapCellCoordinates(getCollisionPosMiddleRight2()));

    }
    
    /**
     * Checks if entity collides with a blocked field on his top if so it returns true
     * @return boolean
     */
    protected boolean collidesTop()
    {
        //Checks at players half on the left and right if there is a block or bomb located
        return map.isCellBlocked(new MapCellCoordinates(getCollisionPosTopHalfLeft()))
                || map.isCellBlocked(new MapCellCoordinates(getCollisionPosTopHalfRight()));
    }
    
    /**
     * Checks if entity collides with a blocked field on his bottom if so it returns true
     * @return boolean
     */
    protected boolean collidesBottom()
    {
        //Checks at players feet on the left if there is a block and on the right
        if(map.isCellBlocked(new MapCellCoordinates(getCollisionPosDownLeft())) 
                || map.isCellBlocked(new MapCellCoordinates(getCollisionPosDownRight())))
            return true;
        //else
        return false;
    }
    
    /**--------------------------MOVE PLAYER BOMB--------------------------**/
    protected void goLeft()
    {
        direction.set(-Constants.ITEMSPEEDINCREASE * Constants.DELTATIME, 0);
        
        for(int i=0; i < entitySpeed; i++)
        {
            if(!collidesLeft() && !collidesLeftBomb() && !isHardLockMovement())
            {
                pos.add(direction);
            }
        }
        playerOrientation = Constants.LEFT;
    }
    
    
    /**
    * Sets the entity movement direction to right controlled from entitySpeed.
    */
    protected void goRight()
    {
        direction.set(Constants.ITEMSPEEDINCREASE * Constants.DELTATIME, 0);
        for(int i=0; i < entitySpeed; i++)
        {
            if(!collidesRight() && !collidesRightBomb() && !isHardLockMovement())
            {
                pos.add(direction);
            }
        }
        playerOrientation = Constants.RIGHT;
    }
    
    
    /**
    * Sets the entity movement direction to up controlled from entitySpeed.
    */
    protected void goUp()
    {
        direction.set(0, Constants.ITEMSPEEDINCREASE * Constants.DELTATIME);
        for(int i=0; i < entitySpeed; i++)
        {
            if(!collidesTop() && !collidesTopBomb() && !isHardLockMovement())
            {
                pos.add(direction);
            }
        }
        playerOrientation = Constants.UP;
    }
    
    
    /**
    * Sets the entity movement direction to down controlled from entitySpeed.
    */
    protected void goDown()
    {
        direction.set(0, -Constants.ITEMSPEEDINCREASE * Constants.DELTATIME);
        for(int i=0; i < entitySpeed; i++)
        {
            if(!collidesBottom() && !collidesBottomBomb() && !isHardLockMovement())
            {
                pos.add(direction);
            }
        }
        playerOrientation = Constants.DOWN;
    }
    
    /**
    * Sets the entity movement direction to 0.
    */
    protected void goNone()
    {
        direction.set(0, 0);
    }
    
    /**--------------------------COLLIDES WITH BOMB--------------------------**/
    /**
     * Checks if entity collides with a bomb on his left if so it returns true
     * @return boolean
     */
    protected boolean collidesLeftBomb()
    {
        return collidesWithBomb(this.getCollisionPosMiddleLeft(), this.getCollisionPosMiddleLeft2());
    }
    
    
    /**
     * Checks if entity collides with a bomb on his right if so it returns true
     * @return boolean
     */
    protected boolean collidesRightBomb()
    {
        return collidesWithBomb(this.getCollisionPosMiddleRight(), this.getCollisionPosMiddleRight2());
    }
    
    
    /**
     * Checks if entity collides with a bomb on his top if so it returns true
     * @return boolean
     */
    protected boolean collidesTopBomb()
    {
        return collidesWithBomb(this.getCollisionPosTopHalfLeft(), this.getCollisionPosTopHalfRight());
    }
    
    
    /**
     * Checks if entity collides with a bomb on his bottom if so it returns true
     * @return boolean
     */
    protected boolean collidesBottomBomb()
    {
        return collidesWithBomb(this.getCollisionPosDownLeft(), this.getCollisionPosDownRight());

    }

    private boolean collidesWithBomb(ThinGridCoordinates pos1, ThinGridCoordinates pos2)
    {
        // Update bomb id
        standsOnBomb();

        // Get bomb object
        Bomb bomb1 = entityManager.getBombManager().getBombObjectInCell(new MapCellCoordinates(pos1));
        Bomb bomb2 = entityManager.getBombManager().getBombObjectInCell(new MapCellCoordinates(pos2));

        // Compare saved bomb id with the one colliding with. If they don't match, lock movement.
        if((bomb1 != null && bomb1.getBombId() != lastBombPlayerStandOnId) || (bomb2 != null && bomb2.getBombId() != lastBombPlayerStandOnId))
        {
            // Lock movement
            return true;
        }

        // Unlock movement
        return false;
    }

    // If player stands on a bomb save the bomb id and return the bomb object.
    protected Bomb standsOnBomb()
    {
        //Get bomb Id and save this Id for later
        Bomb bomb = entityManager.getBombManager().getBombObjectInCell(new MapCellCoordinates(this.getPlaceItemLocation()));
        if(bomb != null)
        {
            lastBombPlayerStandOnId =  bomb.getBombId();
            return bomb;
        }

        // If player doesn't stand on a bomb
        lastBombPlayerStandOnId = -1;
        return bomb;
    }
        
    /**
     * Checks if entity is standing on a deadly field if so it returns true
     * @return boolean
     */
    protected boolean touchesDeadlyBlock()
    {
        //Checks from the walking right texture a collision on the down left, down right
        return map.isCellDeadly(new MapCellCoordinates(this.getPlaceItemLocation()));

    }
    
    /**------------------GET COLLISION COORDINATES------------------**/
    public ThinGridCoordinates getPlaceItemLocation()
    {
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH / 2,pos.getY() + Constants.PLAYERHEIGHT / 4);
    }

    public ThinGridCoordinates getCollisionPosMiddleRight()
    {
        float marginX = 2f;
        float marginY = 0f;
        
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH + marginX,pos.getY() + Constants.PLAYERHEIGHT / 3 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosMiddleRight2()
    {
        float marginX = 2f;
        float marginY = 0f;
        
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH + marginX,pos.getY() + Constants.PLAYERHEIGHT / 8 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosMiddleLeft()
    {
        float marginX = 2f;
        float marginY = 0f;
        
        return new ThinGridCoordinates(pos.getX() - marginX,pos.getY() + Constants.PLAYERHEIGHT / 3 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosMiddleLeft2()
    {
        float marginX = 2f;
        float marginY = 0f;
        
        return new ThinGridCoordinates(pos.getX() - marginX,pos.getY() + Constants.PLAYERHEIGHT / 8 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosTopHalfRight()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH - marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY);
    }
    
    public ThinGridCoordinates getCollisionPosTopHalfLeft()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + marginX,pos.getY() + Constants.PLAYERHEIGHT / 2 + marginY);
    }

    public ThinGridCoordinates getCollisionPosDownRight()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + Constants.PLAYERWIDTH - marginX,pos.getY() - marginY);
    }
    
    public ThinGridCoordinates getCollisionPosDownLeft()
    {
        float marginX = 2f;
        float marginY = 3f;
        
        return new ThinGridCoordinates(pos.getX() + marginX, pos.getY() - marginY);
    }
    
    /**------------------GETTER & SETTER------------------**/
    public void setCameraFollowSpeed(float cameraSpeed)
    {
        this.cameraSpeed = cameraSpeed;
    }
    
    public float getCameraFollowSpeed()
    {
        return this.cameraSpeed;
    }

    /**
     * @return the hardLockMovement
     */
    public boolean isHardLockMovement() {
        return hardLockMovement;
    }

    /**
     * @param hardLockMovement the hardLockMovement to set
     */
    public void setHardLockMovement(boolean hardLockMovement) {
        this.hardLockMovement = hardLockMovement;
    }

}
