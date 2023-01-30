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
    static final int CUARTO2 = 5;
    static final int PRIMEROSPREMIOS = 14;
    static final int RESTOPREMIOS = 1807;

    static final int MIL = 1000;
    static final int CIEN = 100;
    static final int DIEZ = 10;

    /*Declarem les variables globals*/
    static boleto[] numeros = new boleto[NUMPREMIOS];
    static int numGordo;
    static int numSegundo;
    static int numTercero;
    static int numCuarto1;
    static int numCuarto2;

    /*Cada boleto, té un numero de 5 xifres i un premi assignat (o no)*/
    public static class boleto {

        int numero;
        int premio;

    }

    /*Si el numero no ha sigut premiat en cap dels premis del bombo petit
    es calculen les variacions */
    public static int calcularPremio(int numero) {
        int premi = 0;
        //Comprobem si es contigu al gros, al segon o al tercer
        if (numero == numGordo + 1 || numero == numGordo - 1) {
            premi = GORDOCONTIGUOS;
        } else if (numero == numSegundo + 1 || numero == numSegundo - 1) {
            premi = SEGUNDOCONTIGUOS;
        } else if (numero == numTercero + 1 || numero == numTercero - 1) {
            premi = TERCEROCONTIGUOS;
        } //comprobem si coincideixen les ultimes xifres del gros i del segon
        else if (numero % DIEZ == numGordo % DIEZ) {
            if (numero % CIEN == numGordo % CIEN) {
                premi = DOSCIFRAS;
            } else {
                premi = ULTIMAGORDO;
            }
        } else if (numero % CIEN == numSegundo % CIEN) {
            premi = DOSCIFRAS;
        } else if (numero % CIEN == numTercero % CIEN) {
            premi = DOSCIFRAS;
        } //comprobem si coincideixen les primeres xifres del gros, segon, tercer i cuart
        else if ((numero / MIL) % CIEN == (numGordo / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (numSegundo / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (numTercero / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (numCuarto1 / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else if ((numero / MIL) % CIEN == (numCuarto2 / MIL) % CIEN) {
            premi = DOSCIFRAS;
        } else {
            premi = 0;
        }

        return premi;
    }

    //funcio per tornar el numero aleatori
    public static int darNumero() {
        int numero = 0;

        numero = generarNumero();

        nums[numero] = -1;

        return numero;
    }

    //funcio per generar el numero automatic
    public static int generarNumero() {
        /*Aquesta funcio genera un numero aleatori, selecciona aquesta posicio
        de l'array amb numeros del 0 al 99999 i el descarta per al seguent numero*/
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
                numGordo = numero;
                numeros[i].numero = numGordo;
                numeros[i].premio = PREMIOGORDO;
            } else if (i == SEGUNDO) {
                numSegundo = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOSEGUNDO;
            } else if (i == TERCERO) {
                numTercero = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOTERCERO;
            } else if (i == CUARTO) {
                numCuarto1 = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOCUARTO;
            } else if (i == CUARTO2) {
                numCuarto2 = numero;
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOCUARTO;
            } else if (i < PRIMEROSPREMIOS) {
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOQUINTO;
            } else if (i < RESTOPREMIOS) {
                numeros[i].numero = numero;
                numeros[i].premio = PREMIOPEDREA;
            } else { //per als que ja no tenen premi d'inici, calculem el premi
                numeros[i].numero = numero;
                numeros[i].premio = calcularPremio(numero);
            }

        }

    }

    //funcio que busca a l'array el numero que introduiem i torna el premi que
    //correspon
    public static int buscarPremio(boleto boletoIntroducido) {
        int resultat = 0;
        for (int i = 1; i < NUMPREMIOS; i++) {
            if (boletoIntroducido.numero == numeros[i].numero) {
                resultat = numeros[i].premio;
            }

        }

        return resultat;
    }

    //funcio per demanar un numero enter
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

    //funcio que comproba que siguin 5 digits
    static boolean ComprobarTam(boleto boletoIntroducido) {
        boolean resultat = false;

        String cadena = String.valueOf(boletoIntroducido.numero);
        int tam = cadena.length();
        
        while (tam != 5) {
            System.out.println("El numero no es valid, torna a provar: ");
            

            boletoIntroducido.numero = Entero("");
            cadena = String.valueOf(boletoIntroducido.numero);
            tam = cadena.length();

        }
        cadena = "";
        resultat = true;
        return resultat;
    }

    //funcio que crea l'array amb tots els nums del 0 al 99999
    public static void llenarNums() {
        nums = new int[NUMPREMIOS];
        numAleatoris = new int[NUMPREMIOS];
        for (int i = 0; i < NUMPREMIOS - 1; i++) {
            nums[i] = i;
        }
    }

    //funcio per mostrar quins han sigut els numeros premiats
    public static void mostrarPremis() {
        System.out.println("Primer premi: " + numeros[1].numero);
        System.out.println("Segon premi: " + numeros[2].numero);
        System.out.println("Tercer premi: " + numeros[3].numero);
        System.out.println("Quarts premis: " + numeros[4].numero + " " + numeros[5].numero);
        System.out.println("Cinquens premis: " + numeros[6].numero + " " + numeros[7].numero
                + " " + numeros[8].numero + " " + numeros[9].numero + " " + numeros[10].numero
                + " " + numeros[11].numero + " " + numeros[12].numero + " " + numeros[13].numero);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        llenarNums();
        simulacion();
        System.out.println("Sorteo acabado");
        mostrarPremis();
        boleto boletoIntroducido;
        String resposta = "n";

        do {
            boletoIntroducido = new boleto();

            boletoIntroducido.numero = Entero("Introdueix el teu numero de 5 xifres: ");
            ComprobarTam(boletoIntroducido);

            boletoIntroducido.premio = buscarPremio(boletoIntroducido);

            System.out.println("Numero: " + boletoIntroducido.numero + " Premio: " + boletoIntroducido.premio + " euros");

            System.out.println("Vols introduir un nou boleto?(s/n)");
            resposta = scan.nextLine();

        } while (resposta.equals("s"));

    }

}
