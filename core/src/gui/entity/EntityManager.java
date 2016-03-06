/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import gui.TextureManager;

/**
 *
 * @author qubasa
 */
public class EntityManager {
    
    //Array from libgdx is much faster in comparison to an arraylist
    private final Array<EnemyPlayer> enemies = new Array<>();
    private final MainPlayer mainPlayer;
    private final EnemyPlayer enemyPlayer;
    
    
    public EntityManager()
    {
        //Spawn mainPlayer at x=430 and y=100, the second argument is the direction in which the player
        //should move on creation x=0 y=0 means no movement, the third is the player number
        mainPlayer = new MainPlayer(new Vector2(430,100), new Vector2(0,0), 1);
        enemyPlayer = new EnemyPlayer(new Vector2(430,100), new Vector2(0,0), 2);
        enemies.add(enemyPlayer);
    }
    
    
    //Update is the same as render only that it doesn't have the SpriteBatch Object
    public void update()
    {
        //For every Enemy Player Object that is stored in the arraylist execute the update function in it
        for(EnemyPlayer enemy: enemies)
        {
            enemy.update();
        }
        
        //Executes the update function in the mainPlayer object
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
        mainPlayer.render(sb);
    }
    
    //Add an entity object to the arraylist
    public void addEntity(EnemyPlayer enemy)
    {
        enemies.add(enemy);
    }
    
}