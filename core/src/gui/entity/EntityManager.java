/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.camera.OrthoCamera;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Vatiables
    private OrthoCamera camera;
    
    //Array from libgdx is much faster in comparison to an arraylist
    private Array<EnemyPlayer> enemies = new Array<>();
    private MainPlayer mainPlayer;
    
    //Constructor
    public EntityManager(OrthoCamera camera)
    {
        this.camera = camera;
    }
    

    public void render(SpriteBatch sb)
    {
        //For every Enemy Player Object that is stored in the arraylist execute the render function in it
        for(EnemyPlayer enemy: enemies)
        {
            enemy.render(sb);
        }
        
        //Executes the render function in the mainPlayer object
        if(mainPlayer != null)
            mainPlayer.render(sb);
    }
    
    
    public void update()
    {
        //For every Enemy Player Object that is stored in the arraylist execute the update function in it
        for(EnemyPlayer enemy: enemies)
        {
            enemy.update();
        }
        
        //Executes the update function in the mainPlayer object
        if(mainPlayer != null)
            mainPlayer.update();
    }
    
    /**--------------------SPAWN FUNCTIONS--------------------**/
    public void spawnEnemyPlayer(int x, int y, int playerId)
    {
        try
        {
            EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(x,y), new Vector2(0,0), playerId);
            enemies.add(enemyPlayer);
            
        }catch(Exception e)
        {
            throw e;
        }
    }
    
    
    public void spawnMainPlayer(int x, int y, int playerId) throws Exception
    {
        try 
        {
            mainPlayer = new MainPlayer(new Vector2(x,y), new Vector2(0,0), playerId, camera);
            
        } catch (Exception e) 
        {
            throw e;
        }
    }
    

    /**--------------------GETTER & SETTER--------------------**/
    /**
     * 
     * @return Array<EnemyPlayer>
     */
    public Array<EnemyPlayer> getEnemieArray()
    {
        return enemies;
    }
    
    /**
     * 
     * @return MainPlayer
     */
    public MainPlayer getMainPlayer()
    {
        return mainPlayer;
    }
}
