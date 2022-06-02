package atleti;

public class AtletaLC extends Thread
{
    private Field field;
    private final int ID;

    public AtletaLC( Field field, int id ){
        super();
        this.field=field;
        ID=id;
    }

    public void run(){
        field.tryRun(ID);
    }//run
}//AtletaLC
