/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.bomberman.Constants;

import gui.AnimEffects;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.entity.bomb.Barrel;
import gui.entity.bomb.BlackHole;
import gui.entity.bomb.Dynamite;
import gui.entity.bomb.Infinity;
import gui.entity.bomb.Mine;
import gui.entity.bomb.NormalBomb;
import gui.entity.bomb.RemoteBomb;
import gui.entity.bomb.Turret;
import gui.entity.bomb.X3;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;
import gui.screen.MainPlayerHud;

/**
 *
 * @author qubasa
 */
public class EnemyPlayer extends Player
{
    /**------------------VARIABLES------------------**/
    // Player settings
    private int life = Constants.DEFAULTLIFE;
    private float godModeDuration = Constants.GODMODEDURATION; // How long the player is invulnerable after beeing hit by a bomb
    private int coins = Constants.STARTCOINS;
    private int coinBonus = Constants.COINBONUS;
    private int maxBombPlacing = Constants.DEFAULTBOMBPLACE;
    private int bombRange = Constants.DEFAULTBOMBRANGE;
    private int cubicRange = Constants.DEFAULTCUBICRANGE;
    
    // Bomb prices
    private int[] bombPrices = 
    {
        Constants.BOMB1, 
        Constants.BOMB2, 
        Constants.BOMB3, 
        Constants.BOMB4, 
        Constants.BOMB5, 
        Constants.BOMB6, 
        Constants.BOMB7, 
        Constants.BOMB8, 
        Constants.BOMB9,
        //Constants.BOMB10
    };
    
    private int choosenBomb = 1;
    
    // General variables
    private int playerId = 0;
    private boolean isEnemyPlayerDead = false;
    private ThinGridCoordinates newPos = pos;
    private String playerMovementDirection = "";
    private boolean stopPlayerMovement = true;
    
    // General objects
    private AnimEffects animEffects = new AnimEffects();
    
    // Player animation when he is moving around
    private final Animation walkAnimUp;
    private final Animation walkAnimDown;
    private final Animation walkAnimRight;
    private final Animation walkAnimLeft;
    
    
    /**------------------CONSTRUCTOR------------------**/
    public EnemyPlayer(ThinGridCoordinates pos, ThinGridCoordinates direction, int playerId, MapLoader map, EntityManager entityManager, OrthographicCamera camera) 
    {
        super(pos, direction, map, entityManager, camera);
        
        //Save variables & objects as global
        this.playerId = playerId;
        
        //Set player id textures
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
                System.err.println("ERROR: Wrong player number: " + playerId + " in EnemyPlayer. Using default p1");
                this.walkAnimUp = TextureManager.p1WalkingUpAnim;
                this.walkAnimDown = TextureManager.p1WalkingDownAnim;
                this.walkAnimLeft = TextureManager.p1WalkingLeftAnim;
                this.walkAnimRight = TextureManager.p1WalkingRightAnim;
        }
    }

    
    /**------------------RENDER------------------**/
    @Override
    public void render()
    {
        // If enemy player is dead
        if(life == 0)
        {
            onDeath(new MapCellCoordinates(pos));
        }
        renderObject.setProjectionMatrix(camera.combined);
        renderObject.begin();
        
        movePlayer();
        
        renderObject.end();
    }
    
    
    /**------------------OTHER FUNCTIONS------------------**/
    public void onDeath(MapCellCoordinates localCellPos)
    {
        MainPlayerHud.deathMessage("Player " + playerId + " died!");
        
        //Spawn tomb stone
        entityManager.getItemManager().spawnTombstone(localCellPos, coins, playerId);
        
        //Set death to true to delete object in entity manager 
        isEnemyPlayerDead = true;
    }
    
    
    /**------------------HIT DETECTION------------------**/
    public void hitByBomb() 
    {  
       // Makes the flashing animation
       animEffects.flashing(godModeDuration, 3, renderObject);
    }
    
    /**------------------PLACE BOMBS------------------**/
    public void placeInfinityBomb(ThinGridCoordinates pos, int explodePath)
    {
        if(!map.isBombPlaced(new MapCellCoordinates(pos)))
        {
            entityManager.getBombManager().spawnBomb(new Infinity(pos, playerId, bombRange, explodePath, map, entityManager));
        }
    }
    

    public void placeBomb(ThinGridCoordinates pos, String bombType)
    {
        switch(bombType)
        {
            case("default"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {        
                    entityManager.getBombManager().spawnBomb(new NormalBomb(pos, playerId, bombRange, map, entityManager));
                }
                break;
                
            case("dynamite"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {
                    entityManager.getBombManager().spawnBomb(new Dynamite(pos, playerId, cubicRange, map, entityManager));
                }
                break;
                
            case("X3"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {
                    entityManager.getBombManager().spawnBomb(new X3(pos, playerId, bombRange, 1, this.maxBombPlacing,  map, entityManager));
                }
                break;
                
            case("Remote"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {
                    entityManager.getBombManager().spawnRemote(new RemoteBomb(pos, playerId, bombRange,  map, entityManager));
                } 
                break;
                
            case("Barrel"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {
                    entityManager.getBombManager().spawnBomb(new Barrel(pos, playerId, cubicRange,  map, entityManager));
                }
                break;
            case("Mine"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {
                    entityManager.getBombManager().spawnBomb(new Mine(pos, playerId, bombRange,  map, entityManager));
                }
                break;
            case("Turret"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {
                    entityManager.getBombManager().spawnBomb(new Turret(pos, playerId, bombRange,  map, entityManager));
                }
                break;
            case("BlackHole"):
                if(!map.isBombPlaced(new MapCellCoordinates(pos)))
                {
                    entityManager.getBombManager().spawnBomb(new BlackHole(pos, playerId, cubicRange, map, entityManager));
                }
                break;
                
        }
    }
    
    
    /**------------------MOVEMENT------------------**/
    private void movePlayer()
    {
        // If position update came along
        if(newPos != null)
        {
            pos.set(newPos);
            newPos = null;
        }
        
        if(stopPlayerMovement)
        {
            stopMoving();
            return;
        }
        
        // If positive
        if(playerMovementDirection.equalsIgnoreCase(Constants.RIGHT))
        {
            moveRight();
        
        // If negative
        }else if(playerMovementDirection.equalsIgnoreCase(Constants.LEFT))
        {
            moveLeft();
        
        // If positive
        }else if(playerMovementDirection.equalsIgnoreCase(Constants.UP))
        {
            moveUp();
            
        // If negative
        }else if(playerMovementDirection.equalsIgnoreCase(Constants.DOWN))
        {
            moveDown();
        }
    }
    
    private void moveLeft()
    {
        super.playerOrientation = Constants.LEFT;
        
        if(!collidesLeft() && !collidesLeftBomb())
        {

            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimLeft, true), pos.getX(), pos.getY());
            
            // Set direction
            super.goLeft();
            
            
        }else
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimLeft, true), pos.getX(), pos.getY());
        }
    }
    
    private void moveRight()
    {
        super.playerOrientation = Constants.RIGHT;
        
        if(!collidesRight() && !collidesRightBomb())
        {
             //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimRight, true), pos.getX(), pos.getY());
            
            // Set direction
            super.goRight();
            
        }else
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimRight, true), pos.getX(), pos.getY());
        }
    }
    
    private void moveUp()
    {
        super.playerOrientation = Constants.UP;
        
        if(!collidesTop() && !collidesTopBomb())
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimUp, true), pos.getX(), pos.getY());
            
            // Set direction
            super.goUp();
            
        }else
        {
            //Draw the player with animation
            renderObject.draw(animEffects.getFrame(walkAnimUp, true), pos.getX(), pos.getY());
        }
    }
    
    private void moveDown()
    {
        super.playerOrientation = Constants.DOWN;
        
        if(!collidesBottom() && !collidesBottomBomb())
        {
            renderObject.draw(animEffects.getFrame(walkAnimDown, true), pos.getX(), pos.getY());
            
            // Set direction
            super.goDown();
            
        }else
        {
            renderObject.draw(animEffects.getFrame(walkAnimDown, true), pos.getX(), pos.getY());
        }
    }
    
    private void stopMoving()
    {
        super.goNone();
        
        //Draws the player if he stands still
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
    
    /**--------------------GETTER & SETTER--------------------**/
    public boolean isEnemyDead()
    {
        return this.isEnemyPlayerDead;
    }
    
    public int getPlayerId()
    {
        return this.playerId;
    }
    
    public void setPlayerId(int playerId)
    {
        this.playerId = playerId;
    }
    
    public int getLife()
    {
        return this.life;
    }
    
    public void setLife(int life)
    {
        this.life = life;
    }
    
    public int getBombRange()
    {
        return this.bombRange;
    }
    
    public void setBombRange(int bombRange)
    {
        this.bombRange = bombRange;
    }
    
    public int getCoins()
    {
        return this.coins;
    }
    
    public void setCoins(int coins)
    {
        this.coins = coins;
    }
    
    public int getMaxBombPlacing() 
    {
        return maxBombPlacing;
    }

    public void setMaxBombPlacing(int maxBombPlacing) 
    {
        this.maxBombPlacing = maxBombPlacing;
    }
    
    public int getCubicRange() {
        return cubicRange;
    }

    public void setCubicRange(int cubicRange) {
        this.cubicRange = cubicRange;
    }

    /**
     * @return the newPos
     */
    public ThinGridCoordinates getNewPos() {
        return newPos;
    }

    /**
     * @param newPos the newPos to set
     */
    public void setNewPos(ThinGridCoordinates newPos) {
        this.newPos = newPos;
    }

    /**
     * @return the playerMovementDirection
     */
    public String getPlayerMovementDirection() {
        return playerMovementDirection;
    }

    /**
     * @param playerMovementDirection the playerMovementDirection to set
     */
    public void setPlayerMovementDirection(String playerMovementDirection) {
        this.playerMovementDirection = playerMovementDirection;
    }

    /**
     * @return the stopPlayerMovement
     */
    public boolean isStopPlayerMovement() {
        return stopPlayerMovement;
    }

    /**
     * @param stopPlayerMovement the stopPlayerMovement to set
     */
    public void setStopPlayerMovement(boolean stopPlayerMovement) {
        this.stopPlayerMovement = stopPlayerMovement;
    }
    
    /*------------------ GETTER & SETTER ------------------*/  
    public int getBombPrice(int bombNumber) // Used by main player hud
    {
        return bombPrices[bombNumber -1];
    }
    
    public void setBombPrice(int bombNumber, int price)
    {
        bombPrices[bombNumber -1] = price;
    }

    /**
     * @return the choosenBomb
     */
    public int getChoosenBomb() {
        return choosenBomb;
    }

    /**
     * @param choosenBomb the choosenBomb to set
     */
    public void setChoosenBomb(int choosenBomb) {
        this.choosenBomb = choosenBomb;
    }

    /**
     * @return the coinBonus
     */
    public int getCoinBonus() {
        return coinBonus;
    }

    /**
     * @param coinBonus the coinBonus to set
     */
    public void setCoinBonus(int coinBonus) {
        this.coinBonus = coinBonus;
    }
}
