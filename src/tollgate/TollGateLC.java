package tollgate;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TollGateLC extends TollGateAbstract
{
    Lock l=new ReentrantLock();
    Condition[] waitForFreeGate=new Condition[GATES];
    boolean[] occupiedGates=new boolean[GATES];
    LinkedList<Thread>[] queue=new LinkedList[GATES];

    public TollGateLC(){
        for( int i=0; i<GATES; ++i ){
            queue[i]=new LinkedList<>();
            waitForFreeGate[i]=l.newCondition();
        }
    }

    @Override
    public void accessTheGate(int gate) throws InterruptedException {
        l.lock();
        try{
            while( !canAccessGate(gate) )
                waitForFreeGate[gate].await();
            occupiedGates[gate]=true;
            queue[gate].add(Thread.currentThread());
            System.out.println("Veichle "+Thread.currentThread().getId()+" has accessed gate "+gate);
        }finally{ l.unlock(); }
    }//accessTheGate

    @Override
    public void pay(int kilometers, int gate) throws InterruptedException {
        l.lock();
        try{
            incomes+=kilometers*RATE;
            System.out.println("Veichle "+Thread.currentThread().getId()+" has payd "+kilometers*RATE+" at gate "+gate);
            System.out.println("Total incomes: "+incomes);
            occupiedGates[gate]=false;
            queue[gate].removeFirst();
            waitForFreeGate[gate].signalAll();
        }finally{ l.unlock(); }
    }//pay

    private boolean canAccessGate( int gate ){
        return !occupiedGates[gate] && ( queue[gate].size()==0 || Thread.currentThread()==queue[gate].getFirst() );
    }//canAccessGate

    public static void main(String[] args) {
        TollGateAbstract tollGate=new TollGateLC();
        int vehicles=10;
        for( int i=0; i<vehicles; ++i ){
            Vehicle v=new Vehicle(tollGate);
            v.start();
        }
    }//main
}//TollGateLC
