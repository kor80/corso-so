package matricecondivisa;

public class MatriceCondivisaNTS implements MatriceCondivisa
{
    private int[][] mat;

    public MatriceCondivisaNTS( int nRow, int nCol ){
        mat=new int[nRow][nCol];
    }

    public void incrementColumn( int columnIndex ){
        for( int i=0; i<mat.length; ++i )
            mat[i][columnIndex]++;
    }//incrementColumn

    public void decrementRow( int rowIndex ){
        for( int j=0; j<mat[0].length; ++j )
            mat[rowIndex][j]--;
    }//decrementRow

    public void printMat(){
        for( int i=0; i<mat.length; ++i ){
            for( int j=0; j<mat[0].length; ++j )
                System.out.print(mat[i][j]+" ");
            System.out.println();
        }
    }//printMat

    public boolean isNull(){
        for( int i=0; i<mat.length; ++i )
            for( int j=0; j<mat[0].length; ++j )
                if( mat[i][j]!=0 ) return false;
        return true;
    }//getMat
}//MatriceCondivisaNTS
