package sequenze;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ABABC
{
    private static Semaphore aMutex=new Semaphore(1);
    private static Semaphore bMutex=new Semaphore(0);
    private static Semaphore cMutex=new Semaphore(0);

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
                bMutex.acquire();
                System.out.print("B");
                aMutex.release();
                cMutex.release();
            }catch( InterruptedException e ){}
        }//run
    }//B

    private static class C extends Thread{
        public void run(){
            try{
                cMutex.acquire(2);
                System.out.print("C ");
                aMutex.release();
            }catch( InterruptedException e ){}
        }//run
    }//C

    public static void main(String[] args) throws InterruptedException{
        while( true ){
            A a=new A();
            a.start();
            B b=new B();
            b.start();
            C c=new C();
            c.start();
            TimeUnit.SECONDS.sleep(1);
        }
    }//main
}//ABABC
