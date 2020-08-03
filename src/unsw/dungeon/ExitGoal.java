package unsw.dungeon;

public class ExitGoal implements Goal{

	private Player player;
	
	public ExitGoal(Player player) {
		this.player = player;
	}

	@Override
	public String displayGoal(int nesting, Dungeon d){
		String output = "";
		for(int iterator = 0; iterator < nesting; iterator++)
		{
			output = output + "		"; 
		}
		output = "Unlock and reach the exit";
		return output;
	}

	@Override
	public boolean isDone() {
		return player.isExit();
	}
	
	
	
}