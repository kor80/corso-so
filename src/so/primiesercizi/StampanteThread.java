package so.primiesercizi;

class StampanteT extends Thread{
    int da,a;
    public StampanteT( int da, int a ){
        this.da=da;
        this.a=a;
    }

    public void run(){
        for( int i=da; i<=a; ++i )
            System.out.print(i+" ");
        System.out.println();
    }
}//Stampante1

public class StampanteThread {
    public static void main(String[] args) {
        StampanteT s1=new StampanteT(1,10);
        StampanteT s2=new StampanteT(11,20);

        System.out.println("Inizio");
        try{
            s1.start();
            s1.join();
            s2.start();
            s2.join();
        }catch( InterruptedException e ){}
        System.out.println("Fine");
    }
}//StampamteThread
