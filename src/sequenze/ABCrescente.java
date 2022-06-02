package sequenze;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ABCrescente
{
    private static Semaphore aMutex=new Semaphore(1);
    private static Semaphore bMutex=new Semaphore(0);
    private static Semaphore counterMutex=new Semaphore(1);
    private static int counter=1;

    private static class A extends Thread{
        public void run(){
            try{
                aMutex.acquire();
                System.out.print("A");
                bMutex.release();
            }catch( InterruptedException e ){}
        }//run
    }//A

    private static class B extends Thread{
        public void run(){
            try{
                counterMutex.acquire();
                bMutex.acquire(counter);
                System.out.print("B");
                counter++;
                aMutex.release(counter);
                counterMutex.release();
            }catch( InterruptedException e ){}
        }//run
    }//B

    public static void main(String[] args) throws InterruptedException{
        while( true ){
            A a=new A();
            a.start();
            B b=new B();
            b.start();
            TimeUnit.SECONDS.sleep(1);
        }
    }//main
}//ABCrescente
