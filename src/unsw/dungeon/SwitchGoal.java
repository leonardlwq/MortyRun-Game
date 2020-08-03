package unsw.dungeon;

import java.util.List;

public class SwitchGoal implements Goal, Observer{

	private int numSwitch;
	private int countSwitchActivated;
	
	public SwitchGoal(int count) {
		this.numSwitch = count;
		this.countSwitchActivated = 0;
	}
	
	public int getNumSwitch() {
		return numSwitch;
	}
	
	public int getCountSwitchActivated() {
		return countSwitchActivated;
	}

	@Override
	public String displayGoal(int nesting, Dungeon d){
		String output = "";
		for(int iterator = 0; iterator < nesting; iterator++)
		{
			output = output + "		"; 
		}
		if(isDone()){
			output = "Activate all switches (COMPLETE)";
		}
		else{
			output = "Activate all switches";
		}
		d.updateSwitch(isDone(), countSwitchActivated, numSwitch);
		return output;
	}

	@Override
	public void update(Subject obj) {
		if (!(obj instanceof Switch)) return;
		Switch s = (Switch) obj;
		if (s.getActivateStatus()) {
			this.countSwitchActivated += 1;
			//System.out.println(countSwitchActivated);
			//System.out.println(numSwitch);
		}
		else {
			this.countSwitchActivated -= 1;
		}
	}

	@Override
	public boolean isDone() {
		//System.out.println(getCountSwitchActivated());
		//System.out.println(getNumSwitch());
		if (this.countSwitchActivated == this.numSwitch) {
			return true;
		}
		return false;
	}

	@Override
	public void addObserverTo(List<Entity> entities) {
		for (Entity e: entities) {
			if (e instanceof Switch) {
				//System.out.println("ADDED");
				((Switch) e).addObserver(this);
			}
		}
	}
}
