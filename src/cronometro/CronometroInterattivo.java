package cronometro;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CronometroInterattivo extends Thread
{
    private final int SLEEPING_TIME;
    private long msElapsed=0;
    private Semaphore runningMutex;

    public CronometroInterattivo(){
        SLEEPING_TIME=1;
        runningMutex=new Semaphore(0);
    }

    public CronometroInterattivo( int sleepingTime ){
        SLEEPING_TIME=sleepingTime;
        runningMutex=new Semaphore(0);
    }

    public void run(){
        msElapsed=1;
        while( !isInterrupted() ){
            try{
                runningMutex.acquire();
                TimeUnit.MILLISECONDS.sleep(SLEEPING_TIME);
                //System.out.println("Millisecondi trascorsi: "+msElapsed);
                msElapsed++;
                runningMutex.release();
            }
            catch( InterruptedException e ){ break; }
        }
    }//run

    public void startCount(){
        runningMutex.release();
        System.out.println("Cronometro avviato.");
    }//startCount

    public void stopCount(){
        try{
            runningMutex.acquire();
            System.out.println("Cronometro fermato. Millisecondi trascorsi: "+msElapsed);
        }catch( InterruptedException e ){}
    }//stopCount

    public void restartCount(){
        stopCount();
        msElapsed=1;
        startCount();
        System.out.println("Cronometro riavviato");
    }//restartCount

    public void exitCount(){
        interrupt();
        System.out.print("Cronometro interrotto. ");
        printCount();
    }//exitCount

    public void printCount(){
        System.out.println("Millisecondi trascorsi: "+msElapsed);
    }//printCount
}//CronometroInterattivo
