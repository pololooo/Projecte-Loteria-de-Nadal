package loteria_nadal;

import java.util.*;
import java.util.Random;

public class Loteria_Nadal {

    /*Aquí declarem unes constants globals, on guardem els premis i la quantitat
    de numeros que juguen (del 0 fins al 99.999)
     */
    static final int NUMPREMIOS = 100000;
    static int [] nums;
    static int []numAleatoris;
    
    //Quantitat de diners per decim
    static final String PREMIOGORDO = "400.000€";
    static final String PREMIOSEGUNDO = "125.000€";
    static final String PREMIOTERCERO = "50.000€";
    static final String PREMIOCUARTO = "20.000€";
    static final String PREMIOQUINTO = "6.000€";
    static final String PREMIOPEDREA = "1.000€";
    static final String GORDOCONTIGUOS = "2.000€";
    static final String SEGUNDOCONTIGUOS = "1.250€";
    static final String TERCEROCONTIGUOS = "960€";
    static final String DOSCIFRAS = "100€";
    static final String ULTIMAGORDO = "20€";
    
    //Els premis que toquen (primer premi = gordo),...
    static final int PRIMERO=1;
    static final int SEGUNDO=2;
    static final int TERCERO=3;
    static final int CUARTO=4;
    static final int QUINTO=5;
    static final int PRIMEROSPREMIOS=14;
    static final int RESTOPREMIOS=1807;
    
    static final int MIL=1000;
    static final int CIEN=100;
    static final int DIEZ=10;
    
    
    
    /*Declarem les variables globals*/
    static boleto[] numeros = new boleto[NUMPREMIOS];
    static int NUMGORDO;
    static int NUMSEGUNDO;
    static int NUMTERCERO;
    static int NUMCUARTO1;
    static int NUMCUARTO2;

    /*Cada boleto, té un numero de 5 xifres i un premi assignat (o no)*/
    public static class boleto {

        int numero;
        String premio;

    }

    /*Si el numero no ha sigut premiat en cap dels premis del bombo petit
    es calculen les variacions */
    public static String calcularPremio(int numero) {
        String premi = "";
        if (numero == NUMGORDO + 1 || numero == NUMGORDO - 1) {
            premi = GORDOCONTIGUOS;
        } else if (numero == NUMSEGUNDO + 1 || numero == NUMSEGUNDO - 1) {
            premi = SEGUNDOCONTIGUOS;
        } else if (numero == NUMTERCERO + 1 || numero == NUMTERCERO - 1) {
            premi = TERCEROCONTIGUOS;
        } else if (numero % DIEZ == NUMGORDO % DIEZ) {
            if (numero % CIEN == NUMGORDO % CIEN) {
                premi = DOSCIFRAS;
            } else {
                premi = ULTIMAGORDO;
            }
        } else if (numero % CIEN == NUMSEGUNDO % CIEN) {
            premi = DOSCIFRAS;
        } else if (numero % CIEN == NUMTERCERO % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (NUMGORDO / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (NUMSEGUNDO / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (NUMTERCERO / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (NUMCUARTO1 / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (NUMCUARTO2 / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else {
            premi = "Aquest dècim no ha estat premiat.";
        }

        return premi;
    }
    public static int darNumero(){
        int numero=0;
        for (int i = 1; i < NUMPREMIOS; i++) {
        	nums[i] = i;
    	}
    	for (int i = 0; i < numAleatoris.length; i++) {
        	numero = generarNumero();
        	numAleatoris[i] = nums[numero];
        	nums[numero] = -1;

    	}
    	for (int i = 0; i < numAleatoris.length; i++) {
        	System.out.print(numAleatoris[i] + " ");
    	}
     return numero;
    }
    public static int generarNumero(){
        Random ra = new Random();
        int numero = ra.nextInt(15);
    	if (nums[numero] == -1) {
        	return generarNumero();
    	} else {
        	return nums[numero];
    	}
    }
    public static void simulacion() {

        //Generem numeros randoms i li assignem un premi en ordre
        
        int numero = darNumero();
        for (int i = 1; i < NUMPREMIOS; i++) {
            numeros[i] = new boleto();
            if (i == PRIMERO) {
                NUMGORDO = numero;
                numeros[i].numero = NUMGORDO;
                numeros[i].premio = PREMIOGORDO;
            } else if (i == SEGUNDO) {
                NUMSEGUNDO = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOSEGUNDO;
            } else if (i == TERCERO) {
                NUMTERCERO = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOTERCERO;
            } else if (i == CUARTO) {
                NUMCUARTO1 = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOCUARTO;
            } else if (i == QUINTO) {
                NUMCUARTO2 = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOCUARTO;
            } else if (i < PRIMEROSPREMIOS) {
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOQUINTO;
            } else if (i < RESTOPREMIOS) {
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOPEDREA;
            } else {
                numeros[i].numero = numero;
                numeros[i].premio = "No te premi";
            }

        }

    }

    public static void main(String[] args) {

    }

}
