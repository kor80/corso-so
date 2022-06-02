package filosofi;

public class PhilosopherApp
{
    public static int N_PHILOSOPHER=5;

    public static void main(String[] args) {
        PhilosopherAbstract philosopherTable=new PhilosopherLC(N_PHILOSOPHER);
        for( int i=0; i<N_PHILOSOPHER; ++i ){
            Philosopher p=new Philosopher(i,philosopherTable);
            p.start();
        }
    }//main
}//PhilosopherApp
