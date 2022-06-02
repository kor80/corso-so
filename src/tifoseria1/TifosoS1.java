package tifoseria1;

public class TifosoS1 extends Thread
{
    public void run(){
        try{
            Autobus.mutexS1.acquire();
            System.out.print("S1");
            Autobus.mutexA.release();
        }catch( InterruptedException e ){}
    }//run
}//TifosoS1
