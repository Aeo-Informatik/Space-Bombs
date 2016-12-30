/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.map;

import com.gdx.bomberman.Constants;

/**
 *
 * @author qubasa
 */
public class ThinGridCoordinates {
    
    private float x;
    private float y;
     
    // Thin grid coordinates are always in float form
    public ThinGridCoordinates(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    // Calculate MapCellCoordinates to ThinGridCoordinates
    public ThinGridCoordinates(int x, int y)
    {
        this.x = x * Constants.MAPTEXTUREWIDTH;
        this.y = y * Constants.MAPTEXTUREHEIGHT;
    }

    public ThinGridCoordinates(MapCellCoordinates a)
    {
        this.x = a.getX() * Constants.MAPTEXTUREWIDTH;
        this.y = a.getY() * Constants.MAPTEXTUREHEIGHT;
    }
    
    public void set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void set(ThinGridCoordinates newPos)
    {
        this.x = newPos.getX();
        this.y = newPos.getY();
    }
    
    public void set(int x, int y)
    {
        this.x = x * Constants.MAPTEXTUREWIDTH;
        this.y = y * Constants.MAPTEXTUREHEIGHT;
    }
    
    public void add(ThinGridCoordinates a)
    {
        this.x += a.getX();
        this.y += a.getY();
    }
    
    public void addX(float x)
    {
        this.x += x;
    }
    
    public void addY(float y)
    {
        this.y += y;
    }
    
    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

        /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x * Constants.MAPTEXTUREWIDTH;
    }
    
    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }
 
    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y * Constants.MAPTEXTUREHEIGHT;
    }
}
