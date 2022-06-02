package nursinghouse;

public class Patient extends Thread
{
    NursingHouse nursingHouse;
    public Patient( NursingHouse nursingHouse, int id ){
        super(id+"");
        this.nursingHouse=nursingHouse;
    }
    public void run(){
        try{
            nursingHouse.patientEnters();
            nursingHouse.patientExits();
        }catch( InterruptedException e ){}
    }//run
}//Patient
