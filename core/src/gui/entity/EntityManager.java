/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.map.MapManager;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Array from libgdx is much faster in comparison to an arraylist
    public static Array<EnemyPlayer> enemies = new Array<>();
    public static MainPlayer mainPlayer;

    //Update is the same as render only that it doesn't have the SpriteBatch Object
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
    

    public static void spawnEnemyPlayer(int x, int y, int playerId)
    {
        EnemyPlayer enemyPlayer = new EnemyPlayer(new Vector2(x,y), new Vector2(0,0), playerId);
        enemies.add(enemyPlayer);
    }
    
    
    public static void spawnMainPlayer(int x, int y, int playerId) throws Exception
    {
        try 
        {
            mainPlayer = new MainPlayer(new Vector2(x,y), new Vector2(0,0), playerId);
            
        } catch (Exception e) 
        {
            throw e;
        }
    }
}
