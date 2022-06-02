package nursinghouse;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NursingHouseLC extends NursingHouse
{
    Lock lock=new ReentrantLock();
    Condition fullWaitingRoom=lock.newCondition();
    Condition emptyWaitingRoom= lock.newCondition();
    Condition operationEnded=lock.newCondition();
    Thread currentPatient;
    LinkedList<Thread> waitingPatients=new LinkedList<>();
    int waitingRoomOccupiedSits=0;
    boolean isDoctorOperating=false;

    public void callAndStartOperation() throws InterruptedException{
        lock.lock();
        try{
            while( !canOperate() )
                emptyWaitingRoom.await();
            currentPatient=waitingPatients.pop();
            System.out.println("Start operating patient "+currentPatient.getName());
            isDoctorOperating=true;
        }finally {
            lock.unlock();
        }
    }//callAndStartOperation

    public void endOperation(){
        lock.lock();
        try{
            System.out.println("End operating patient "+currentPatient.getName());
            isDoctorOperating=false;
            operationEnded.signalAll();
        }finally {
            lock.unlock();
        }
    }//endOperation

    public void patientEnters() throws InterruptedException{
        lock.lock();
        try{
            while( !canSitDown() )
                fullWaitingRoom.await();
            waitingRoomOccupiedSits++;
            System.out.println("Patient "+Thread.currentThread().getName()+" joins the room");
            waitingPatients.add(Thread.currentThread());
            emptyWaitingRoom.signal();
        }finally {
            lock.unlock();
        }
    }//patientEnters

    public void patientExits() throws InterruptedException{
        lock.lock();
        try{
            while( !canExit() )
                operationEnded.await();
            waitingRoomOccupiedSits--;
            System.out.println("Patient "+Thread.currentThread().getName()+" leaves the room");
            fullWaitingRoom.signalAll();
        }finally {
            lock.unlock();
        }
    }//patientExits

    private boolean canOperate(){
        return !isDoctorOperating && waitingRoomOccupiedSits>0;
    }//canOperate

    private boolean canSitDown(){
        return waitingRoomOccupiedSits<SITS_NUMBER;
    }//canSitDown

    private boolean canExit(){
        return Thread.currentThread().equals(currentPatient) && !isDoctorOperating;
    }//canExit
}//NursingHouseLC
