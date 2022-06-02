package nursinghouse;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Doctor extends Thread
{
    private final int MAX_OPERATION_TIME=4;
    private final int MIN_OPERATION_TIME=2;
    private final int PREPARATION_TIME=2;
    private Random random=new Random();
    NursingHouse nursingHouse;

    public Doctor( NursingHouse nursingHouse ){
        this.nursingHouse=nursingHouse;
    }

    public void run(){
        while( true ){
            try{
                nursingHouse.callAndStartOperation();
                waitFor(MAX_OPERATION_TIME,MIN_OPERATION_TIME);
                nursingHouse.endOperation();
                TimeUnit.SECONDS.sleep(PREPARATION_TIME);
            }catch( InterruptedException e ){}
        }
    }//run

    private void waitFor( int max, int min ) throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(max-min+1)+min);
    }//waitFor
}//Doctor
