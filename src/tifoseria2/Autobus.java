package tifoseria2;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Autobus extends Thread
{
    public static final int N_FANS=6;
    public static Semaphore mutexS1=new Semaphore(0);
    public static Semaphore mutexS2=new Semaphore(0);
    public static Semaphore mutexA=new Semaphore(0);
    public static int s1Fans=0;
    public static int s2Fans=0;
    public static Semaphore counterS1Mutex=new Semaphore(1);
    public static Semaphore counterS2Mutex=new Semaphore(1);

    public void run(){
        while( true ){
            try{
                System.out.println("A");
                TimeUnit.SECONDS.sleep(1);
                mutexA.acquire(N_FANS*2-1);
                counterS1Mutex.acquire();
                counterS2Mutex.acquire();
                if( s1Fans>=s2Fans ){ mutexS1.release(6); s1Fans-=N_FANS; }
                else{ mutexS2.release(6); s2Fans-=N_FANS; }
                counterS1Mutex.release();
                counterS2Mutex.release();
                TimeUnit.SECONDS.sleep(1);
            }catch( InterruptedException e ){}
        }
    }//run

    public static void main(String[] args) {
        Autobus a=new Autobus();
        a.start();
        while( true ){
            try{
                fansArrival();
                TimeUnit.SECONDS.sleep(1);
            }catch( InterruptedException e ){}
        }
    }//main

    private static void fansArrival(){
        Random rand=new Random();
        TifosoS1 ts1=new TifosoS1();
        TifosoS2 ts2=new TifosoS2();

        int n=rand.nextInt(2);
        if( n==0 ){
            ts1.start();
            ts2.start();
        }
        else{
            ts2.start();
            ts1.start();
        }
    }//fansArrival
}//Autobus
