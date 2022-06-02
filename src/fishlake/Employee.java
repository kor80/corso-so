package fishlake;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Employee extends Thread
{
    private final int MIN_TIME_TO_RELEASE=300;
    private final int MAX_TIME_TO_RELEASE=600;
    private final int TIME_FOR_GOING_AWAY=3;
    Random random=new Random();
    private FishLakeAbstract fishLake;

    public Employee( FishLakeAbstract fishLake ){
        this.fishLake=fishLake;
    }

    @Override
    public void run() {
        while( true ){
            try{
                fishLake.startt(fishLake.EMPLOYEE_TURN);
                System.out.println("Employee enters");
                waitFor(MAX_TIME_TO_RELEASE,MIN_TIME_TO_RELEASE);
                fishLake.stop(fishLake.EMPLOYEE_TURN);
                System.out.println("Employee exits");
                TimeUnit.SECONDS.sleep(TIME_FOR_GOING_AWAY);
            }catch( InterruptedException e ){ break; }
        }
    }//run

    private void waitFor( int max, int min ) throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(random.nextInt(max-min+1)+min);
    }//waitFor
}//Employee
