package cronometro;

import java.util.Locale;
import java.util.Scanner;

public class CronometroTest
{
    private static CronometroInterattivo cronometro;

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        cronometro=new CronometroInterattivo();
        String input="";

        cronometro.start();
        System.out.println("-------------------TABELLA COMANDI-------------------");
        System.out.println("A=avvia(o riprendi se fermato) il cronometro");
        System.out.println("F=ferma il cronometro");
        System.out.println("R=ferma e inizializza il cronometro");
        System.out.println("E=ferma il cronometro ed esci");
        System.out.println("S=stampa il contatore corrente");
        do{
            System.out.print(">");
            input=sc.nextLine();
            commandHandler(input.toUpperCase());
        }while( !input.equals("") );
    }//main

    private static void commandHandler( String command ){
        switch( command ){
            case "A": cronometro.startCount();   break;
            case "F": cronometro.stopCount();    break;
            case "R": cronometro.restartCount(); break;
            case "E": cronometro.exitCount();    break;
            case "S": cronometro.printCount();   break;
            case "":  if( !cronometro.isInterrupted() ) cronometro.exitCount();
                      System.out.println("Bye");
                      break;
            default:  System.out.println("Comando non valido.");
        }
    }//commandHandler
}//CronometroTest
