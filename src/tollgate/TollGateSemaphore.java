package tollgate;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class TollGateSemaphore extends TollGateAbstract
{
    Semaphore[] gates=new Semaphore[GATES];
    Semaphore paymentMutex=new Semaphore(1);

    public TollGateSemaphore(){
       for( int i=0; i<GATES; ++i ) gates[i]=new Semaphore(1,true);
    }

    @Override
    public void accessTheGate( int gate ) throws InterruptedException{
        gates[gate].acquire();
        System.out.println("Veichle "+Thread.currentThread().getId()+" has accessed gate "+gate);
    }//chooseGate

    @Override
    public void pay( int kilometers, int gate ) throws InterruptedException{
        paymentMutex.acquire();
        incomes+=kilometers*RATE;
        System.out.println("Veichle "+Thread.currentThread().getId()+" has payd "+kilometers*RATE+" at gate "+gate);
        System.out.println("Total incomes: "+incomes);
        paymentMutex.release();
        gates[gate].release();
    }//pay

    public static void main(String[] args) {
        LinkedList<Vehicle> ll=new LinkedList<>();
        TollGateAbstract tollGate=new TollGateSemaphore();
        int vehicles=10;
        for( int i=0; i<vehicles; ++i ){
            Vehicle v=new Vehicle(tollGate);
            ll.add(v);
            v.start();
        }
        try{
            for( Vehicle v : ll ) v.join();
            System.out.println("Incomes: "+tollGate.incomes);
        }catch( InterruptedException e ){}
    }//main
}//TollGateSemaphore
