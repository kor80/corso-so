package so.primiesercizi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class PuntiDiSellaT
{
    interface Trovatore<T extends Comparable<? super T>>{
        public LinkedList<Punto<T>> getRisultati();
    }//Trovatore

    static class TempiEsecuzione{
        public final long MAX,MIN,AVG;

        public TempiEsecuzione( long max, long min, long avg ){
            this.MAX=max;
            this.MIN=min;
            this.AVG=avg;
        }
    }

    static class Punto<T extends Comparable<? super T>>{
        public int x,y;
        private T info;

        public Punto( int x, int y, T info ){
            this.x=x;
            this.y=y;
            this.info=info;
        }

        public boolean equals( Object o ){
            if( this==o ) return true;
            if( !(o instanceof Punto) ) return false;
            Punto<T> p=(Punto)o;
            if( this.x!=p.x ) return false;
            if( this.y!=p.y ) return false;
            return this.info.compareTo(p.info)==0;
        }//equals

        public String toString(){
            return "Elemento "+info.toString()+" in coordinate ("+x+","+y+")";
        }//toString
    }//Punto

    static class MaxRigaT<T extends Comparable<? super T>> extends Thread implements Trovatore<T>{
        private int row;
        private T[] matRow;
        private T max;
        private LinkedList<Punto<T>> massimi;

        public MaxRigaT( T[][] mat, int row ){
            if( mat[0].length<2 ) throw new IllegalArgumentException();
            massimi=new LinkedList<>();
            this.matRow=mat[row];
            this.row=row;
        }

        private void findMax(){
            max=matRow[0];
            for( int i=1; i<matRow.length; ++i )
                if( matRow[i].compareTo(max)>0 )
                    max=matRow[i];

            for( int i=1; i<matRow.length; ++i )
                if( matRow[i].compareTo(max)==0 )
                    massimi.add(new Punto<>(row,i,max));
        }//findMax

        @Override
        public void run(){ findMax(); }

        public LinkedList<Punto<T>> getRisultati() {
            try{
                this.join();
            }catch( InterruptedException e ){}
            return massimi;
        }//getRisultati

    }//MaxRigaT

    static class MinColT<T extends Comparable<? super T>> extends Thread implements Trovatore<T>{
        private int col;
        private T min;
        private T[][] mat;
        private LinkedList<Punto<T>> minimi;

        public MinColT( T[][] mat, int col ){
            if( mat.length<2 ) throw new IllegalArgumentException();
            minimi=new LinkedList<>();
            this.col=col;
            this.mat=mat;
        }

        private void findMin(){
            min=mat[0][col];
            for( int i=1; i<mat.length; ++i )
                if( mat[i][col].compareTo(min)<0 )
                    min=mat[i][col];

            for( int i=0; i<mat.length; ++i )
                if( mat[i][col].compareTo(min)==0 )
                    minimi.add(new Punto<>(i,col,min));
        }//findMax

        @Override
        public void run(){ findMin(); }

        public LinkedList<Punto<T>> getRisultati(){
            try{
                this.join();
            }catch( InterruptedException e ){}
            return minimi;
        }//getRisultati
    }//MaxRigaT

    public static void main(String[] args) throws InterruptedException {
        final int N_TEST=1;
        final int RIGHE_MATRICE=4;
        final int COLONNE_MATRICE=5;
        final int VALORE_MASSIMO=9;
        LinkedList<Punto<Integer>> puntiSella=new LinkedList<>();
        long[] tempiEsecuzione=new long[N_TEST];
        Integer[][] mat={ {2,7,2,5,2},
                          {2,5,9,7,2},
                          {1,4,1,4,1},
                          {2,5,2,9,2} };

        for( int i=0; i<N_TEST; ++i ){
            //mat=generaMatriceQuadrata(RIGHE_MATRICE,COLONNE_MATRICE,VALORE_MASSIMO);
            long tempoInizio=System.currentTimeMillis();
            puntiSella=testAlgoritmo(mat,true);
            if( puntiSella.size()>1 )
                tempiEsecuzione[i]=System.currentTimeMillis()-tempoInizio;
        }

        stampaPuntiSella(puntiSella);
        stampaTempiEsecuzione(tempiEsecuzione,RIGHE_MATRICE,COLONNE_MATRICE,N_TEST,VALORE_MASSIMO);
    }//main

    private static void stampaPuntiSella( LinkedList<Punto<Integer>> puntiSella ){
        System.out.println();
        System.out.println("Punti di sella:");
        for( Punto<Integer> p : puntiSella )
            System.out.println(p);
    }//stampaPuntiSella

    private static void stampaTempiEsecuzione( long[] tempiEsecuzione, int righe, int col, int nTest, int maxVal ){
        System.out.println();
        TempiEsecuzione tempoEsecuzione=calcolaTempi(tempiEsecuzione);
        System.out.println("Con una matrice "+righe+"x"+col+
                ", con "+nTest+" test effettuati ed un valore massimo di "+maxVal+": ");
        System.out.println("Tempo esecuzione massimo: "+ tempoEsecuzione.MAX+"ms");
        System.out.println("Tempo esecuzione minimo: "+ tempoEsecuzione.MIN+"ms");
        System.out.println("Tempo esecuzione medio: "+ tempoEsecuzione.AVG+"ms");
    }//stampaTempiEsecuzione

    private static TempiEsecuzione calcolaTempi( long[] tempiEsecuzione ){
        long max=tempiEsecuzione[0];
        long min=tempiEsecuzione[0];
        long avg=0;
        for( long el : tempiEsecuzione ){
            if( el>max ) max=el;
            if( el<min ) min=el;
            avg+=el;
        }
        avg/=tempiEsecuzione.length;
        return new TempiEsecuzione(max,min,avg);
    }//calcolaTempi

    private static Integer[][] generaMatrice( int righe, int col, int upperBound ){
        Random generatore=new Random();
        Integer[][] mat=new Integer[righe][col];
        for( int i=0; i<righe; ++i )
            for( int j=0; j<col; ++j )
                mat[i][j]=generatore.nextInt(upperBound);
        return mat;
    }//generaMatriceQuadrata

    private static void stampaMatrice( Integer[][] mat ){
        for( int i=0; i<mat.length; ++i ){
            for( int j=0; j<mat[0].length; ++j )
                System.out.print(mat[i][j]+" ");
            System.out.println();
        }
    }//stampaMatrice

    private static <T extends Comparable<? super T>> void ottieniERiempi( LinkedList<Trovatore<T>> threads, LinkedList<Punto<T>> dest ){
        for( Trovatore<T> t : threads )
            dest.addAll(t.getRisultati());
    }//ottieniERiempi

    private static LinkedList<Punto<Integer>> testAlgoritmo( Integer[][] mat, boolean mostraMatrice ) throws InterruptedException{
        LinkedList<Punto<Integer>> puntiMassimo=new LinkedList<>();
        LinkedList<Punto<Integer>> puntiMinimo=new LinkedList<>();
        LinkedList<Punto<Integer>> puntiSella=new LinkedList<>();
        LinkedList<Trovatore<Integer>> threadsMassimi=new LinkedList<>();
        LinkedList<Trovatore<Integer>> threadsMinimi=new LinkedList<>();

        if( mostraMatrice ) stampaMatrice(mat);

        for( int row=0; row<mat.length; ++row ){
            MaxRigaT<Integer> t=new MaxRigaT<>(mat,row);
            t.start();
            threadsMassimi.add(t);
        }
        for( int col=0; col<mat[0].length; ++col ){
            MinColT<Integer> t=new MinColT<>(mat,col);
            t.start();
            threadsMinimi.add(t);
        }

        ottieniERiempi(threadsMassimi,puntiMassimo);
        ottieniERiempi(threadsMinimi,puntiMinimo);

        Iterator<Punto<Integer>> itMax=puntiMassimo.iterator();
        Iterator<Punto<Integer>> itMin;
        while( itMax.hasNext() ){
            Punto<Integer> puntoMax=itMax.next();
            itMin=puntiMinimo.iterator();
            while( itMin.hasNext() ){
                Punto<Integer> puntoMin=itMin.next();
                if( puntoMax.equals(puntoMin) ){
                    itMin.remove();
                    puntiSella.add(puntoMax);
                }
            }
        }
        return puntiSella;
    }//testAlgoritmo
}//PuntiDiSellaT
