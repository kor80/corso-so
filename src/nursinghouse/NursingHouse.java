package nursinghouse;

import java.util.concurrent.TimeUnit;

public abstract class NursingHouse
{
    protected final int SITS_NUMBER=3;
    protected final int N_DOCTORS=3;

    public abstract void callAndStartOperation() throws InterruptedException;
    public abstract void endOperation() throws InterruptedException;
    public abstract void patientEnters() throws InterruptedException;
    public abstract void patientExits() throws InterruptedException;

    public static void main( String[] args ){
        int id=1;
        NursingHouse nursingHouse=new NursingHouseSem();
        Doctor d=new Doctor(nursingHouse);
        d.start();
        try{
            while( true ){
                Patient p=new Patient(nursingHouse,id);
                p.start();
                id++;
                TimeUnit.SECONDS.sleep(1);
            }
        }catch( InterruptedException e ){}
    }//main
}//NursingHouse
