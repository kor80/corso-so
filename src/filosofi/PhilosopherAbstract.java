package filosofi;

import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public abstract class PhilosopherAbstract
{
    private static final int MAX_TIME_TO_EAT=3;
    private static final int MIN_TIME_TO_EAT=1;
    private static final int MAX_TIME_TO_THINK=3;
    private static final int MIN_TIME_TO_THINK=1;
    private static Random randomGen=new Random();
    private Semaphore mutex;

    protected HashSet<PhilosopherGroup> philosopherCombinations=new HashSet<>();
    protected PhilosopherGroup philosopherGroup=new PhilosopherGroup();

    public PhilosopherAbstract(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("------------------------------");
                try{
                    if( mutex==null ) return;
                    mutex.acquire();
                    for( PhilosopherGroup combination : philosopherCombinations )
                        System.out.println(combination);
                    System.out.println("Sono state trovate "+philosopherCombinations.size()+" combinazioni");
                    System.out.println("------------------------------");
                }catch( InterruptedException e ){}
            }
        });
    }

    public abstract void takeChopsticks ( int philosopherID );
    public abstract void leaveChopsticks( int philosopherID );

    public static void eat(){
        waitFor( MAX_TIME_TO_EAT,MIN_TIME_TO_EAT );
    }//mangia
    public static void think(){
        waitFor( MAX_TIME_TO_THINK,MIN_TIME_TO_THINK );
    }//pensa

    private static void waitFor(int max, int min ){
        try{
            TimeUnit.SECONDS.sleep( randomGen.nextInt(max-min+1)+min );
        }catch( InterruptedException e ){}
    }//attendi

    protected void setMutex( Semaphore mutex ){ this.mutex=mutex; }

}//PhilosopherAbstract
