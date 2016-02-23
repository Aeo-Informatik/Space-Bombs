package data;

/**
 *
 * @author phinix
 */
public interface Blocks {
    boolean destructible=true;//if the block is destructible
    int coins =4;//how many coins will be droped after the desruction
    
    /**
     * set how many coins will be droped after 
     * the block is destroid
     * @param Coins how many coins
     */
    public void setCoins(int Coins);
    
    
    /**
     * set if the block is destructible
     * @param destructible if the Block is Destructible
     */
    public void setDestructible(boolean destructible);
    
    /**
     * get how many coins will be droped afte the block is destroid
     * @return how Many Coins
     */
    public int getCoins();
    
    /**
     * get if the block is destructible
     * @return if the Block is Destructible
     */
    public boolean getDestructible();
}

