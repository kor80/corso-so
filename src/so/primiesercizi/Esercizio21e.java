package so.primiesercizi;

class MyThread extends Thread{
    public MyThread( String name ){
        setName(name);
    }
    public void run(){
        System.out.println(getName()+" "+getState());
    }
}

public class Esercizio21e {
    public static void main(String[] args) throws InterruptedException{
        MyThread t1=new MyThread("T1");
        t1.start();
        t1.join();
        MyThread t2=new MyThread("T2");
        System.out.println(t1.getName()+" "+t1.getState());
        t2.start();
        t2.join();
    }
}//Esercizio21e
