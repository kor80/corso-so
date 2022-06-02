package barbiereaddormentato;

public class Client extends Thread
{
    private final int ID;
    private BarberRoomAbstract barberRoom;

    public Client( int id, BarberRoomAbstract barberRoom ){
        ID=id;
        this.barberRoom=barberRoom;
    }

    public void run(){
        System.out.println("Il cliente "+ID+" vuole tagliare i capelli");
        boolean heSucceeded=barberRoom.waitForHairCut();
        if( heSucceeded )
            System.out.println("Il cliente "+ID+" è riuscito a tagliare i capelli");
        else
            System.out.println("Il cliente "+ID+" non è riuscito a tagliare i capelli");
    }//run
}//Client
