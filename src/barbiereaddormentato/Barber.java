package barbiereaddormentato;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Barber extends Thread
{
    private final int MIN_CUTTING_TIME=1;
    private final int MAX_CUTTING_TIME=3;
    private Random random=new Random();
    private BarberRoomAbstract barberRoom;

    public Barber( BarberRoomAbstract barberRoom ){
        this.barberRoom=barberRoom;
    }

    public void run(){
        while( true ){
            barberRoom.hairCut();
            System.out.println("Cutting..");
            cut();
        }
    }//run

    private void cut(){
        try{
            TimeUnit.SECONDS.sleep(random.nextInt(MAX_CUTTING_TIME-MIN_CUTTING_TIME+1)+MIN_CUTTING_TIME);
        }catch( InterruptedException e ){}
    }//cut
}//Barber
