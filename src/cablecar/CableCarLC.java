package cablecar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CableCarLC extends CableCar
{
    Lock lock=new ReentrantLock();
    int turn=WALKING_TOURIST;
    int[] nTourist=new int[touristCodes.length];
    boolean[] canGoDown=new boolean[touristCodes.length];
    Condition[] fullCableCar=new Condition[touristCodes.length];
    Condition[] emptyCableCar=new Condition[touristCodes.length];
    Condition readyCableCar=lock.newCondition();

    public CableCarLC(){
        for( int i=0; i<touristCodes.length; ++i ){
            fullCableCar[i]=lock.newCondition();
            emptyCableCar[i]=lock.newCondition();
        }
    }

    public void startPilot() throws InterruptedException{
        lock.lock();
        try{
            while( !canCableCarGoUp() )
                readyCableCar.await();
        }finally {
            lock.unlock();
        }
    }//startPilot

    public void stopPilot() throws InterruptedException{
        lock.lock();
        try{
            for( Tourist t : passengers ) System.out.println(t);
            passengers.clear();
            canGoDown[turn]=true;
            emptyCableCar[turn].signalAll();
        }finally {
            lock.unlock();
        }
    }//stopPilot

    public void touristGoesUp( int touristType ) throws InterruptedException{
        lock.lock();
        try{
            while( !canGoUp(touristType) )
                fullCableCar[touristType].await();
            nTourist[touristType]++;
            if( nTourist[touristType]==MAX_TOURISTS[touristType] ){
                readyCableCar.signal();
            }
        }finally {
            lock.unlock();
        }
    }//touristGoesUp

    public void touristGoesDown( int touristType ) throws InterruptedException{
        lock.lock();
        try{
            while( !canGoDown(touristType) )
                emptyCableCar[touristType].await();
            nTourist[touristType]--;
            if( nTourist[touristType]==0 ){
                canGoDown[touristType]=false;
                turn = (touristType+1)%touristCodes.length;
                fullCableCar[turn].signalAll();
            }
        }finally {
            lock.unlock();
        }
    }//touristGoesDown

    private boolean canCableCarGoUp(){
        return nTourist[turn]==MAX_TOURISTS[turn];
    }//canCableCarGoUp

    private boolean canGoUp( int touristType ){
        return touristType==turn && nTourist[touristType]<MAX_TOURISTS[touristType];
    }//canGoUp

    private boolean canGoDown( int touristType ){
        return canGoDown[touristType];
    }//canGoDown

}//CableCarLC
