package tifoseria1;

public class TifosoS2 extends Thread
{
    public void run(){
        try {
            Autobus.mutexS2.acquire();
            System.out.print("S2");
            Autobus.mutexA.release();
        } catch (InterruptedException e) {}
    }//run
}//TifosoS2
