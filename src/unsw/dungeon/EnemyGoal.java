package unsw.dungeon;

import java.util.List;

public class EnemyGoal implements Goal, Observer{

	private int numEnemies;
	private int killed;
	/**
	 * Constructor
	 * @param nE is the number of enemies
	 */
	public EnemyGoal(int nE) {
		this.numEnemies = nE;
		this.killed = 0;
	}

	public int getNumEnemies() {
		return numEnemies;
	}

	public int getKilled() {
		return killed;
	}


	@Override
	public String displayGoal(int nesting, Dungeon d){
		String output = "";
		for(int iterator = 0; iterator < nesting; iterator++)
		{
			output = output + "		"; 
		}
		if(isDone()){
			output = "Eliminate all enemies (COMPLETE)";
		}
		else{
			output = "Eliminate all enemies";
		}

		d.updateEnemyDisplay(isDone(), killed, numEnemies);
		
		return output;
	}


	@Override
	public boolean isDone() {
		if (killed == numEnemies)
			return true;
		else 
		return false;
	}
	/**
	 * Update the goal status
	 */
	@Override
	public void update(Subject obj) {
		if (obj instanceof Enemy) {
			this.killed ++;
		}
	}
	/**
	 * Add object into the observer class
	 */
	@Override
	public void addObserverTo(List <Entity> entities) {
		for (Entity e: entities) {
			if (e instanceof Enemy) {
				((Enemy) e).addObserver(this);
			}
		}
	}

}
