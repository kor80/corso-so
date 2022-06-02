package atleti;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Atleta extends Thread
{
    private static final int N=10;
    private static Semaphore[] semaphores=new Semaphore[N];
    private int number;

    public Atleta( int number ){
        this.number=number;
    }

    @Override
    public void run() {
        try{
            if( number>0 ) semaphores[number-1].acquire();
            System.out.println("Sta correndo l'atleta "+number);
            TimeUnit.SECONDS.sleep(1);
            semaphores[number].release();
        }catch( InterruptedException e ){}
    }//run

    private static void initializeSemaphores(){ //potrei anche passargli il puntatore facendo l'init nel main
        for( int i=0; i<N; ++i ) semaphores[i]=new Semaphore(0);
    }//initializeSemaphores

    public static void main(String[] args) {
        Atleta.initializeSemaphores();
        for( int i=0; i<N; ++i ){
            Atleta a=new Atleta(i);
            a.start();
        }
    }//main
}//Atleta
