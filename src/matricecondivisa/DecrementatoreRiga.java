package matricecondivisa;

public class DecrementatoreRiga extends Thread
{
    private final int N_ACTIONS;
    private final int ROW_INDEX;
    private MatriceCondivisa mat;

    public DecrementatoreRiga( int nActions, int rowIndex, MatriceCondivisa mat ){
        this.N_ACTIONS=nActions;
        this.ROW_INDEX=rowIndex;
        this.mat=mat;
    }

    @Override
    public void run(){
        for( int i=0; i<N_ACTIONS; ++i )
            mat.decrementRow( ROW_INDEX );
    }//run
}//DecrementatoreRiga
