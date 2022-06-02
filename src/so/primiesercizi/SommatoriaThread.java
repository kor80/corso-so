package so.primiesercizi;

class SommaT extends Thread{
    int da,a,somma;
    public SommaT( int da, int a ){
        this.da=da;
        this.a=a;
    }

    public void run(){
        for( int i=da; i<=a; ++i ) somma+=i;
    }

    public int getSomma() throws InterruptedException{
        this.join(); return somma;
    }
}//SommaT

public class SommatoriaThread {
    public static void main(String[] args) throws InterruptedException{
        int da=1;
        int a=100;
        SommaT s1=new SommaT(da,a/2);
        SommaT s2=new SommaT(a/2+1,a);
        s1.start();
        s2.start();
        int somma=s1.getSomma()+s2.getSomma();
        System.out.println("La somma è: "+ somma );
    }
}//SommatoriaThread
