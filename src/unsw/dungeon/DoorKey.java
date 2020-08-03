package unsw.dungeon;

public class DoorKey extends Entity {

    private int kid;

    /**
     * Constructor
     * @param x location
     * @param y location
     * @param kid unique integer id for key
     */
    public DoorKey(int x, int y, int kid) {
        super(x, y);
        this.kid = kid;
    }

    public int getKid() {
		return kid;
	}

	/*
	Method that allows player to collect item.
	 */
    public void collect (Dungeon d) {
    	d.getPlayer().obtainKey(this);
    }

}
