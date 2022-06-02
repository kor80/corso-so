package fishlake;

public abstract class FishLakeAbstract
{
    protected final int MIN_FISH=50;
    protected final int MAX_FISH=200;
    protected final int FISHERMAN_TURN=0;
    protected final int EMPLOYEE_TURN=1;

    protected abstract void startt( int t ) throws InterruptedException;
    protected abstract void stop( int t ) throws InterruptedException;
}//FishLakeAbstract
