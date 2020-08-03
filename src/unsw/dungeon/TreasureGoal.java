package unsw.dungeon;

import java.util.List;

public class TreasureGoal implements Goal, Observer{
	
	private int numTreasure;
	private int pickedUpCount;	
	
	public TreasureGoal(int numTreasure) {
		this.numTreasure = numTreasure;
		this.pickedUpCount = 0;
	}

	public void updateNum(int newNum){
		this.numTreasure = newNum;
	}

	@Override
	public String displayGoal(int nesting, Dungeon d){
		String output = "";
		for(int iterator = 0; iterator < nesting; iterator++)
		{
			output = output + "		"; 
		}
		if(isDone()){
			output = "Collect all gems (COMPLETE)";
		}
		else{
			output = "Collect all gems";
		}
		d.updateTreasureDisplay(isDone(), pickedUpCount, numTreasure);
		return output;
	}


	@Override
	public void update(Subject obj) {

		pickedUpCount++;
		
	}

	@Override
	public boolean isDone() {
		if (pickedUpCount == numTreasure) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void addObserverTo(List<Entity> entities) {
		for (Entity e: entities) {
			if (e instanceof Treasure) {
				((Treasure) e).addObserver(this);
			}
		}
	}

}
