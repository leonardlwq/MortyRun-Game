package unsw.dungeon;

public class Boulder extends Entity {

    /**
     * Constructor
     * @param x position
     * @param y position
     */
	public Boulder(int x, int y) {
        super(x, y);
    }

    /**
     * Method that checks the position of the boulder when moved if 
     * it has a switch under it.
     * @param x position
     * @param y position
     * @param d dungeon object instance
     * @return boolean If the boulder has been moved.
     */
    public boolean checkBoulder(int x, int y, Dungeon d){
        Entity e = d.getEntity(x, y);
        Entity s = d.getSwitch(x, y);
        boolean moved = true;
    	if (e == null) {
            moved = false;
            //check if boulder is currently on a switch
            Entity c = d.getSwitch(getX(), getY());
            if(c != null)
            {
                ((Switch) c).changeStatus();
            }
            //moves boulder
            y().set(y);
            x().set(x);
            //check if boulder's new location is on a switch
            if(s != null)
            {
                ((Switch) s).changeStatus();
            }
        }
        d.checkGoals();
        
        return moved;
    }

    /**
     * Method to change status of switch if boulder entity is on it.
     * @param d dungeon object instance
     */
    public void checkStartLocation(Dungeon d){
        Entity s = d.getSwitch(getX(), getY());
        if(s != null)
        {
            ((Switch) s).changeStatus();
        }
    }

    public boolean isBlocked (int x, int y, Dungeon d) {
    	return false;
    }

}
