package fishlake;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FishLakeLC extends FishLakeAbstract
{
    private Lock l=new ReentrantLock();
    private Condition[] canPeopleEnter=new Condition[2];
    private int[] nPeoplesInside=new int[2];
    private int[] fishUsed=new int[2];
    private int nFish=MAX_FISH;

    public FishLakeLC(){
        for( int i=0; i<2; ++i ) canPeopleEnter[i]=l.newCondition();
        fishUsed[FISHERMAN_TURN]=-1;
        fishUsed[EMPLOYEE_TURN]=10;
    }

    @Override
    protected void startt(int t) throws InterruptedException {
        l.lock();
        try{
            while( !canEnter(t) )
                canPeopleEnter[t].await();
            nPeoplesInside[t]++;
        }finally{ l.unlock(); }
    }//startt

    @Override
    protected void stop(int t) throws InterruptedException {
        l.lock();
        try{
            nFish+=fishUsed[t];
            nPeoplesInside[t]--;
            if( nPeoplesInside[t]==0 ) canPeopleEnter[(t+1)%2].signalAll();
        }finally{ l.unlock(); }
    }

    private boolean canEnter( int t ){
        if( nPeoplesInside[(t+1)%2]!=0 ) return false;
        if( t==FISHERMAN_TURN ) return nFish>MIN_FISH;
        else return nFish<=MAX_FISH-10;
    }//canEnter

    public static void main(String[] args) {
        FishLakeAbstract fishLake=new FishLakeLC();
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
}//FishLakeLC
