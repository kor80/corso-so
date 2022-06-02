package aes16bruteforce;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

@SuppressWarnings("Duplicates")
public class Decrypt
{
    private static final String ALGORITHM="AES";
    private static final String FLAG="SISOP-corsoB";
    private static final int N_THREADS=16;
    private static final int N_KEY_BYTES=16;
    private static final int N_SECTIONS=Integer.MAX_VALUE/N_THREADS;
    private static byte[] sourceByteArray;
    private static LinkedList<TDecipherer> decipherers=new LinkedList<>();

    public static void main(String[] args) throws IOException{
        JFileChooser jfc=new JFileChooser();
        jfc.showOpenDialog(null);
        final File inputFile=jfc.getSelectedFile();
        long startingTime=System.currentTimeMillis();
        sourceByteArray=new byte[(int) inputFile.length()];

        readAndFill(inputFile,sourceByteArray);
        if( sourceByteArray==null ) return;
        startDecipherers(sourceByteArray);
        printEncryptionKey();

        double minutesTook=(System.currentTimeMillis()-startingTime)/Math.pow(10,3)/60;
        System.out.println("Il programma ha impiegato "+minutesTook+" minuti");
    }//main

    private static void startDecipherers( byte[] sourceByteArray ){
        final byte[] FLAG_BYTES=FLAG.getBytes();
        int start,end;
        for( int i=0; i<N_THREADS; ++i ){
            start=i*N_SECTIONS;
            end=(i+1)*N_SECTIONS;
            TDecipherer decipherer=new TDecipherer(N_KEY_BYTES, sourceByteArray, FLAG_BYTES, start, end);
            decipherers.add(decipherer);
        }
        for( TDecipherer decipherer : decipherers ){
            decipherer.setDecipherersList(decipherers);
            decipherer.start();
        }
    }//startDecipherers

    private static void printEncryptionKey(){
        try{
            for( TDecipherer decipherer : decipherers ){
                String key=decipherer.getKey();
                if( key!=null ){
                    encryptAndPrint(key);
                    return;
                }
            }
            System.out.println("Key not found");
        }catch( InterruptedException e ){}
    }//printEncryptionKey

    private static void encryptAndPrint( String key ) {
        System.out.println("La chiave è: "+key);
        System.out.println("Il testo è il seguente: ");
        try{
            Cipher cipher=Cipher.getInstance(ALGORITHM);
            Key k=new SecretKeySpec(key.getBytes(),ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE,k);
            byte[] outputBytes=cipher.doFinal(sourceByteArray);
            for( byte b : outputBytes )
                System.out.print(Character.toChars(b));
            System.out.println();

        }catch( NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e ) {
            System.out.println("Something went wrong with decrypted text reading");
        }
    }//encryptAndPrint

    private static void readAndFill( File sourceFile, byte[] sourceByteArray ) throws IOException {
        BufferedInputStream sourceStream=null;
        try{
            sourceStream=new BufferedInputStream(new FileInputStream(sourceFile));
            sourceStream.read(sourceByteArray);
        }catch( IOException e ){
            System.out.println("Something went wrong with source file reading");
        }finally{
            if( sourceStream!=null ) sourceStream.close();
        }
    }//readAndFill
}//Decrypt
