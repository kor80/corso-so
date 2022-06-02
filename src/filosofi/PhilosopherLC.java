package filosofi;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherLC extends PhilosopherAbstract
{
    private final int N_CHOPSTICKS;
    private final int MAX_WAITING_TURNS=5;

    Lock lock;
    Condition needChopsticks;
    boolean[] busyChopsticks;
    HashMap<Integer,Integer> waitingTurns;

    public PhilosopherLC( int N_PHILOSOPHER ){
        super();
        N_CHOPSTICKS=N_PHILOSOPHER;
        lock=new ReentrantLock(true);
        needChopsticks=lock.newCondition();
        busyChopsticks=new boolean[N_CHOPSTICKS];
        waitingTurns=new HashMap<>();
        for( int i=0; i<N_CHOPSTICKS; ++i ) waitingTurns.put(i,0);
    }

    public void takeChopsticks( int philosopherID ){
        lock.lock();
        try{
            int nextChopstickID=(philosopherID+1)%N_CHOPSTICKS;
            while( !canGetChopsticks(philosopherID) ){
                updateWaitingTurn( philosopherID );
                needChopsticks.await();
            }
            waitingTurns.put(philosopherID,0);
            busyChopsticks[philosopherID]=true;
            busyChopsticks[nextChopstickID]=true;
            System.out.println("Il filosofo "+philosopherID+" inizia a mangiare");
        }catch( InterruptedException e ){ lock.unlock(); }
        finally{ lock.unlock(); }
    }//takeChopsticks

    public void leaveChopsticks( int philosopherID ){
        lock.lock();
        try{
            int nextChopstickID=(philosopherID+1)%N_CHOPSTICKS;
            busyChopsticks[philosopherID]=false;
            busyChopsticks[nextChopstickID]=false;
            needChopsticks.signalAll();
            System.out.println("Il filosofo "+philosopherID+" smette di mangiare");
        }finally{ lock.unlock(); }
    }//takeChopsticks

    private boolean canGetChopsticks( int philosopherID ){
        int nextChopstickID=(philosopherID+1)%N_CHOPSTICKS;
        int maxWaitingTurn= Collections.max(waitingTurns.values());
        boolean isWaitingForTooLong=maxWaitingTurn>=MAX_WAITING_TURNS ? waitingTurns.get(philosopherID)==maxWaitingTurn : true;
        return !busyChopsticks[philosopherID] && !busyChopsticks[nextChopstickID]
                && isWaitingForTooLong;
    }//canGetChopsticks

    private void updateWaitingTurn( int philosopherID ){
        int oldWaitingTurns=waitingTurns.get(philosopherID);
        waitingTurns.put(philosopherID,oldWaitingTurns+1);
    }//updateWaitingTurn
}//PhilosopherLC
