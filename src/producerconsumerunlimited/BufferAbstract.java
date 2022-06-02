package producerconsumerunlimited;

import java.util.LinkedList;

public abstract class BufferAbstract<T>
{
    protected LinkedList<T> buffer;

    public BufferAbstract(){ buffer=new LinkedList<>(); }

    protected abstract void put( T item );
    protected abstract T get();
}//BufferAbstract
