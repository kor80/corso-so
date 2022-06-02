package producerconsumer;

public abstract class BufferAbstract<T>
{
    protected T[] buffer;
    protected int in;
    protected int out;

    public BufferAbstract( int size ){
        buffer=(T[])new Object[size];
        in=0;
        out=0;
    }

    protected abstract void put( T item );
    protected abstract T get();
}//BufferAbstract
