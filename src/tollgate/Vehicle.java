package tollgate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Vehicle extends Thread
{
    TollGateAbstract tollGate;
    private final static int MIN_KM=2;
    private final static int MAX_KM=4;
    private final static int MIN_PAY_TIME=3;
    private final static int MAX_PAY_TIME=6;
    private int pathLength=0;
    private int gate=0;
    Random random=new Random();

    public Vehicle( TollGateAbstract tollGate ){
        this.tollGate=tollGate;
    }

    @Override
    public void run(){
        try{
            travel();
            chooseGate();
            tollGate.accessTheGate(gate);
            waitFor(MAX_PAY_TIME,MIN_PAY_TIME);
            tollGate.pay(pathLength,gate);
        }catch( InterruptedException e ){}
    }//run

    private void chooseGate(){
        gate=random.nextInt(tollGate.GATES);
        System.out.println("Veichle "+getId()+" has chosen gate number "+gate);
    }//chooseGate

    private void travel() throws InterruptedException{
        pathLength=random.nextInt(MAX_KM-MIN_KM+1)+MIN_KM;
        TimeUnit.SECONDS.sleep(4*pathLength);
        System.out.println("Veichle "+getId()+" has travelled for "+pathLength+" km");
    }//travel

    private void waitFor( int max, int min ) throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(max-min+1)+min);
    }//travel
}//Vehicle
