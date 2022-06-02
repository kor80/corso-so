package fishlake;

import java.util.concurrent.Semaphore;

public class FishLakeSem extends FishLakeAbstract
{
    private Semaphore[] turnMutex=new Semaphore[2];
    private Semaphore lakeMutex=new Semaphore(1);
    private Semaphore[] fishOperation=new Semaphore[2];
    private int[] fishRequired=new int[2];
    private int[] numberOfPeopleInside=new int[2];

    public FishLakeSem(){
        fishOperation[FISHERMAN_TURN]=new Semaphore(MAX_FISH-MIN_FISH);
        fishOperation[EMPLOYEE_TURN]=new Semaphore(0);
        fishRequired[FISHERMAN_TURN]=1;
        fishRequired[EMPLOYEE_TURN]=10;
        for( int i=0; i<2; ++i ) turnMutex[i]=new Semaphore(1);
    }

    @Override
    protected void startt(int t) throws InterruptedException{
        fishOperation[t].acquire(fishRequired[t]);
        turnMutex[t].acquire();
        if( numberOfPeopleInside[t]==0 ) lakeMutex.acquire();
        numberOfPeopleInside[t]++;
        turnMutex[t].release();
    }//startt

    @Override
    protected void stop(int t) throws InterruptedException{
        turnMutex[t].acquire();
        numberOfPeopleInside[t]--;
        fishOperation[(t+1)%2].release(fishRequired[t]);
        if( numberOfPeopleInside[t]==0 ) lakeMutex.release();
        turnMutex[t].release();
    }//stop

    public static void main(String[] args) {
        FishLakeAbstract fishLake=new FishLakeSem();
        int nFisherMan=40,nEmployees=5;
        for( int i=0; i<nFisherMan; ++i ){
            Fisherman f=new Fisherman(fishLake);
            f.start();
        }
        for( int i=0; i<nEmployees; ++i ){
            Employee e=new Employee(fishLake);
            e.start();
        }
    }//main
}//FishLakeSem
