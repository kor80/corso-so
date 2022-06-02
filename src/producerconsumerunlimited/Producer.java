package producerconsumerunlimited;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Producer<T> extends Thread
{
    private final int MIN_PRODUCING_TIME=1;
    private final int MAX_PRODUCING_TIME=3;
    private Random random=new Random();

    private BufferAbstract<T> buffer;
    private T[] itemsList;

    public Producer( BufferAbstract<T> buffer, T[] itemList ){
        this.buffer=buffer;
        this.itemsList=itemList; //volontario
    }

    public void run(){
        while( true ){
            T item=produce();
            buffer.put(item);
        }
    }//run

    private T produce(){
        int itemIndexToProduce=random.nextInt(itemsList.length);
        try{
            TimeUnit.SECONDS.sleep(random.nextInt(MAX_PRODUCING_TIME-MIN_PRODUCING_TIME+1)+MIN_PRODUCING_TIME);
        }catch( InterruptedException e ){}
        System.out.println("Prodotto "+itemsList[itemIndexToProduce]);
        return itemsList[itemIndexToProduce];
    }//produce
}//Producer
