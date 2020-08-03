package unsw.dungeon;

public class Portal extends Entity {

    private Portal linkedto;
    private int pid;

    /**
     * Constructor
     * @param x location
     * @param y location
     * @param pid unique id for a portal
     */
    public Portal(int x, int y, int pid) {
        super(x, y);
        this.pid = pid;
        this.linkedto = null;
    }

    public int getPid(){
        return pid;
    }

    public void linkPortal(Portal p){
        this.linkedto = p;
    }

    public Portal getLinked(){
        return linkedto;
    }

}
