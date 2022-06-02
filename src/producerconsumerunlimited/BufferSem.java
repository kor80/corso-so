package producerconsumerunlimited;

import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

public class BufferSem<T> extends BufferAbstract<T>
{
    private Semaphore mutex=new Semaphore(1,true);
    private Semaphore availableItems=new Semaphore(0);

    public BufferSem(){ super(); }

    protected void put( T item ){
        try{
            mutex.acquire();
            buffer.add(item);
            mutex.release();
            availableItems.release();
        }catch( InterruptedException e ){}
    }//produce

    protected T get(){
        try{
            availableItems.acquire();
            mutex.acquire();
            T item=buffer.getLast();
            mutex.release();
            return item;
        }catch( InterruptedException e ){}
        throw new NoSuchElementException();
    }//consume
}//BufferSem
