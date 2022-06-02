package barbiereaddormentato;

import java.util.concurrent.TimeUnit;

public class BarberApp
{
    public static void main(String[] args) {
        final int CHAIRS_NUMBER=5;
        int id=0;
        BarberRoomAbstract barberRoom=new BarberRoomLC(CHAIRS_NUMBER);
        Barber barber=new Barber(barberRoom);
        barber.start();
        while( true ){
            Client c=new Client(id,barberRoom);
            c.start();
            id++;
            try {
                TimeUnit.MILLISECONDS.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }//main
}//BarberApp
