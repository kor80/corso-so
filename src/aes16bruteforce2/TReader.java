package aes16bruteforce2;

public class TReader extends Thread
{
    private final byte[] PASSAGE;
    private final byte[] WORD_TO_FIND;
    private final int START,END;
    private boolean wordFinded=false;

    public TReader( byte[] passage, byte[] wordToFind, int start, int end ){
        this.PASSAGE=passage;
        this.WORD_TO_FIND=wordToFind;
        this.START=start;
        this.END=end;
    }

    public void run(){
        int wordCharStreak=0;
        wordCharStreak=startReading(START,END,wordCharStreak);
        if( 0<wordCharStreak && wordCharStreak< WORD_TO_FIND.length )
            continueReading(END,wordCharStreak);
    }//run

    private int startReading( int start, int end, int wordCharStreak ){
        for( int i=start; i<end; ++i ){
            if( PASSAGE[i]==WORD_TO_FIND[wordCharStreak] ){
                wordCharStreak++;
                if( wordCharStreak==WORD_TO_FIND.length ){
                    wordFinded=true;
                    break;
                }
            }
            else wordCharStreak=0;
        }
        return wordCharStreak;
    }//startReading

    private void continueReading( int start, int wordCharStreak ){
        for( int i=start; i<PASSAGE.length; ++i ){
            if( PASSAGE[i]==WORD_TO_FIND[wordCharStreak] ){
                wordCharStreak++;
                if( wordCharStreak==WORD_TO_FIND.length ){
                    wordFinded=true;
                    break;
                }
            }
            else break;
        }
    }//continueReading

    public boolean hasFindWord(){
        try{
            this.join();
        }catch( InterruptedException e ){}
        return wordFinded;
    }//hasFindWord

}//TReader
