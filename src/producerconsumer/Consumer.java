package producerconsumer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Consumer<T> extends Thread
{
    private final int MIN_CONSUMING_TIME=1;
    private final int MAX_CONSUMING_TIME=3;
    private final Random random=new Random();
    private BufferAbstract<T> buffer;

    public Consumer( BufferAbstract<T> buffer ){
        this.buffer=buffer; //volontario
    }

    public void run(){
        while( true ){
            T item=buffer.get();
            consume(item);
        }
    }//run

    private void consume( T item ){
        System.out.println("Consumando "+item);
        try{
            TimeUnit.SECONDS.sleep(random.nextInt(MAX_CONSUMING_TIME-MIN_CONSUMING_TIME+1)+MAX_CONSUMING_TIME);
        }catch( InterruptedException e ){}
    }
}//Consumer
