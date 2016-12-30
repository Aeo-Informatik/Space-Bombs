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
public class MapCellCoordinates {
    
    private int x;
    private int y;
    
    public MapCellCoordinates(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public MapCellCoordinates(float x, float y)
    {
        this.x = (int) (x / Constants.MAPTEXTUREWIDTH);
        this.y = (int) (y / Constants.MAPTEXTUREHEIGHT);
    }

    public MapCellCoordinates(ThinGridCoordinates a)
    {
        this.x = (int) (a.getX() / Constants.MAPTEXTUREWIDTH);
        this.y = (int) (a.getY() / Constants.MAPTEXTUREHEIGHT);
    }
    
    public boolean equalCoordinates(MapCellCoordinates e)
    {
        return e.getX() == x && e.getY() == y;
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }
    
        /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = (int) (x / Constants.MAPTEXTUREWIDTH);
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
        /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = (int) (y / Constants.MAPTEXTUREHEIGHT);
    }
    
}
