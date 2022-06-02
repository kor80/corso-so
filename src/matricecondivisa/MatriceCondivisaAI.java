package matricecondivisa;

import java.util.concurrent.atomic.AtomicInteger;

public class MatriceCondivisaAI implements MatriceCondivisa
{
    private AtomicInteger[][] mat;

    public MatriceCondivisaAI( int nRows, int nColumns ){
        mat=new AtomicInteger[nRows][nColumns];
        initializeMat();
    }

    public void incrementColumn( int columnIndex ){
        for( int i=0; i<mat.length; ++i )
            mat[i][columnIndex].incrementAndGet();
    }//incrementColumn

    public void decrementRow( int rowIndex ){
        for( int j=0; j<mat[0].length; ++j )
            mat[rowIndex][j].decrementAndGet();
    }//decrementRow

    public void printMat(){
        for( int i=0; i<mat.length; ++i ){
            for( int j=0; j<mat[0].length; ++j )
                System.out.print(mat[i][j].get()+" ");
            System.out.println();
        }
    }//printMat

    private void initializeMat(){
        for( int i=0; i<mat.length; ++i )
            for( int j=0; j<mat[0].length; ++j )
                mat[i][j]=new AtomicInteger(0);
    }//initializeMat
}//MatriceCondivisaAI
