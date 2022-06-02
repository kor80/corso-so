package aes16bruteforce;

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
    private final String ALGORITHM="AES";
    private final byte[] SOURCE_BYTES;
    private final byte[] FLAG_BYTES;
    private LinkedList<TDecipherer> DECIPHERERS;
    private final int N_KEY_BYTES;
    private final int START,END;
    private String rightKey=null;
    Cipher cipher;

    //Speed Test
    private byte[] outputBytes;
    private int flagCharStreak;
    private boolean canFindFlag;
    private Key secretKey;
    private StringBuilder sb;

    public TDecipherer( int nKeyBytes, byte[] sourceBytes, byte[] flagBytes, int start, int end ){
        this.SOURCE_BYTES=sourceBytes;
        this.FLAG_BYTES=flagBytes;
        this.N_KEY_BYTES=nKeyBytes;
        this.START=start;
        this.END=end;
        DECIPHERERS=new LinkedList<>();
        try{
            cipher=Cipher.getInstance(ALGORITHM);
        } catch( NoSuchPaddingException | NoSuchAlgorithmException e ){
            System.out.println("Something went wrong with the cipher");
        }
    }

    @Override
    public void run(){
        for( int key=START; key<END; ++key )
            if( isValidKey(Integer.toString(key)) ){
                stopRunningDecipherers();
                break;
            }
    }//run

    private boolean isValidKey( String key ) {
        if( isInterrupted() ) return true;
        flagCharStreak=0;
        try{
            initCipher(key);
            outputBytes=cipher.doFinal(SOURCE_BYTES);
            canFindFlag=true;
            for( int i=0; canFindFlag; ++i ){
                if( outputBytes[i]==FLAG_BYTES[flagCharStreak] ){
                    flagCharStreak++;
                    if( flagCharStreak==FLAG_BYTES.length ){
                        rightKey=key;
                        return true;
                    }
                }
                else flagCharStreak=0;
                canFindFlag=i-flagCharStreak<outputBytes.length-FLAG_BYTES.length;
            }
            return false;
        }catch( IllegalBlockSizeException | BadPaddingException | InvalidKeyException e ){
            //System.out.println("Something went wrong with file decryption");
            return false;
        }
    }//isValidKey

    private void initCipher( String key ) throws InvalidKeyException{
        secretKey=new SecretKeySpec(keyPadding(key).getBytes(),ALGORITHM);
        cipher.init(CIPHER_MODE,secretKey);
    }//initCipher

    private void stopRunningDecipherers(){
        for( TDecipherer decipherer : DECIPHERERS )
            decipherer.interrupt();
    }//stopRunningDecipherers

    private String keyPadding( String key ){
        sb=new StringBuilder(N_KEY_BYTES);
        sb.append("0".repeat(N_KEY_BYTES - key.length()));
        sb.append(key);
        return sb.toString();
    }//keyPAdding

    public void setDecipherersList( LinkedList<TDecipherer> decipherers ){
        DECIPHERERS=decipherers; //Non mi serve una copia profonda
    }//setDecipherersList

    public String getKey() throws InterruptedException{
        this.join();
        if( rightKey!=null )
            return keyPadding(rightKey);
        return null;
    }//getKey
}//TDecipherer
