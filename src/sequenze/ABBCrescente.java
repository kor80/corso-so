package sequenze;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ABBCrescente
{
    private static final int REPETITIONS=2;
    private static Semaphore aMutex=new Semaphore(1);
    private static Semaphore bMutex=new Semaphore(0);

    private static class A extends Thread{
        private static int counter=0;
        private static Semaphore counterMutex=new Semaphore(1);

        public void run(){
            try{
                aMutex.acquire();
                System.out.print("A");
                counterMutex.acquire();
                counter++;
                B.counterMutex.acquire();
                if( counter==B.aCounter ){
                    counter=0;
                    bMutex.release(REPETITIONS);
                }
                B.counterMutex.release();
                counterMutex.release();
            }catch( InterruptedException e ){}
        }//run
    }//A

    private static class B extends Thread{
        private static int bCounter=0;
        public static int aCounter=1;
        public static Semaphore counterMutex=new Semaphore(1);

        public void run(){
            try{
                bMutex.acquire();
                System.out.print("B");
                counterMutex.acquire();
                bCounter++;
                if( bCounter==REPETITIONS ){
                    bCounter=0;
                    aCounter++;
                    aMutex.release(aCounter);
                }
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
}//ABBCrescente
