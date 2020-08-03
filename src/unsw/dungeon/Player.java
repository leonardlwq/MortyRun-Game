package unsw.dungeon;

import javafx.scene.media.AudioClip;
import java.util.ArrayList;

/*
The player entity
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private SwordState sword;
    private DoorKey key;
    //private Boolean alive;
    private Boolean isInvincible;
    private int potionLife;
    private Boolean alive;
    private AudioClip portalSound;
    private AudioClip killSound;
    private AudioClip swingSound;
    private AudioClip keyDropSound;
    private AudioClip fireSound;
    private AudioClip keyPickSound;
    private AudioClip ohmanSound;

    /**
     * Constructor
     * @param dungeon dungeon object
     * @param x position
     * @param y position
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.sword = new SwordState0();
        this.key = null;
        this.isInvincible = false;
        this.potionLife = 0;
        this.alive = true;
        this.portalSound = new AudioClip(getClass().getResource("sounds/portal.mp3").toString());
        this.killSound = new AudioClip(getClass().getResource("sounds/sword.mp3").toString());
        this.swingSound = new AudioClip(getClass().getResource("sounds/whoosh.mp3").toString());
        this.keyDropSound = new AudioClip(getClass().getResource("sounds/keydrop.mp3").toString());
        this.fireSound = new AudioClip(getClass().getResource("sounds/fireblast.mp3").toString());
        this.keyPickSound = new AudioClip(getClass().getResource("sounds/keypickup.mp3").toString());
        this.ohmanSound = new AudioClip(getClass().getResource("sounds/ohman.wav").toString());
    }

    /**
     * Movement methods: each one checks alive, auto-pickups and decrement potion.
     */
    public void moveUp() {
        if(alive){
            if (getY() > 0 && !isBlocked(getX(), getY()-1, "up"))
                y().set(getY() - 1);
            pickup();
            decrementPotion();
            check_alive();
        }
    }

    public void moveDown() {
        if(alive){
            if (getY() < dungeon.getHeight() - 1 && !isBlocked(getX(), getY()+1, "down"))
                y().set(getY() + 1);
            pickup();
            decrementPotion();
            check_alive();
        }
    }

    public void moveLeft() {
        if(alive){
            if (getX() > 0 && !isBlocked(getX() - 1, getY(), "left"))
                x().set(getX() - 1);
            pickup();
            decrementPotion();
            check_alive();
        }
    }

    public void moveRight() {
        if(alive){
            if (getX() < dungeon.getWidth() - 1 && !isBlocked(getX() + 1, getY(), "right"))
                x().set(getX() + 1);
            pickup();
            decrementPotion();
            check_alive();
        }
        
    }

    /**
     * Method checking if entity blocks player.
     * @param x position
     * @param y position
     * @param direction String for direction
     * @return true if object is blocking
     */
    public boolean isBlocked (int x ,int y, String direction) {
        Entity e = dungeon.getEntity(x, y);
        //dungeon.updateEnemies(isInvincible);
    	if (e == null) {
    		return false;
        
        }
        else if(e instanceof Portal){
            Portal p = (Portal) e;
            teleports(p);
            return true;
        }
        else {
    		int new_x = x;
    		int new_y = y;
    		if (direction == "up") {
    			new_y --;
    		} else if (direction == "down") {
    			new_y++;
    		} else if (direction == "right") {
    			new_x++;
    		} else {
    			new_x--;
            }
            if (e instanceof Boulder)
            {
                return ((Boulder) e).checkBoulder(new_x, new_y, dungeon);
            }
            
    		return e.isBlocked(new_x, new_y, dungeon);
    	}
    }

    /**
     * Method to transport player to another portal.
     * @param p portal
     */
    public void teleports(Portal p)
    {
        Portal teleportTo = p.getLinked();
        portalSound.play();
        x().set(teleportTo.getX());
        y().set(teleportTo.getY());
    }


    /**
     * Called whenever player moves, to handle item pick-ups.
     */
    private void pickup() {
		Entity currSpot = dungeon.getEntity(getX(), getY());
		if (currSpot != null) {
			currSpot.collect(dungeon);
		}
	}

    public boolean getIsInvincible() {
		return isInvincible;
	}

    /**
     * Called whenever player moves.
     */
    public void check_alive() {
		ArrayList <Enemy> enemies = dungeon.getEnemies(getX(), getY());
		for (Enemy enemy: enemies) {
			if (enemy instanceof Enemy) {
				if (isInvincible) {
                    fireSound.play();
					dungeon.removeEntity(enemy);
				} else {
					getKilled();	
				}
			}
		}
	}
 
    public void getKilled() {
        this.alive = false;
        dungeon.removeEntity(this);
        dungeon.gameOverScreen();
    }
   
    public boolean isAlived() {
    	return this.alive;
    }

    
    public boolean isExit() {
        Entity currSpot = dungeon.getEntity(getX(), getY());
		if (currSpot instanceof Exit) {
            return true;
		} else {
            return false;
		}
	}
    
    
    public Dungeon getDungeon () {
        return this.dungeon;
    }

    /**
     * Should only be called when player picks up sword or uses sword.
     * @param sword
     */
    public void setSword(SwordState sword) {
        this.sword = sword;
        int uses;
        if(sword instanceof SwordState5){
            uses = 5;
        } else if(sword instanceof SwordState4){
            uses = 4;
        } else if(sword instanceof SwordState3){
            uses = 3;
        } else if(sword instanceof SwordState2){
            uses = 2;
        } else if(sword instanceof SwordState1){
            uses = 1;
        } else{
            uses = 0;
        }
        dungeon.updateSword(uses);
    }

    public void updateSwordDurability(int usesLeft){
        
    }
    
    public void useSword() {
        if(sword.isSword()) {
    		swingSound.play();
    	}
    	sword.swingSword(this);
    }
    
    public void kill_enemies (ArrayList <Enemy> enemies) {
        for (Enemy e : enemies) {
            dungeon.removeEntity(e);
            killSound.play();
        }
    }

    public void addSwordAttack(int i, int j) {
        dungeon.addSwordAttack(i,j);
		
	}

	public boolean hasSword() {
		return sword.isSword();
    }

    /**
     * Should only be called by Potion when player picks-up a Potion.
     * @param e potion entity
     */
    public void obtainPotion (Entity e) {
		this.isInvincible = true;
        this.potionLife = 31;
        dungeon.updateInvincibleStatus(potionLife);
        dungeon.setPlayerView(isInvincible);
		dungeon.removeEntity(e);
    }

    /**
     * Method that decrements potion usuage till it is finished.
     */
    private void decrementPotion() {
    	if (isInvincible) {
            potionLife--;
            dungeon.updateInvincibleStatus(potionLife);
        	if (potionLife == 0) {
                ohmanSound.play();
        		isInvincible = false;
        		dungeon.setPlayerView(isInvincible);
        	}
        }
    }

    /**
     * Should only be called by DoorKey when player picks-up a DoorKey.
     * @param e key entity
     */
    public void obtainKey (DoorKey e) {
		if (this.key == null) {
            this.key = e;
            this.keyPickSound.play();
            dungeon.showKeyInInventory(true);
            dungeon.removeEntity(e);
            
		}
    }

    /**
     * Method that recreates key entity back onto map that was collected.
     */
    public void dropKey() {
		if (key != null) {
            keyDropSound.play();
			key.x().set(getX());
			key.y().set(getY());
            dungeon.restoreKey(key);
            dungeon.showKeyInInventory(false);
			key = null;
		}
    }
    
    public int getKey() {
		if (this.key == null) return -1;
		else return this.key.getKid();
	}
	
	public void useKey(Door d) {
		this.key = null;
		dungeon.updateDoor(d);
	}
    
}
