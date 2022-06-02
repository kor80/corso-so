package barbiereaddormentato;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarberRoomLC extends BarberRoomAbstract
{
    Lock lock;
    Condition barber;
    Condition clients;
    boolean isLoungeChairOccupied;

    public BarberRoomLC( int chairsNumber ){
        super(chairsNumber);
        lock=new ReentrantLock(true);
        barber=lock.newCondition();
        clients= lock.newCondition();
    }

    public void hairCut(){
        lock.lock();
        try{
            while( !canCut() )
                barber.await();
            isLoungeChairOccupied=false;
            clients.signalAll();
        }catch( InterruptedException e ){ lock.unlock(); }
        finally{ lock.unlock(); }
    }//hairCut

    public boolean waitForHairCut(){
        lock.lock();
        try{
            if( freeChairs==0 ) return false;
            freeChairs--;
            barber.signal();
            while( !canSit() )
                clients.await();
            isLoungeChairOccupied=true;
            freeChairs++;
            return true;
        }catch( InterruptedException e ){ lock.unlock(); }
        finally{ lock.unlock(); }
        return false;
    }//waitForHairCut

    private boolean canCut(){
        return freeChairs<CHAIRS_NUMBER;
    }//canCut

    private boolean canSit(){
        return !isLoungeChairOccupied;
    }//canSit
}//BarberRoomLC
