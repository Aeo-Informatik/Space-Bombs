/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.entity.bomb;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.gdx.bomberman.Constants;

import gui.AudioManager;
import gui.TextureManager;
import gui.entity.EntityManager;
import gui.map.MapCellCoordinates;
import gui.map.MapLoader;
import gui.map.ThinGridCoordinates;

/**
 *
 * @author Christian
 */
public class Turret extends Bomb{
    
    private String currDirection = "Up";
    private int rounds = 0;
    
    
    private Animation Up    = TextureManager.turretAnimUp;
    private Animation Right = TextureManager.turretAnimRight;
    private Animation Down  = TextureManager.turretAnimDown;
    private Animation Left  = TextureManager.turretAnimLeft;
    
    
    public Turret(ThinGridCoordinates pos, int playerId, int range,  MapLoader map, EntityManager entityManager) 
    {
        super(pos, playerId, range*2, Constants.TURRETEXPLOSIONTIME, Constants.TURRETEXPLOSIONDURATION, Constants.TURRETDELAYEXPLODEAFTERHITBYBOMB, map, entityManager);
    
        super.setBombAnim(TextureManager.turretAnimUp);
    }
    
    @Override
    public void render(){
        
        switch(currDirection){
            case("Up"):
                        super.setBombAnim(Up);
                        break;
            case("Right"):
                        super.setBombAnim(Right); 
                        break;
            case("Down"):
                        super.setBombAnim(Down); 
                        break;
            case("Left"):
                        super.setBombAnim(Left); 
                        break;
            default:
                        System.out.println("Something went wrong incorrect direction");
                        break;
        }
        
        //To make sure no bomb gets placed into wall
        if(!map.isCellBlocked(new MapCellCoordinates(pos.getX(), pos.getY())) && !isExploded)
        {
            //Check if bomb has been hit by another bomb
            if(map.isCellDeadly(new MapCellCoordinates(pos.getX(), pos.getY())) && hasBombTouchedDeadlyTile == false)
            {
                this.isExploded = true;
                //Create new cell and set texture
                TiledMapTileLayer.Cell cellCenter = new TiledMapTileLayer.Cell();
                cellCenter.setTile(new StaticTiledMapTile(emptyBlock));
                deleteExplosionEffect(explosionRange, explosionRange, explosionRange, explosionRange);
            
                //Explosion center replaces bomb texture
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY(), cellCenter);
            }

            //If time to explode or deadly tile has been touched
            if(timerTillExplosion >= explosionTime)
            {
//                fuseSound.stop();
                explode(AudioManager.getNormalExplosion());

                //Delete explosion effect after a while
                if(timerTillExplosionDelete >= explosionDuration)
                {
                    deleteExplosionEffect(explosionRange, explosionRange, explosionRange, explosionRange);
                    
                }else
                {
                    //Add passed time to timer
                    timerTillExplosionDelete += Constants.DELTATIME;
                }

            }else if(!hasBombTouchedDeadlyTile)//Creates bomb animation
            {
                //Create new cell and set its animation texture
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(animEffects.getFrame(bombAnim, true)));
                cell.getTile().getProperties().put("bomb", null);

                //Set bomb into bomb layer
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY(), cell);
            }
            
            if(rounds >= 3)
            {
                this.isExploded = true;
                //Create new cell and set texture
                TiledMapTileLayer.Cell cellCenter = new TiledMapTileLayer.Cell();
                cellCenter.setTile(new StaticTiledMapTile(emptyBlock));
            
                //Explosion center replaces bomb texture
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY(), cellCenter);
            }
            
            // Add passed time to bomb activation timer
            timerTillExplosion += Constants.DELTATIME;
            
        }else 
        {
            // If bomb placed in wall delete bomb object.
            this.isExploded = true;
        }
    }
    

    @Override
    public void explode(Sound sound){
        
        switch(currDirection){
            
            case("Up"):
                        explodeUp();
                        break; 
            case("Right"):
                        explodeRight();
                        break;
            case("Down"):
                        explodeDown();
                        break;
            case("Left"):
                        explodeLeft();
                        break;
            default:
                        System.out.println("Something went wrong incorrect direction");
                        break;
        }
    }
    
    @Override
    public void deleteExplosionEffect(int cellsUp, int cellsDown, int cellsRight, int cellsLeft){
        switch(currDirection){
            
            case("Up"):
                        deleteUp();
                        currDirection = "Right";
                        timerTillExplosion = 0;
                        timerTillExplosionDelete = 0;
                        break;          
            case("Right"):
                        deleteRight();
                        currDirection = "Down";
                        timerTillExplosion = 0;
                        timerTillExplosionDelete = 0;
                        break;
            case("Down"):
                        deleteDown();
                        currDirection = "Left";
                        timerTillExplosion = 0;
                        timerTillExplosionDelete = 0;
                        break;
            case("Left"):
                        deleteLeft();
                        currDirection = "Up";
                        timerTillExplosion = 0;
                        timerTillExplosionDelete = 0;
                        rounds += 1;                    // after a full round increase round
                        break;
            default:
                        System.out.println("Something went wrong incorrect direction");
                        break;
        }
    }
    
    public void explodeUp(){
        //Explode UP
        for(int y=1; y <= explosionRange; y++)
        {
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y )))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionUpEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() + y, cellDown);
                break;
            }
            
            if(y != explosionRange) // If not end of explosion
            {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() + y, cell);
            }else
            {
                //Set end of explosion
                TiledMapTileLayer.Cell cellUp = new TiledMapTileLayer.Cell();
                cellUp.setTile(new StaticTiledMapTile(explosionUpEnd));
                cellUp.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() + y, cellUp);
            }
        }
    }
    
    public void deleteUp(){
        for(int y=1; y <= explosionRange; y++){
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));

            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y)))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() + y, cell);
                
                //Delete block
                deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
                
                break;
            }
            
            //Explosion down
            map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() + y, cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() + y));
        }
    }
    
    public void explodeRight(){
        //Explode RIGHT
        for(int x=1; x <= explosionRange; x++)
        {
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX() +x, cellPos.getY())))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionRightEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() +x, super.cellPos.getY(), cellDown);
                break;
            }
            
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() +x, super.cellPos.getY(), cell);
                
            }else
            {
                //Set end of explosion
                TiledMapTileLayer.Cell cellRight = new TiledMapTileLayer.Cell();
                cellRight.setTile(new StaticTiledMapTile(explosionRightEnd));
                cellRight.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() +x, super.cellPos.getY(), cellRight);
            }
        }
    }
    
    public void deleteRight(){
        for(int x=1; x <= explosionRange; x++){
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));

            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX() + x, cellPos.getY())))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX() + x, cellPos.getY(), cell);
                
                //Delete block
                deleteBlock(new MapCellCoordinates(cellPos.getX() + x, cellPos.getY()));
                
                break;
            }
            
            //Explosion down
            map.getBombLayer().setCell(cellPos.getX() + x, cellPos.getY(), cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX() + x, cellPos.getY()));
        }
    }
    
    public void explodeDown(){
        for(int y=1; y <= explosionRange; y++)
        {
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y )))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionDownEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() - y, cellDown);
                break;
            }
            
            if(y != explosionRange) // If not end of explosion
            {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionYMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() - y, cell);
            }else
            {
                //Set end of explosion
                TiledMapTileLayer.Cell cellUp = new TiledMapTileLayer.Cell();
                cellUp.setTile(new StaticTiledMapTile(explosionDownEnd));
                cellUp.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX(), super.cellPos.getY() - y, cellUp);
            }
        }
    }
    
    public void deleteDown(){
        for(int y=1; y <= explosionRange; y++){
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));

            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y)))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() - y, cell);
                
                //Delete block
                deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
                
                break;
            }
            
            //Explosion down
            map.getBombLayer().setCell(cellPos.getX(), cellPos.getY() - y, cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX(), cellPos.getY() - y));
        }
    }
    
    public void explodeLeft(){
        //Explode LEFT
        for(int x=1; x <= explosionRange; x++)
        {
            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX() -x, cellPos.getY())))
            {
                //Set ending texture and break out of loop
                TiledMapTileLayer.Cell cellDown = new TiledMapTileLayer.Cell();
                cellDown.setTile(new StaticTiledMapTile(explosionLeftEnd));
                cellDown.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() -x, super.cellPos.getY(), cellDown);
                break;
            }
            
            if(x != explosionRange)  // If not end of explosion
            {
                //Set cell with middle explosion texture
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                cell.setTile(new StaticTiledMapTile(explosionXMiddle));
                cell.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() -x, super.cellPos.getY(), cell);
                
            }else
            {
                TiledMapTileLayer.Cell cellLeft = new TiledMapTileLayer.Cell();
                cellLeft.setTile(new StaticTiledMapTile(explosionLeftEnd));
                cellLeft.getTile().getProperties().put("deadly", null);
                
                map.getBombLayer().setCell(super.cellPos.getX() -x, super.cellPos.getY(), cellLeft);
            }
        }
    }
    
    public void deleteLeft(){
        for(int x=1; x <= explosionRange; x++){
            TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
            cell.setTile(new StaticTiledMapTile(emptyBlock));

            //If explosion hits block
            if(map.isCellBlocked(new MapCellCoordinates(cellPos.getX() - x, cellPos.getY())))
            {
                //Delete explosion effect
                map.getBombLayer().setCell(cellPos.getX() - x, cellPos.getY(), cell);
                
                //Delete block
                deleteBlock(new MapCellCoordinates(cellPos.getX() - x, cellPos.getY()));
                
                break;
            }
            
            //Explosion down
            map.getBombLayer().setCell(cellPos.getX() - x, cellPos.getY(), cell);
            deleteBlock(new MapCellCoordinates(cellPos.getX() - x, cellPos.getY()));
        }
    }
}
