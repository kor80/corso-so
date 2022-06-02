package cablecar;

import java.util.concurrent.TimeUnit;

public class Pilot extends Thread
{
    private CableCar cableCar;
    public Pilot( CableCar cableCar ){
        this.cableCar=cableCar;
    }

    public void run(){
        while( true ){
            try{
                cableCar.startPilot();
                TimeUnit.SECONDS.sleep(5);
                cableCar.stopPilot();
                TimeUnit.SECONDS.sleep(2);
            }catch( InterruptedException e ){ break; }

        }
    }//run
}//Pilot
