package matricecondivisa;

import java.util.concurrent.Semaphore;

public class MatriceCondivisaS implements MatriceCondivisa
{
    private int mat[][];
    private Semaphore semaphoreMat[][];

    public MatriceCondivisaS( int nRow, int nCol ){
        mat=new int[nRow][nCol];
        semaphoreMat=new Semaphore[nRow][nCol];
        for( int i=0; i<nRow; ++i )
            for( int j=0; j<nCol; ++j )
                semaphoreMat[i][j]=new Semaphore(1);
    }//MatriceCondivisaS


    public void incrementColumn( int columnIndex ){
        int i=0;
        try{
            for( i=0; i<mat.length; ++i ){
                semaphoreMat[i][columnIndex].acquire();
                mat[i][columnIndex]++;
                semaphoreMat[i][columnIndex].release();
            }
        }catch( InterruptedException e ){}
        finally{
            if( i<mat.length )
                if( semaphoreMat[i][columnIndex].availablePermits()<1 )
                    semaphoreMat[i][columnIndex].release();
        }
    }//incrementColumn

    public void decrementRow( int rowIndex ){
        int j=0;
        try{
            for( j=0; j<mat[0].length; ++j ){
                semaphoreMat[rowIndex][j].acquire();
                mat[rowIndex][j]--;
                semaphoreMat[rowIndex][j].release();
            }
        }catch( InterruptedException e ){}
        finally{
            if( j<mat[0].length )
                if( semaphoreMat[rowIndex][j].availablePermits()<1 )
                    semaphoreMat[rowIndex][j].release();
        }
    }//decrementRow

    public void printMat(){
        for( int i=0; i<mat.length; ++i ){
            for( int j=0; j<mat[0].length; ++j )
                System.out.print(mat[i][j]+" ");
            System.out.println();
        }
    }//printMat
}//MatriceCondivisa
