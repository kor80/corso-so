package so.primiesercizi;

import java.util.Iterator;
import java.util.LinkedList;

public class PuntiDiSellaT2
{
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

    static class MaxRigaT<T extends Comparable<? super T>> extends Thread{
        private int row;
        private T[][] mat;
        private T max;
        private LinkedList<Punto<T>> puntiSella;

        public MaxRigaT( T[][] mat, int row ){
            if( mat[0].length<2 ) throw new IllegalArgumentException();
            puntiSella=new LinkedList<>();
            this.mat=mat;
            this.row=row;
        }

        private void cercaPuntiSella() throws InterruptedException{
            max=mat[row][0];
            for( int i=1; i<mat[0].length; ++i )
                if( mat[row][i].compareTo(max)>0 )
                    max=mat[row][i];

            for( int i=1; i<mat[0].length; ++i )
                if( mat[row][i].compareTo(max)==0 ){
                    MinColT<T> ricercatoreMinimi=new MinColT<>(mat,i);
                    ricercatoreMinimi.start();
                    for( Punto<T> min : ricercatoreMinimi.getMinimi() )
                        if( min.equals(new Punto<>(row,i,max)) ){
                            puntiSella.add(new Punto<>(row,i,max));}
                }
        }//findMax

        @Override
        public void run() {
            try{
                cercaPuntiSella();
            }catch( InterruptedException e ){}
        }

        public LinkedList<Punto<T>> getPuntiSella() throws InterruptedException{
            this.join();
            return puntiSella;
        }
    }//MaxRigaT

    static class MinColT<T extends Comparable<? super T>> extends Thread{
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

        public LinkedList<Punto<T>> getMinimi() throws InterruptedException{ this.join(); return minimi; }
    }//MaxRigaT

    public static void main(String[] args) throws InterruptedException{
        final long startTime=System.currentTimeMillis();
        LinkedList<Punto<Integer>> puntiSella=new LinkedList<>();
        Integer[][] mat={ {2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2},
                          {2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2},
                          {1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1},
                          {2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2},
                          {2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1},
                {2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2},
                {2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2},
                {1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1},
                {2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2},
                {2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1},
                {2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2},
                {2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2},
                {1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1},
                {2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2},
                {2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1},
                {2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2},
                {2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2},
                {1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1},
                {2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2},
                {2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1},
                {2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2,2,7,2,5,2},
                {2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2,2,5,9,7,2},
                {1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1,1,4,1,4,1},
                {2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2,2,5,2,9,2},
                {2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1,2,4,1,4,1} };

        for( int row=0; row<mat.length; ++row ){
            MaxRigaT<Integer> t=new MaxRigaT<>(mat,row);
            t.start();
            puntiSella.addAll(t.getPuntiSella());
        }
        System.out.println(puntiSella);
        System.out.println("Tempo di esecuzione: "+( System.currentTimeMillis()-startTime )+"ms" );
    }//main
}//PuntiDiSellaT

