package filosofi;

public class Philosopher extends Thread
{
    private final int ID;
    PhilosopherAbstract philosopherTable;

    public Philosopher( int philosopherID, PhilosopherAbstract philosopherTable ){
        ID=philosopherID;
        this.philosopherTable=philosopherTable;
    }

    @Override
    public void run() {
        while( true ){
            //PhilosopherAbstract.think();
            philosopherTable.takeChopsticks(ID);
            //PhilosopherAbstract.eat();
            philosopherTable.leaveChopsticks(ID);
        }
    }//run
}//Philosopher
