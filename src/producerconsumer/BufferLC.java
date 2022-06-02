package producerconsumer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferLC<T> extends BufferAbstract<T>
{
    ReentrantLock lock;
    Condition emptyBuffer;
    Condition fullBuffer;

    LinkedList<Long> producerQueue;
    LinkedList<Long> consumerQueue;

    private int numberOfItems;

    public BufferLC( int size ){
        super(size);
        lock=new ReentrantLock();
        emptyBuffer=lock.newCondition();
        fullBuffer=lock.newCondition();
        producerQueue=new LinkedList<>();
        consumerQueue=new LinkedList<>();
        numberOfItems=0;
    }

    public void put( T item ){
        lock.lock();
        try {
            producerQueue.add(Thread.currentThread().getId());
            while( !canInsert() )
                fullBuffer.await();
            producerQueue.remove(Thread.currentThread().getId());
            buffer[in]=item;
            in=(in+1)%buffer.length;
            numberOfItems++;
            emptyBuffer.signalAll();
        }catch( InterruptedException e ){}
        finally{ lock.unlock(); }
    }//put

    public T get(){
        T item=null;
        lock.lock();
        try{
            consumerQueue.add(Thread.currentThread().getId());
            while( !canGet() )
                emptyBuffer.await();
            consumerQueue.remove(Thread.currentThread().getId());
            item=buffer[out];
            out=(out+1)%buffer.length;
            numberOfItems--;
            fullBuffer.signalAll();
        }catch( InterruptedException e ){}
        finally{ lock.unlock(); }
        return item;
    }//get

    private boolean canInsert(){
        return numberOfItems<buffer.length && Thread.currentThread().getId()==Collections.min(producerQueue);
    }//canInsert

    private boolean canGet(){
        return numberOfItems>0 && Thread.currentThread().getId()==Collections.min(consumerQueue);
    }//canInsert
}//BufferLC
