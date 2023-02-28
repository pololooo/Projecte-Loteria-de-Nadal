package loteria_nadal;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*Link al GitHub: https://github.com/pololooo/Projecte-Loteria-de-Nadal */
public class Loteria_Nadal {

    static Scanner scan = new Scanner(System.in);

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

    static String any;
    static final String MISSATGEPREMI = "El teu premi es de: ";

    /*Cada boleto, té un numero de 5 xifres i un premi assignat (o no)*/
    public static class boleto {

        int numero;
        int premio;

    }

    /**
     * Si el numero no ha sigut premiat en cap dels premis del bombo petit es
     * calculen les variacions
     *
     * @param numero
     * @return aquesta funció ens retorna el premi per aquells numeros que no
     * han tingut premi directe
     */
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
                premi = DOSCIFRAS + ULTIMAGORDO;
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

    /**
     * funcio per tornar el numero aleatori i descartar-lo pel següent numero
     *
     * @return aquesta funcio retorna el numero del decim, generat aleatoriament
     */
    public static int darNumero() {
        int numero = 0;

        numero = generarNumero();

        nums[numero] = -1;

        return numero;
    }

    /**
     * funcio per generar el numero automatic
     *
     * @return aquesta funcio genera el numero aleatori
     */
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

    /**
     * funcio que genera el sorteig
     */
    public static void simulacion(File f, String any) {

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
            GrabarBoleto(numeros, i, any);
        }

    }

    //
    /**
     * funcio que busca a l'array el numero que introduiem i torna el premi que
     * correspon
     *
     * @param boletoIntroducido
     * @return aquesta funcio ens retorna l'import del decim introduit per
     * l'usuari
     */
    public static int buscarPremio(boleto boletoIntroducido) {
        int resultat = 0;
        for (int i = 1; i < NUMPREMIOS; i++) {
            if (boletoIntroducido.numero == numeros[i].numero) {
                resultat = numeros[i].premio;
            }

        }

        return resultat;
    }

    /**
     * funcio per demanar un numero enter
     *
     * @param missatge
     * @return ens retorna el numero vàlid
     */
    static int Entero(String missatge) {
        int result;

        System.out.println(missatge);

        while (!scan.hasNextInt()) {
            System.out.println("Solo numeros enteros de 5 cifras: ");
            scan.next();
        }

        result = scan.nextInt();
        return result;
    }

    /**
     * funcio que comproba que siguin 5 digits
     *
     * @param boletoIntroducido
     * @return ens retorna el numero de decim amb un tamany valid
     */
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

    /**
     * funcio que crea l'array amb tots els nums del 0 al 99999
     */
    public static void llenarNums() {
        nums = new int[NUMPREMIOS];
        numAleatoris = new int[NUMPREMIOS];
        for (int i = 0; i < NUMPREMIOS - 1; i++) {
            nums[i] = i;
        }
    }

    /**
     * funcio per mostrar quins han sigut els numeros premiats
     */
    public static void mostrarPremis() {
        System.out.println("Primer premi: " + LeerNumerosBinario(1).numero);
        System.out.println("Segon premi: " + LeerNumerosBinario(2).numero);
        System.out.println("Tercer premi: " + LeerNumerosBinario(3).numero);
        System.out.println("Quarts premis: " + LeerNumerosBinario(4).numero + " " + LeerNumerosBinario(5).numero);
        System.out.println("Cinquens premis: " + LeerNumerosBinario(6).numero + " " + LeerNumerosBinario(7).numero
                + " " + LeerNumerosBinario(8).numero + " " + LeerNumerosBinario(9).numero + " " + LeerNumerosBinario(10).numero
                + " " + LeerNumerosBinario(11).numero + " " + LeerNumerosBinario(12).numero + " " + LeerNumerosBinario(13).numero);
    }

    public static boleto LeerNumerosBinario(int posicion) {
        boleto blt = new boleto();

        String nombre = "Sorteig" + any + ".bin";
        int linea = 1;
        DataInputStream dis = AbrirFicheroLecturaBinario(nombre, true);
        boolean trobat = false;
        do {
            blt = LeerDatosClienteBinario(dis);
            if (posicion == linea) {
                trobat = true;
            } else {
                blt = LeerDatosClienteBinario(dis);
                linea++;
            }
        } while (trobat != true);

        CerrarFicheroBinarioInput(dis);
        return blt;
    }

    public static void CerrarFicheroBinarioInput(DataInputStream dis) {
        try {
            dis.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void CerrarFicheroBinario(DataOutputStream dos) {
        try {
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boleto LeerDatosClienteBinario(DataInputStream dis) {
        boleto blt = new boleto();

        try {
            blt.numero = dis.readInt();
            blt.premio = dis.readInt();

        } catch (IOException ex) {
            blt = null;
        }
        return blt;
    }

    public static void ComprobarNumeroBinario() {
        String nombre = "Sorteig" + any + ".bin";
        DataInputStream dis = AbrirFicheroLecturaBinario(nombre, true);

        boleto boletoIntroducido = new boleto();
        boletoIntroducido.numero = Entero("Introdueix el teu numero de 5 xifres: ");
        boolean trobat = false;
        do {
            boleto blt = LeerDatosClienteBinario(dis);
            if (blt.numero == boletoIntroducido.numero) {
                System.out.println(MISSATGEPREMI+blt.premio);
                trobat = true;
            } else {
                blt = LeerDatosClienteBinario(dis);
            }
        } while (trobat != true);
        CerrarFicheroBinarioInput(dis);
    }

    public static DataInputStream AbrirFicheroLecturaBinario(String nomFichero, boolean crear) {
        DataInputStream dis = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileInputStream reader;
            try {
                reader = new FileInputStream(f);
                // PrintWriter para poder escribir más comodamente
                dis = new DataInputStream(reader);
            } catch (IOException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dis;
    }

    /**
     * funcio per introduir quin es el teu numero de decim
     *
     * @param boletoIntroducido
     */
    public static void IntroduirNum(boleto boletoIntroducido) {
        String resposta = "n";
        do {
            boletoIntroducido.numero = Entero("Introdueix el teu numero de 5 xifres: ");
            ComprobarTam(boletoIntroducido);
            boletoIntroducido.premio = buscarPremio(boletoIntroducido);
            System.out.println("Numero: " + boletoIntroducido.numero + " Premio: " + boletoIntroducido.premio + " euros");
            System.out.println("Vols introduir un nou boleto?(s/n)");
            resposta = scan.nextLine().toLowerCase();
        } while (resposta.equals("s"));
    }

    public static File AbrirFichero(String nomFichero, boolean crear) {
        File result = null;

        result = new File(nomFichero);

        if (!result.exists()) {
            if (crear) {
                try {
                    result.createNewFile();
                    llenarNums();
                    simulacion(result, any);
                } catch (IOException ex) {
                    Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
                    result = null;
                }
            } else {
                result = null;
            }
        }

        return result;
    }

    public static void GrabarBoleto(boleto[] numeros, int i, String any) {
        DataOutputStream dos = AbrirFicheroEscrituraBinario("Sorteig" + any + ".bin", true, true);

        if (numeros[i] != null) {
            try {
                dos.writeInt(numeros[i].numero);
                dos.writeInt(numeros[i].premio);
            } catch (IOException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        CerrarFicheroBinario(dos);

    }

    public static void CerrarFicheroBinarioOutput(DataOutputStream dos) {
        try {
            dos.flush();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DataOutputStream AbrirFicheroEscrituraBinario(String nomFichero, boolean crear, boolean blnAnyadir) {
        DataOutputStream dos = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero¡
            FileOutputStream writer;
            try {
                writer = new FileOutputStream(f, blnAnyadir);
                // PrintWriter para poder escribir más comodamente
                dos = new DataOutputStream(writer);
            } catch (IOException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dos;
    }

    public static void main(String[] args) {
        System.out.println("Inserta l'any del sorteig: ");
        any = scan.nextLine();
        File f = AbrirFichero("Sorteig" + any + ".bin", true);
        System.out.println("Sorteo acabado");
        mostrarPremis();
        boleto boletoIntroducido = new boleto();
        //IntroduirNum(boletoIntroducido);
        ComprobarNumeroBinario();
    }
//hola
}
