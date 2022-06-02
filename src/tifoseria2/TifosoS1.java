package tifoseria2;

public class TifosoS1 extends Thread
{
    public void run(){
        try{
            Autobus.counterS1Mutex.acquire();
            Autobus.s1Fans++;
            Autobus.counterS1Mutex.release();
            Autobus.mutexA.release();
            Autobus.mutexS1.acquire();
            System.out.print("S1");
        }catch( InterruptedException e ){}
    }//run
}//TifosoS1