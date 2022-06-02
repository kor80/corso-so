package cablecar;

import java.util.LinkedList;

public abstract class CableCar
{
    protected final int[] MAX_TOURISTS={6,3};
    protected static final int WALKING_TOURIST=0;
    protected static final int TOURIST_ON_BIKE=1;
    protected int[] touristCodes={WALKING_TOURIST,TOURIST_ON_BIKE};
    protected LinkedList<Tourist> passengers=new LinkedList<>();

    public abstract void startPilot() throws InterruptedException;
    public abstract void stopPilot() throws InterruptedException;
    public abstract void touristGoesUp( int touristType ) throws InterruptedException;
    public abstract void touristGoesDown( int touristType ) throws InterruptedException;

    public static void main(String[] args) {
        CableCar cableCarSem=new CableCarLC();
        Pilot pilot=new Pilot(cableCarSem);
        pilot.setDaemon(true);
        pilot.start();
        for( int i=0; i<18; ++i ){
            Tourist t=new Tourist(cableCarSem,WALKING_TOURIST);
            t.start();
        }
        for( int i=0; i<9; ++i ){
            Tourist t=new Tourist(cableCarSem,TOURIST_ON_BIKE);
            t.start();
        }
    }//main
}//CableCar
