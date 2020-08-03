/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private DungeonController dungeonC;
    private List<Enemy> enemies;
	private int nEnemies;
	private int nTreasures;
	private int nSwitches;
    private Goal goal;
	private boolean isGameFinished;
	private boolean isGamePaused;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.dungeonC = null;
        this.enemies = new ArrayList<Enemy>();
		this.setnEnemies(0);
		this.setnTreasures(0);
		this.setnSwitches(0);
        this.goal = null;
		this.isGameFinished = false;
		this.isGamePaused = false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void addEnemies(Enemy enemy) {
		enemies.add(enemy);
	} 
    
    public void removeEnemies(Enemy enemy) {
    	enemies.remove(enemy);
    }
    
    public int getnEnemies() {
		return nEnemies;
	}

	public void setnEnemies(int nEnemies) {
		this.nEnemies = nEnemies;
	}

	public int getnTreasures() {
		return nTreasures;
	}

	public void setnTreasures(int nTreasures) {
		this.nTreasures = nTreasures;
	}

	public void setnSwitches(int nSwitches){
		this.nSwitches = nSwitches;
	}

	public void updateInvincibleStatus(int p){
		dungeonC.updatePotion(p);
	}

	public int getnSwitches(){
		return nSwitches;
	}

    public Entity getEntity(int x, int y) {
    	for (Entity e : entities) {
    		if (e.getX() == x && e.getY() == y) {
    			if (e instanceof Player || e instanceof Switch) {
    				continue;
    			} else {
    				return e;
    			}
    		}
    	}
		return null;
    }


    public Portal getPortal(int pid){
      for (Entity e : entities){
        if(e instanceof Portal){
          if(pid == ((Portal) e).getPid())
          {
            Portal p = (Portal)e;
            return p;
          }
        }
      }
      return null;
    }

    public Entity getSwitch(int x, int y) {
    	for (Entity e : entities) {
    		if (e.getX() == x && e.getY() == y) {
    			if (e instanceof Switch) {
    				return e;
    			}
    		}
    	}
		return null;
    }

    /**
     * This method is used to remove entity from the entities list in dungeon
     * if the entity is a enemy then decrement the nEnemies in dungeon and check goal
     * else if entity is a treasure then check goal
     * @param entity of an object
     */
    public void removeEntity(Entity entity) {
    	if (entity instanceof Enemy) {
    		removeEnemies((Enemy) entity);
    		setnEnemies(getnEnemies() - 1);
       		((Enemy) entity).notifyObservers();
			 checkGoals();
			 //System.out.println("Killed an enemy.");
    	} else if (entity instanceof Treasure) {
			((Treasure) entity).notifyObservers();
			//dungeonC.updateTreasure();
			checkGoals();
			//System.out.println("Picked up gold.");
    	}
    	entities.remove(entity);
    	if (dungeonC != null) dungeonC.removeEntity(entity);
  }

  public List<Entity> getAllEntities(){
	  return entities;
  }

  	/**
	 * This method is used to restore the key back to the dungeon's entities list
	 * @param key for the door
	 */
	public void restoreKey(DoorKey key) {
		entities.add(key);
		if (dungeonC != null) dungeonC.addEntity(key);
	}

	public void showKeyInInventory(boolean status){
		dungeonC.updateKeyAppearance(status);
	}

	public void updateKeyInventory(){

	}

	public void updateDoor(Entity e) {
		if (dungeonC != null) {
			dungeonC.updateDoor(e);
		}
	}


    public void set_dc(DungeonController dungeonController) {
		this.dungeonC = dungeonController;
	}


    public void setGoal(Goal g) {
		this.goal = g;
	}

    public void addObserverToEntities(Observer o) {
		o.addObserverTo(entities);
    }
    
    public void checkGoals() {
		//System.out.println("CHECK GOAL");
		if(goal == null){
		}
		else if (goal.isDone()) {
			isGameFinished = true;
			dungeonC.winningScreen();
		}
		displayGoals();
	}
	
	public void displayGoals(){
		String output = "OBJECTIVE: \n" + goal.displayGoal(0, this);
		dungeonC.updateGoalDisplay(output);
	}

	public void updateTreasureDisplay(boolean isDone, int done, int total){
        dungeonC.updateTreasure(isDone, done, total);
    }

    public void updateEnemyDisplay(boolean isDone, int done, int total){
        dungeonC.updateEnemy(isDone, done, total);
    }

    public void updateSwitch(boolean isDone, int done, int total){
        dungeonC.updateSwitch(isDone, done, total);
	}
	
	public void updateSword(int uses){
		dungeonC.updateSwordDurability(uses);
	}

	public void updatePause(){
		if(isGamePaused)
		{
			this.isGamePaused = false;
		}
		else{
			this.isGamePaused = true;
		}
	}

	public boolean getPauseStatus(){
		return isGamePaused;
	}

    
    public void updateEnemies(boolean isInvincible) {
	  	//if(!isGamePaused){
			for (Enemy e: enemies) {
				if (isInvincible) {
				((Enemy) e).moveAwayPlayer();;
				} else {
				((Enemy) e).moveTowardsPlayer();
				}
			}
		//}
		
		
	}
	
	/**
     * This method is only called by player when the player use sword or moving 
     * and return list of enemies at the given coordinate.
     * @param x position
     * @param y position
     * @return ArrayList <Enemy>
     */
    public ArrayList <Enemy> getEnemies (int x, int y) {
    	ArrayList <Enemy> enemies2 = new ArrayList <Enemy> ();
    	for (Enemy e : enemies) {
    		if (e.getX() == x && e.getY() == y) {
    			enemies2.add(e);
    		};
    	}
    	return enemies2;
	}
	
    
    public void addSwordAttack(int i, int j) {
		if (dungeonC != null)
		{
			dungeonC.addSwordAttack(i, j);
		}		
		
	}

	public void setPlayerView(boolean b) {
		if(dungeonC != null){
			dungeonC.setPlayerView(b);
		}
		
		
	}

    public boolean isGameFinished() {
		return isGameFinished;
	}

	/**
	 * This method is used to call updateEnemies if player is having a potion or potion is finished
	 */
	public void tick() {
		if (player.isAlived()) {
			updateEnemies(player.getIsInvincible());
			player.check_alive();
		}
	}

	public void gameOverScreen() {
		dungeonC.gameOverScreen();	
	}
	

}
