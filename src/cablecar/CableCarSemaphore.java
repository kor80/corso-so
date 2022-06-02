package cablecar;

import java.util.concurrent.Semaphore;

public class CableCarSemaphore extends CableCar
{
    Semaphore[] turn=new Semaphore[touristCodes.length];
    Semaphore[] mutex=new Semaphore[touristCodes.length];
    int[] nTourists={0,0};
    Semaphore sits=new Semaphore(0);
    Semaphore pilot=new Semaphore(0);

    public CableCarSemaphore(){
        turn[0]=new Semaphore(1);
        for( int i=1; i<touristCodes.length; ++i )
            turn[i]=new Semaphore(0);
        for( int i=0; i<touristCodes.length; ++i )
            mutex[i]=new Semaphore(1);
    }

    public void startPilot() throws InterruptedException{
        pilot.acquire();
    }//startPilot

    public void stopPilot(){
        for( Tourist t : passengers ) System.out.println(t);
        passengers.clear();
        sits.release(6);
    }//stopPilot

    public void touristGoesUp( int touristType ) throws InterruptedException{
        turn[touristType].acquire();
        mutex[touristType].acquire();
        nTourists[touristType]++;
        if( nTourists[touristType]==MAX_TOURISTS[touristType] ){
            pilot.release();
            mutex[touristType].release();
            return;
        }
        mutex[touristType].release();
        turn[touristType].release();
    }//touristGoesUp

    public void touristGoesDown( int touristType ) throws InterruptedException{
        sits.acquire(touristType+1);
        mutex[touristType].acquire();
        nTourists[touristType]--;
        if( nTourists[touristType]==0 ){
            turn[(touristType+1)%touristCodes.length].release();
        }
        mutex[touristType].release();
    }//touristGoesDown
}//CableCarSemaphore
