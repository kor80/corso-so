package sequenze;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AABB
{
    private static Semaphore aMutex=new Semaphore(2);
    private static Semaphore bMutex=new Semaphore(0);
    private static final int REPETITION=2;

    private static class A extends Thread{
        private static Semaphore counter =new Semaphore(0);
        public void run(){
            try{
                aMutex.acquire();
                System.out.print("A");
                counter.release();
                if( counter.tryAcquire(REPETITION) ) bMutex.release(REPETITION);
            }catch( InterruptedException e ){}
        }//run
    }//A

    private static class B extends Thread{
        public static Semaphore counter=new Semaphore(0);
        public void run(){
            try{
                bMutex.acquire();
                System.out.print("B");
                counter.release();
                if( counter.tryAcquire(REPETITION) ) aMutex.release(REPETITION);
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
}//AABB
