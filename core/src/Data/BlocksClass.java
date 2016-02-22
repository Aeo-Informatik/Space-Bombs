package Data;

/**
 *
 * @author cb0703
 */
public class BlocksClass implements Blocks{
    private boolean destructible;//if the block is destructible
    private int coins;//how many coins will be droped after the desruction 
    /**
     * 
     * @param destructible if the block is destructible
     * @param coins 
     */
    public BlocksClass(boolean destructible, int coins) {
        this.destructible = destructible;
        this.coins = coins;
    }
    /**
     * get if the block is destructible
     * @return if the block is destructible 
     */
    public boolean getDestructible() {
        return destructible;
    }
    /**
     * set if the block is destructible
     * @param destructible if the block is destructible
     */
    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    


    
    
}
