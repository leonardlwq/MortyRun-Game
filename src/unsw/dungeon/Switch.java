package unsw.dungeon;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;

public class Switch extends Entity implements Subject {

    //private Dungeon dungeon;
    private boolean isActivate;
    private ArrayList <Observer> obs = new ArrayList<>();;
    private AudioClip switchSound;

    /**
     * Constructor
     * @param x location
     * @param y location
     * @param dungeon dungeon object
     */
	public Switch(int x, int y, Dungeon dungeon) {
        super(x, y);
        this.isActivate = false;
        //this.dungeon = dungeon;
        dungeon.setnSwitches(dungeon.getnSwitches() + 1);
        this.switchSound = new AudioClip(getClass().getResource("sounds/switch.mp3").toString());
    }

    /**
     * Method that changes the status of the switch depending on boulder situation.
     */
    public void changeStatus()
    {
        if(this.isActivate == false)
        {
            this.isActivate = true;
            switchSound.play();
            //this.dungeon.updateSwitches(true);          
        }
        else{
            this.isActivate = false;
            switchSound.play(1.75, 0, 1.5, 0, 1);
            //this.dungeon.updateSwitches(false);  
        }
        notifyObservers();

    }

    public boolean getActivateStatus() {
		return isActivate;
	}



    @Override
    public void addObserver(Observer o) {
        obs.add(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: obs) {
			o.update(this);
		}
    }

}
