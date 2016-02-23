package data;

/**
 *
 * @author phinix
 */
public class BlocksClass implements Blocks, GameObjects{
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
    
     /**
     * get how many coins will be droped afte the block is destroid
     * @return how Many Coins
     */
    public int getCoins() {
        return coins;
    }
    
    /**
     * set how many coins will be droped after 
     * the block is destroid
     * @param Coins how many coins
     */
    public void setCoins(int coins) {
        this.coins = coins;
    }    
}
