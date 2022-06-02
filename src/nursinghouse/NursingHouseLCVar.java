package nursinghouse;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 * Se potessi modificherei la classe Doctor, aggiungendo un campo dedicato al paziente corrente
 * per poi sfruttarlo nei vari metodi come da commenti.
 */

public class NursingHouseLCVar extends NursingHouse
{
    Lock lock=new ReentrantLock();
    Condition fullWaitingRoom=lock.newCondition();
    Condition emptyWaitingRoom= lock.newCondition();
    Condition operationEnded=lock.newCondition();
    LinkedList<Thread> operatedPatients=new LinkedList<>();
    LinkedList<Thread> waitingPatients=new LinkedList<>();
    int waitingRoomOccupiedSits=0;
    int nBusyDoctors=0;

    public void callAndStartOperation() throws InterruptedException{
        lock.lock();
        try{
            while( !canOperate() )
                emptyWaitingRoom.await();
            //farei un cast di current thread a doctor(con le dovute accortezze) e chiamerei il metodo setPatient su di esso,
            // rimuovendolo con una pop dalla lista waiting patient
            System.out.println("Start operating patient "+waitingPatients.pop().getName());
            nBusyDoctors++;
        }finally {
            lock.unlock();
        }
    }//callAndStartOperation

    public void endOperation(){
        lock.lock();
        try{
            nBusyDoctors--;
            //operatedPatients.add(doctor.getPatient()) dopo il casting
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
        return nBusyDoctors<N_DOCTORS && waitingRoomOccupiedSits>0;
    }//canOperate

    private boolean canSitDown(){
        return waitingRoomOccupiedSits<SITS_NUMBER;
    }//canSitDown

    private boolean canExit(){
        return operatedPatients.contains(Thread.currentThread());
    }//canExit

    public static void main( String[] args ){
        int id=1;
        NursingHouse nursingHouse=new NursingHouseLCVar();
        for( int i=0; i< nursingHouse.N_DOCTORS; ++i ){
            Doctor d=new Doctor(nursingHouse);
            d.setDaemon(true);
            d.start();
        }
        try{
            while( true ){
                Patient p=new Patient(nursingHouse,id);
                p.start();
                id++;
                TimeUnit.SECONDS.sleep(1);
            }
        }catch( InterruptedException e ){}
    }//main
}//NursingHouseLCVar
