package unsw.dungeon;

import javafx.scene.media.AudioClip;

public class Door extends Entity {

    private int id;
	private boolean locked;
	private AudioClip doorSound;

	/**
	 * Constructor
	 * @param x position
	 * @param y position
	 * @param id door's unique id
	 */
    public Door(int x, int y, int id) {
        super(x, y);
        this.id = id;
		this.locked = true;
		this.doorSound = new AudioClip(getClass().getResource("sounds/door.mp3").toString());
    }

	/**
	 * Method that unlocks the door.
	 * @param kid integer id for door
	 * @return boolean
	 */
	public boolean unlock(int kid) {
    	if (this.id == kid) {
			this.locked = false;
			doorSound.play();
    		return true;
    	} 
    	return false;
    }

	/**
	 * Method to check if entity is blocked at a given location.
	 * @param x position
	 * @param y position
	 * @param d dungeon object
	 * @return true if it is blocked
	 */
    public boolean isBlocked(int x, int y, Dungeon d) {
    	if (this.locked == true) {
    		int kid = d.getPlayer().getKey();
    		if (unlock(kid)) {
    			d.getPlayer().useKey(this);
    		}
    	} 
    	return this.locked;
    }

}
