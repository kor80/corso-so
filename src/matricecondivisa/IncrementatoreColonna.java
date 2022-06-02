package matricecondivisa;

public class IncrementatoreColonna extends Thread
{
    private final int N_ACTIONS;
    private final int COLUMN_INDEX;
    private MatriceCondivisa mat;

    public IncrementatoreColonna( int nActions, int columnIndex, MatriceCondivisa mat ){
        this.N_ACTIONS=nActions;
        this.COLUMN_INDEX=columnIndex;
        this.mat=mat;
    }

    @Override
    public void run(){
        for( int i=0; i<N_ACTIONS; ++i )
            mat.incrementColumn( COLUMN_INDEX );
    }//run
}//IncrementatoreColonna
