package filosofi;

import java.util.concurrent.Semaphore;

public class PhilosopherSem extends PhilosopherAbstract
{
    private final int N_PHILOSOPHER;
    private Semaphore mutex=new Semaphore(1);
    private Semaphore[] chopsticks;

    public PhilosopherSem( int nPhilosopher ){
        super();
        setMutex(mutex);
        N_PHILOSOPHER=nPhilosopher;
        chopsticks=new Semaphore[N_PHILOSOPHER];
        for( int i=0; i<N_PHILOSOPHER; ++i )
            chopsticks[i]=new Semaphore(1,true);
    }

    public void takeChopsticks( int philosopherID ){
        int nextChopstick=(philosopherID+1)%N_PHILOSOPHER;
        try{
            if( philosopherID%2==0 ){
                chopsticks[philosopherID].acquire();
                chopsticks[nextChopstick].acquire();
            }
            else{
                chopsticks[nextChopstick].acquire();
                chopsticks[philosopherID].acquire();
            }
            mutex.acquire();
            philosopherGroup.addPhilosopher(philosopherID);
            philosopherCombinations.add(new PhilosopherGroup(philosopherGroup));
            mutex.release();
            System.out.printf("Il filosofo %d inizia a mangiare..%n",philosopherID);
        }catch( InterruptedException e ){ mutex.release(); }
    }//takeChopsticks

    public void leaveChopsticks( int philosopherID ){
        try{
            mutex.acquire();
            philosopherGroup.remove(philosopherID);
            philosopherCombinations.add(new PhilosopherGroup(philosopherGroup));
            mutex.release();
        }catch( InterruptedException e ){ mutex.release(); }

        int nextChopstick=(philosopherID+1)%N_PHILOSOPHER;
        chopsticks[philosopherID].release();
        chopsticks[nextChopstick].release();
        System.out.printf("Il filosofo %d è sazio..%n",philosopherID);
    }//leaveChopsticks
}//PhilosopherSem
