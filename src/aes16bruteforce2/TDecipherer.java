package aes16bruteforce2;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class TDecipherer extends Thread
{
    private final int CIPHER_MODE=Cipher.DECRYPT_MODE;
    private final String ALGORITHM;
    private final byte[] SOURCE_BYTES;
    private final byte[] FLAG_BYTES;
    private final int N_KEY_BYTES;
    private final int N_READERS=2;
    private final int start,end;
    private String rightKey=null;
    private Cipher cipher;
    private LinkedList<TDecipherer> decipherers;
    private LinkedList<TReader> readers;

    public TDecipherer( String algorithm, int nKeyBytes, byte[] sourceBytes, byte[] flagBytes, int start, int end ){
        this.ALGORITHM=algorithm;
        this.SOURCE_BYTES=sourceBytes;
        this.FLAG_BYTES=flagBytes;
        this.N_KEY_BYTES=nKeyBytes;
        this.start=start;
        this.end=end;
        decipherers=new LinkedList<>();
        try{
            cipher=Cipher.getInstance(ALGORITHM);
        } catch( NoSuchPaddingException | NoSuchAlgorithmException e ){
            System.out.println("Something went wrong with the cipher");
        }
        readers=new LinkedList<>();
    }

    @Override
    public void run(){
        for( int key=start; key<end; ++key )
            if( isValidKey(Integer.toString(key)) ){
                rightKey=Integer.toString(key);
                stopRunningDecipherers();
                break;
            }
    }//run

    private boolean isValidKey(String key ) {
        if( isInterrupted() ) return true;
        try{
            initCipher(key);
            byte[] outputBytes=cipher.doFinal(SOURCE_BYTES);
            startReaders(outputBytes);
            return readersFoundTheKey();

        }catch( IllegalBlockSizeException | BadPaddingException | InvalidKeyException e ){ return false; }
    }//isValidKey

    private void startReaders( byte[] passage ){
        int nSection=passage.length/N_READERS;
        int start,end;
        for( int i=0; i<N_READERS; ++i ){
            start=i*nSection;
            end=(i+1)*nSection;
            TReader reader=new TReader(passage,FLAG_BYTES,start,end);
            reader.setDaemon(true);
            reader.start();
            readers.add(reader);
        }
    }//startReaders

    private boolean readersFoundTheKey(){
        for( TReader reader : readers )
            if( reader.hasFindWord() || isInterrupted() ) return true;
        return false;
    }//readersFoundTheKey

    private void initCipher( String key ) throws InvalidKeyException{
        Key secretKey=new SecretKeySpec(keyPadding(key).getBytes(),ALGORITHM);
        cipher.init(CIPHER_MODE,secretKey);
    }//initCipher

    private void stopRunningDecipherers(){
        for( TDecipherer decipherer : decipherers )
            decipherer.interrupt();
    }//stopRunningDecipherers

    private String keyPadding( String key ){
        StringBuilder sb=new StringBuilder(16);
        sb.append("0".repeat(N_KEY_BYTES - key.length()));
        sb.append(key);
        return sb.toString();
    }//keyPAdding

    public void setDecipherersList( LinkedList<TDecipherer> decipherers ){
        this.decipherers=decipherers;
    } //setDecipherersList

    public String getKey(){
        try{
            this.join();
        }catch( InterruptedException e ){}
        if( rightKey!=null ) return keyPadding(rightKey);
        return null;
    }//getKey
}//TDecipherer
