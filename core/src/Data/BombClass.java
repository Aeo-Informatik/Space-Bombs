package Data;

/**
 *
 * @author phinix
 */
public class BombClass implements Bomb{
    
    private int range;//the range of the bomb
    private int timer;//the timer of the bomb
    /**
     * Coinstructor
     * @param range of the bomb
     * @param timer  of the bomb
     */
    public BombClass(int range, int timer) {
        this.range = range;
        this.timer = timer;
    }
    /**
     * get the range of the Bomb
     * @return the range of the bomb
     */
    public int getRange() {
        System.out.println(range);
        return range;
    }
    /**
     * set the range of the bomb
     * @param range theRange of the bomb
     */
    public void setRange(int range) {
        this.range = range;
    }
    /**
     * get the Timer of the bomb;
     * @return the Timer of the bomb;
     */
    public int getTimer() {
        System.out.println(timer);
        return timer;
    }
    /**
     * set the Timer of the bomb;
     * @param timer the Timer of the bomb
     */
    public void setTimer(int timer) {
        this.timer = timer;
    }
    
    
}
