package cronometro;

import java.util.concurrent.TimeUnit;

public class Cronometro extends Thread
{
    public final int SLEEPING_TIME;

    public Cronometro(){ SLEEPING_TIME=1; }
    public Cronometro( int sleepingTime ){ SLEEPING_TIME=sleepingTime; }

    public void run(){
        int numSec=1;
        while( !isInterrupted() ){
            try{ TimeUnit.SECONDS.sleep(SLEEPING_TIME); }
            catch( InterruptedException e ){}
            System.out.println("\nSecondi trascorsi: "+numSec);
            numSec++;
        }
    }//run
}//Cronometro
