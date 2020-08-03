package unsw.dungeon;

public interface Goal {
    
    public String displayGoal(int nesting, Dungeon d);
    public boolean isDone();
    
}