package producerconsumer;

import java.util.concurrent.TimeUnit;

public class ProducerConsumerApp
{
    public static void main(String[] args) {
        Integer[] itemList={3,1,4,1,5,9,2,6,5,3};
        final int BUFFER_SIZE=5;
        BufferAbstract<Integer> buffer=new BufferLC<>(BUFFER_SIZE);
        while( true ){
            Producer<Integer> p=new Producer<>(buffer,itemList);
            p.start();
            Consumer<Integer> c=new Consumer<>(buffer);
            c.start();
            try{
                TimeUnit.MILLISECONDS.sleep(300);
            }catch( InterruptedException e ){}
        }
    }//main
}//ProducerConsumerApp
