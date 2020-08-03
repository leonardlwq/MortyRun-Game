package unsw.dungeon;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;

public class Treasure extends Entity implements Subject {

    ArrayList<Observer> observers;
    private AudioClip gemSound;

    /**
     * Constructor
     * @param x location
     * @param y location
     * @param dungeon dungeon object
     */
	public Treasure(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.observers = new ArrayList<Observer>() ;
        dungeon.setnTreasures(dungeon.getnTreasures() + 1);
        this.gemSound = new  AudioClip(getClass().getResource("sounds/gem.mp3").toString());
    }


    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
	public void notifyObservers() {
		for(Observer o: observers) {
			o.update(this);
        }
    }

    public void collect (Dungeon d) {
    	d.removeEntity(this);
        d.setnTreasures(d.getnTreasures() - 1);
        gemSound.play();
    }

}
