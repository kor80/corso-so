package barbiereaddormentato;

public abstract class BarberRoomAbstract
{
    protected final int CHAIRS_NUMBER;
    protected int freeChairs;

    public BarberRoomAbstract( int chairsNumber ){
        CHAIRS_NUMBER=chairsNumber;
        freeChairs=chairsNumber;
    }

    public abstract void hairCut();
    public abstract boolean waitForHairCut();
}//BarberRoomAbstract
