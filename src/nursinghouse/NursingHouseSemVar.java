package nursinghouse;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class NursingHouseSemVar extends NursingHouse
{
    Semaphore waitingRoom=new Semaphore(SITS_NUMBER);
    Semaphore patients=new Semaphore(0,true);
    Semaphore doctor=new Semaphore(N_DOCTORS);
    Semaphore operatingRoom=new Semaphore(N_DOCTORS);
    Semaphore canLeave=new Semaphore(0);
    Semaphore mutex=new Semaphore(1);
    LinkedList<String> queue=new LinkedList<>();
    LinkedList<String> hospitalizedPatientsID=new LinkedList<>();

    public void callAndStartOperation() throws InterruptedException{
        patients.acquire();
        doctor.acquire();
        mutex.acquire();
        hospitalizedPatientsID.add(queue.pop());
        System.out.println("Operating patient "+ hospitalizedPatientsID.getLast()+"..");
        mutex.release();
    }//callAndStartOperation

    public void endOperation() throws InterruptedException{
        mutex.acquire();
        System.out.println("Patient "+hospitalizedPatientsID.pop()+" operation ended");
        mutex.release();
        canLeave.release();
        doctor.release();
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
        operatingRoom.release();
        waitingRoom.release();
        System.out.println("Patient "+Thread.currentThread()+" leaves the room");
    }//patientExits

    public static void main( String[] args ){
        int id=1;
        NursingHouse nursingHouse=new NursingHouseSemVar();
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
}//NursingHouseSemVar
