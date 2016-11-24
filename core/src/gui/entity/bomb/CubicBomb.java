/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.entity.bomb;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author cb0703
 */
public abstract class CubicBomb extends Bomb{
    
    protected TextureRegion explosionCubic;
    
    public CubicBomb(ThinGridCoordinates pos, ThinGridCoordinates direction, int range, int explosionTime, float explosionDuration, float delayExplodeAfterHitByBomb, int playerId, MapLoader map, EntityManager entityManager) {
        super(pos, direction, range, explosionTime, explosionDuration, delayExplodeAfterHitByBomb, playerId, map, entityManager);
        
    }
   
    @Override
    public abstract void render();
    
    private void setBombTexture()
    {
        switch(playerId)
        {
            case 1:
                this.bombAnim = TextureManager.p1NormalBombAnim;
                this.explosionCubic = TextureManager.p1ExplosionCubic;
            /*
            case 2:
                this.bombAnim = TextureManager.p2NormalBombAnim;
                this.explosionCubic = TextureManager.p2ExplosionCubic;
            case 3:
                this.bombAnim = TextureManager.p3NormalBombAnim;
                this.explosionCubic = TextureManager.p3ExplosionCubic;
            case 4:
                this.bombAnim = TextureManager.p4NormalBombAnim;
                this.explosionCubic = TextureManager.p4ExplosionCubic;
                    */
            default:
                System.err.println("ERROR: Wrong playerId in bomb defined " + playerId + " using default p1 textures.");
                this.bombAnim = TextureManager.p1NormalBombAnim;
                this.explosionCubic = TextureManager.p1ExplosionCubic;
        }
    }
          
    @Override
    public void explode(){
        //cubic explosion for barrel and dynamite
    }
    
}
