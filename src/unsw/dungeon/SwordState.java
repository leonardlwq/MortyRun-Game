package unsw.dungeon;

public interface SwordState {

    /**
     * Method that determines the swing style of the sword and location of tiles it affects.
     * @param player player object
     */
    public void swingSword(Player player);
	public boolean isSword();
    
}