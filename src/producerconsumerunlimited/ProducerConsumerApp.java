package producerconsumerunlimited;

import java.util.concurrent.TimeUnit;

public class ProducerConsumerApp
{
    public static void main(String[] args) {
        Integer[] itemList={3,1,4,1,5,9,2,6,5,3};
        BufferAbstract<Integer> buffer=new BufferSem<>();
        Producer<Integer> p=new Producer<>(buffer,itemList);
        p.start();
        Consumer<Integer> c=new Consumer<>(buffer);
        c.start();
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch( InterruptedException e ){}
    }//main
}//ProducerConsumerApp
