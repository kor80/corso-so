package barbiereaddormentato;

import java.util.concurrent.Semaphore;

public class BarberRoomSem extends BarberRoomAbstract
{
    private Semaphore waitingClients;
    private Semaphore mutex;
    private Semaphore loungeChair;

    public BarberRoomSem( int chairsNumber ){
        super(chairsNumber);
        waitingClients=new Semaphore(0);
        mutex=new Semaphore(1);
        loungeChair=new Semaphore(1);
    }

    public void hairCut(){
        try{
            waitingClients.acquire();
            loungeChair.release();
        }catch( InterruptedException e ){}
    }//hairCut

    public boolean waitForHairCut(){
        try{
            mutex.acquire();
            if( freeChairs==0 ){
                mutex.release();
                return false;
            }
            freeChairs--;
            mutex.release();
            waitingClients.release();
            loungeChair.acquire();
            mutex.acquire();
            freeChairs++;
            mutex.release();
            return true;
        }catch( InterruptedException e ){}
        return false;
    }//waitForHairCut
}//BarberRoomSem
