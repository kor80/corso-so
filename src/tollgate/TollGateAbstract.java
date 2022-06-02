package tollgate;

public abstract class TollGateAbstract
{
    protected final int GATES=3;
    protected final int RATE=10;
    protected int incomes=0;

    public abstract void accessTheGate( int gate ) throws InterruptedException;
    public abstract void pay( int kilometers, int gate ) throws InterruptedException;

}//TollGateAbstract
