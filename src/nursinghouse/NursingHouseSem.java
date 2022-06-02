package nursinghouse;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

public class NursingHouseSem extends NursingHouse
{
    Semaphore waitingRoom=new Semaphore(SITS_NUMBER);
    Semaphore patients=new Semaphore(0,true);
    Semaphore operatingRoom=new Semaphore(1);
    Semaphore canLeave=new Semaphore(0);
    Semaphore mutex=new Semaphore(1);
    LinkedList<String> queue=new LinkedList<>();
    String hospitalizedPatientID;

    public void callAndStartOperation() throws InterruptedException{
        patients.acquire();
        hospitalizedPatientID=queue.pop();
        System.out.println("Operating patient "+hospitalizedPatientID+"..");
    }//callAndStartOperation

    public void endOperation(){
        System.out.println("Patient "+hospitalizedPatientID+" operation ended");
        canLeave.release();
        operatingRoom.release();
    }//endOperation

    public void patientEnters() throws InterruptedException{
        waitingRoom.acquire();
        System.out.println("Patient "+Thread.currentThread()+" joins the room");
        mutex.acquire();
        queue.add(Thread.currentThread().getName());
        patients.release();
        mutex.release();
        operatingRoom.acquire();
    }//patientEnters

    public void patientExits() throws InterruptedException{
        canLeave.acquire();
        waitingRoom.release();
        System.out.println("Patient "+Thread.currentThread()+" leaves the room");
    }//patientExits
}//NursingHouseSem
