package loteria_nadal;

import java.util.*;
import java.util.Random;

public class Loteria_Nadal {

    /*Aquí declarem unes constants globals, on guardem els premis i la quantitat
    de numeros que juguen (del 0 fins al 99.999)
     */
    static final int NUMPREMIOS = 100000;
    static int[] nums;
    static int[] numAleatoris;

    //Quantitat de diners per decim
    static final int PREMIOGORDO = 400000;
    static final int PREMIOSEGUNDO = 125000;
    static final int PREMIOTERCERO = 50000;
    static final int PREMIOCUARTO = 20000;
    static final int PREMIOQUINTO = 6000;
    static final int PREMIOPEDREA = 1000;
    static final int GORDOCONTIGUOS = 2000;
    static final int SEGUNDOCONTIGUOS = 1250;
    static final int TERCEROCONTIGUOS = 960;
    static final int DOSCIFRAS = 100;
    static final int ULTIMAGORDO = 20;

    //Els premis que toquen (primer premi = gordo),...
    static final int PRIMERO = 1;
    static final int SEGUNDO = 2;
    static final int TERCERO = 3;
    static final int CUARTO = 4;
    static final int QUINTO = 5;
    static final int PRIMEROSPREMIOS = 14;
    static final int RESTOPREMIOS = 1807;

    static final int MIL = 1000;
    static final int CIEN = 100;
    static final int DIEZ = 10;

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
        int premio;

    }

    /*Si el numero no ha sigut premiat en cap dels premis del bombo petit
    es calculen les variacions */
    public static int calcularPremio(int numero) {
        int premi = 0;
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
            premi = 0;
        }

        return premi;
    }

    public static int darNumero() {
        int numero = 0;

        numero = generarNumero();

        nums[numero] = -1;

        return numero;
    }

    public static int generarNumero() {

        Random ra = new Random();
        int numero = -1;
        do {
            numero = ra.nextInt(NUMPREMIOS);
        } while (nums[numero] == -1);

        return nums[numero];
    }

    public static void simulacion() {

        //Generem numeros randoms i li assignem un premi en ordre
        for (int i = 1; i < NUMPREMIOS; i++) {
            int numero = darNumero();
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
                numeros[i].premio = calcularPremio(numero);
            }

        }

    }

    public static int buscarPremio(boleto boletoIntroducido) {
        int resultat = 0;
        for (int i = 1; i < NUMPREMIOS; i++) {
            if (boletoIntroducido.numero == numeros[i].numero) {
                resultat = numeros[i].premio;
            }

        }

        return resultat;
    }

    static int Entero(String missatge) {
        Scanner scan = new Scanner(System.in);
        int result;

        System.out.println(missatge);

        while (!scan.hasNextInt()) {
            System.out.println("Solo numeros enteros de 5 cifras: ");
            scan.next();
        }

        result = scan.nextInt();
        return result;
    }

    static void ComprobarTam(boleto boletoIntroducido) {

        String cadena = String.valueOf(boletoIntroducido.numero);
        int tam = cadena.length();

        while (tam != 5) {
            System.out.println("El numero no es valid, torna a provar: ");
            boletoIntroducido.numero = Entero("");

        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        nums = new int[NUMPREMIOS];
        numAleatoris = new int[NUMPREMIOS];
        for (int i = 0; i < NUMPREMIOS - 1; i++) {
            nums[i] = i;
        }
        simulacion();
        System.out.println("Sorteo acabado");
        System.out.println(numeros[1].numero);
        boleto boletoIntroducido;
        String resposta = "n";

        /**/
        
        do {
            boletoIntroducido = new boleto();

            boletoIntroducido.numero = Entero("Introdueix el teu numero: ");

            ComprobarTam(boletoIntroducido);

            boletoIntroducido.premio = buscarPremio(boletoIntroducido);

            System.out.println("Numero: " + boletoIntroducido.numero + " Premio: " + boletoIntroducido.premio + "€");

            System.out.println("Vols introduir un nou boleto?(s/n)");
            resposta = scan.nextLine();

        } while (resposta.equals("s"));

    }

}
