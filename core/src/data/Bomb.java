package data;

/**
 *
 * @author phinix
 */
public interface Bomb {
  
    int range=2;//the range of the bomb
    int timer=1000;//the timer of the bomb
    
    /**
     * set the range of the bomb
     * @param range theRange of the bomb
     */
    public void setRange (int range);
    
    /**
     * set the Timer of the bomb;
     * @param timer the Timer of the bomb
     */
    public void setTimer (int timer);
    
    /**
     * get the range of the bomb
     * @return the range of the bomb
     */
    public int getRange();
    
    /**
     * get the Timer of the bomb;
     * @return the Timer of the bomb;
     */
    public int getTimer();
    
    
}
