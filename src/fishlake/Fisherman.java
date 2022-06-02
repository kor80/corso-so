package fishlake;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Fisherman extends Thread
{
    private final int MIN_TIME_TO_FISH=200;
    private final int MAX_TIME_TO_FISH=800;
    private final int TIME_FOR_GOING_AWAY=1;
    Random random=new Random();
    private FishLakeAbstract fishLake;

    public Fisherman( FishLakeAbstract fishLake ){
        this.fishLake=fishLake;
    }

    @Override
    public void run() {
        while( true ){
            try{
                fishLake.startt(fishLake.FISHERMAN_TURN);
                System.out.println("Fisherman enters");
                waitFor(MAX_TIME_TO_FISH,MIN_TIME_TO_FISH);
                fishLake.stop(fishLake.FISHERMAN_TURN);
                System.out.println("Fisherman exits");
                TimeUnit.SECONDS.sleep(TIME_FOR_GOING_AWAY);
            }catch( InterruptedException e ){ break; }
        }
    }//run

    private void waitFor( int max, int min ) throws InterruptedException{
        TimeUnit.MILLISECONDS.sleep(random.nextInt(max-min+1)+min);
    }//waitFor
}//Fisherman
