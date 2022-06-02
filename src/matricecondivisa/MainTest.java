package matricecondivisa;

import java.util.LinkedList;

public class MainTest
{
    private final static int N_ROW=10;
    private final static int N_COL=15;
    private final static int ATTEMPTS=1_000_000;

    private static LinkedList<DecrementatoreRiga> decrementatori=new LinkedList<>();
    private static LinkedList<IncrementatoreColonna> incrementatori=new LinkedList<>();
    private static MatriceCondivisaNTS matNTS=new MatriceCondivisaNTS(N_ROW,N_COL);
    private static MatriceCondivisaAI matAI=new MatriceCondivisaAI(N_ROW,N_COL);
    private static MatriceCondivisaS matS=new MatriceCondivisaS(N_ROW,N_COL);

    public static void main(String[] args) {
        //staticAttempt();
        //dynamicAttempt();

        /* ------------------------ CELLWISE MUTEX ------------------------ */
        long startTime=System.currentTimeMillis();
        startThreads(matS,ATTEMPTS);
        printMat(matS);
        System.out.println(System.currentTimeMillis()-startTime+"sec");
    }//main

    private static void staticAttempt(){
        startThreads( matNTS,ATTEMPTS );
        startThreads( matAI,ATTEMPTS );
        System.out.println("Marice NTS dopo "+ATTEMPTS+" azioni per cella");
        printMat( matNTS );
        System.out.print("\n\n\n");
        System.out.println("Marice AI dopo "+ATTEMPTS+" azioni per cella");
        printMat( matAI );
    }//staticAttempt

    private static void dynamicAttempt(){
        int attemptsForInterleaving=getNumberOfAttemptsBeforeInterleaving();
        System.out.println("Il numero di tentativi per il quale si è verificato un interfogliamento è "+attemptsForInterleaving);
        System.out.println("La matrice falsata a causa del 'non thread safe' è: ");
        printMat(matNTS);
        System.out.println("\n\nE la matrice con lo stesso numero di tentativi, ma con atomic integer è: ");
        startThreads(matAI,attemptsForInterleaving);
        printMat(matAI);
    }//dynamicAttempt

    private static int getNumberOfAttemptsBeforeInterleaving(){
        int attempts=10;
        do{
            attempts++;
            matNTS=new MatriceCondivisaNTS(N_ROW,N_COL);
            startThreads(matNTS,attempts);
            joinThreads();
        }while( matNTS.isNull() );
        return attempts;
    }//getNumberOfAttemptsBeforeInterleaving

    /* --------------------- UTILITY --------------------- */

    private static void startThreads( MatriceCondivisa mat, int attempts ){
        decrementatori.clear();
        incrementatori.clear();
        for( int i=0; i<N_ROW; ++i ){
            DecrementatoreRiga dr=new DecrementatoreRiga(attempts,i,mat);
            decrementatori.add(dr);
            dr.start();
        }
        for( int j=0; j<N_COL; ++j ){
            IncrementatoreColonna dc=new IncrementatoreColonna(attempts,j,mat);
            incrementatori.add(dc);
            dc.start();
        }
    }//startThreads

    private static void joinThreads(){
        try{
            for( DecrementatoreRiga dr : decrementatori ) dr.join();
            for( IncrementatoreColonna dc : incrementatori ) dc.join();
        }
        catch( InterruptedException e ){ System.out.println("Errore nella join dei thread"); }
    }//joinThreads

    private static void printMat( MatriceCondivisa mat ){
        joinThreads();
        mat.printMat();
    }//printMat
}//MainTest
