package tifoseria2;

public class TifosoS2 extends Thread
{
    public void run(){
        try {
            Autobus.counterS2Mutex.acquire();
            Autobus.s2Fans++;
            Autobus.counterS2Mutex.release();
            Autobus.mutexA.release();
            Autobus.mutexS2.acquire();
            System.out.print("S2");
        } catch (InterruptedException e) {}
    }//run
}//TifosoS2