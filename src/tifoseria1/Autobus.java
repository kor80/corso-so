package tifoseria1;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Autobus extends Thread
{
    public static Semaphore mutexS1=new Semaphore(6);
    public static Semaphore mutexS2=new Semaphore(0);
    public static Semaphore mutexA=new Semaphore(0);

    public void run(){
        while( true ){
            try{
                System.out.println("A");
                mutexA.acquire(6);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("A");
                mutexS2.release(6);
                mutexA.acquire(6);
                TimeUnit.SECONDS.sleep(1);
                mutexS1.release(6);
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
