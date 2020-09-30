public class Wall{
    private int xPosition;
    private int yPosition;
    private Location loc;
    private boolean isDoor;
    private boolean isPortal;

    public Wall(int x, int y, boolean door, boolean portal){
        xPosition = x;
        yPosition = y;
        isDoor = door;
        isPortal = portal;
    }

    public int getX(){
        return xPosition;
    }

    public int getY(){
        return yPosition;
    }

    public Location getLocation(){
        return loc;
    }

    public boolean getIsDoor(){
        return isDoor;
    }

    public boolean getIsPortal(){
        return isPortal;
    }

}