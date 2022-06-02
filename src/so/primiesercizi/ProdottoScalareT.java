package so.primiesercizi;

import java.util.ArrayList;
import java.util.Scanner;

public class ProdottoScalareT
{
    private final static int M=8;

    static class ProdottoT extends Thread{
        ArrayList<Integer> vect1,vect2;
        int start,end,sum;

        public ProdottoT( int start, int end, ArrayList<Integer> vect1, ArrayList<Integer> vect2 ){
            this.start=start;
            this.end= Math.min(end,vect1.size());
            this.vect1=vect1;
            this.vect2=vect2;
        }

        public void run(){
            for( int i=start; i<end; ++i )
                sum+=vect1.get(i)*vect2.get(i);
        }

        public int getSum() throws InterruptedException{ this.join(); return sum; }
    }//ProdottoT

    private static void readVect( ArrayList<Integer> vect ){
        Scanner sc=new Scanner(System.in);
        String input;
        System.out.print(">");
        input=sc.nextLine();
        while( !input.equals("") ){
            vect.add(Integer.parseInt(input));
            System.out.print(">");
            input=sc.nextLine();
        }
    }//readVect

    public static void main(String[] args) throws InterruptedException{
        ArrayList<Integer> vect1=new ArrayList<>();
        ArrayList<Integer> vect2=new ArrayList<>();
        int sum=0;

        System.out.println("Inserisci i due vettori. Se non hanno la stessa dimensione dovrai reinserirli da capo");
        do{
            System.out.println("Inserisci le componenti del PRIMO vettore: (premi invio per terminare l'inserimento)");
            readVect(vect1);
            System.out.println("Inserisci le componenti del SECONDO vettore: (premi invio per terminare l'inserimento)");
            readVect(vect2);
        }while( vect1.size()!=vect2.size() );

        int numeroSezioni=(vect1.size()/M)>0 ? (vect1.size()/M) : 1;
        for( int i=0; i<vect1.size(); i+=numeroSezioni ){
            ProdottoT p=new ProdottoT(i,i+numeroSezioni,vect1,vect2);
            p.start();
            sum+=p.getSum();
        }
        System.out.println("Prodotto scalare: "+sum);
    }//main
}//ProdottoScalareT
