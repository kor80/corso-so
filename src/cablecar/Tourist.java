package cablecar;

public class Tourist extends Thread
{
    private CableCar cableCar;
    private int touristType;
    public Tourist( CableCar cableCar, int touristType ){
        this.cableCar=cableCar;
        this.touristType=touristType;
    }

    public void run(){
        try{
            cableCar.touristGoesUp(touristType);
            cableCar.passengers.add(this);
            cableCar.touristGoesDown(touristType);
        }catch( InterruptedException e ){}
    }//run

    public String toString(){
        return "ID: "+getId()+", Tourist Type: "+touristType;
    }//toString
}//Tourist
