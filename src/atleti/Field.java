package atleti;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Field
{
    private boolean[] isMyTurn;
    Lock lock;
    Condition waitForTurn;

    public Field( int athletesNumber ){
        isMyTurn=new boolean[athletesNumber];
        isMyTurn[0]=true;
        lock=new ReentrantLock();
        waitForTurn=lock.newCondition();
    }

    public void tryRun( int id ){
        lock.lock();
        try{
            while( !canRun(id) )
                waitForTurn.await();
            System.out.println("Thread "+id+" is running..");
            isMyTurn[id]=false;
            if( id+1<isMyTurn.length ) isMyTurn[id+1]=true;
            waitForTurn.signalAll();

        }catch( InterruptedException e ){ lock.unlock(); }
        finally { lock.unlock(); }
    }//tryRun

    private boolean canRun( int id ){
        return isMyTurn[id];
    }

    public static void main(String[] args) {
        final int N=100;
        Field f=new Field(N);
        for( int i=0; i<N; ++i ){
            AtletaLC a=new AtletaLC(f,i);
            a.start();
        }
    }//main
}//Field
