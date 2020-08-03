package unsw.dungeon;

import java.util.ArrayList;


public class CompositeGoal implements Goal {

	
	private ArrayList<Goal> goals = new ArrayList<Goal>();
	private String condition;
	
	/**
	 * Constructor
	 * @param condition - String
	 */
	public CompositeGoal(String condition) {
		this.condition = condition;
	}


	@Override
	public String displayGoal(int nesting, Dungeon d){
		String output = "";
		boolean first = true;
		for(Goal subgoals : goals){
			if(first){
				first = false;
			}
			else{
				for(int iterator = 0; iterator < nesting; iterator++)
				{
					output = output + "   "; 
				}
				output = output + condition +"\n";
				if(subgoals instanceof CompositeGoal)
				{
					output = output + "\n";
				}
			}

			
			
			for(int iterator = 0; iterator < nesting; iterator++)
			{
				output = output + "   "; 
			}

			output = output + subgoals.displayGoal(nesting + 1, d);

			

			output = output + "\n";
			
		}
		return output;
	}


	/**
	 * Method to check if goal(s) are completed
	 */
	@Override
	public boolean isDone() {
		if (condition == "AND") {
			for (Goal g : goals) {
				if (!g.isDone())
					return false;
			}
			return true;
		} else if (condition == "OR") {
			for (Goal g : goals) {
				if (g.isDone())
					return true;
			}
			return false;
		}
		return false;
	}
	/**
	 * Method to add goals in list
	 * @param g Goal
	 */
	public void addGoal(Goal g) {
		goals.add(g);
	}

	/**
	 * Method to store subgoals of goal in a list
	 * @return list of goals
	 */
	public ArrayList<Goal> subGoals() {
		ArrayList<Goal> subs = new ArrayList<Goal>();
		for (Goal g : goals) {
			if (g instanceof CompositeGoal) {
				CompositeGoal cmpgoal = (CompositeGoal) g;
				subs.addAll(cmpgoal.subGoals());
			}
			else {
				subs.add(g);
			}
		}
		return subs;
	}
	
}
