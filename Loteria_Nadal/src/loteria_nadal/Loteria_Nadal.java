package loteria_nadal;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    //Nom dels fitxers
    public static final String FICHERO_ES = "./espanol.txt";
    public static final String FICHERO_CAT = "./catalan.txt";
    public static final String FICHERO_EUSK = "./euskera.txt";
    public static final String FICHERO_GAL = "./gallego.txt";
    public static int numLinea = 0;
    public static int opcionIdioma = 0;
    public static int opcion = 0;

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

        //System.out.println(missatge);
        try {
            while (!scan.hasNextInt()) {
                numLinea = 28;
                LeerLineaIdioma();
                scan.next();
            }
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                numLinea = 27;
                LeerLineaIdioma();
                boletoIntroducido.numero = Entero("");
                cadena = String.valueOf(boletoIntroducido.numero);
                tam = cadena.length();
            } catch (IOException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }

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
        try {
            numLinea = 13;
            LeerLineaIdioma();
            System.out.println(LeerNumerosBinario(1).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosBinario(2).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosBinario(3).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosBinario(4).numero + " " + LeerNumerosBinario(5).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosBinario(6).numero + " " + LeerNumerosBinario(7).numero
                    + " " + LeerNumerosBinario(8).numero + " " + LeerNumerosBinario(9).numero + " " + LeerNumerosBinario(10).numero
                    + " " + LeerNumerosBinario(11).numero + " " + LeerNumerosBinario(12).numero + " " + LeerNumerosBinario(13).numero);
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boleto LeerNumerosBinario(int posicion) {
        boleto blt = new boleto();

        String nombre = "Sorteig" + any + ".bin";
        int linea = 1;
        DataInputStream dis = AbrirFicheroLecturaBinario(nombre, true);
        boolean trobat = false;
        blt = LeerNumerosPremiados(dis);
        while(trobat!=true){
            
            if (posicion == linea) {
                trobat = true;
            } else {
                blt = LeerNumerosPremiados(dis);
                linea++;
            }
        } 

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

    public static boleto LeerNumerosPremiados(DataInputStream dis) {
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
        try {
            String nombre = "Sorteig" + any + ".bin";
            DataInputStream dis = AbrirFicheroLecturaBinario(nombre, true);

        boleto boletoIntroducido = new boleto();
        boletoIntroducido.numero = Entero("Introdueix el teu numero de 5 xifres: ");
        boolean trobat = false;
        boleto blt = LeerNumerosPremiados(dis);
        while(trobat!=true){
            
            if (blt.numero == boletoIntroducido.numero) {
                System.out.println(MISSATGEPREMI + blt.premio);
                trobat = true;
            } else {
                blt = LeerNumerosPremiados(dis);
            }
        }
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

        try {
            do {
                numLinea = 18;
                LeerLineaIdioma();
                ComprobarTam(boletoIntroducido);
                boletoIntroducido.premio = buscarPremio(boletoIntroducido);

                numLinea = 19;
                LeerLineaIdioma();
                numLinea = 29;
                LeerLineaIdioma();
                resposta = scan.nextLine().toLowerCase();
            } while (resposta.equals("s"));
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    //Añair parametros amigos
    public static class Amigo {

        String nombre;
        int numLoteria;
        double importe;

        Amigo(String nombre, int numLoteria, double importe) {
            this.nombre = nombre;
            this.numLoteria = numLoteria;
            this.importe = importe;
        }
    }

    static String nombre;
    static int anySorteig;
    static List<Amigo> amigos;
    static double importeTotal;

    public void Colla(String nombre, int anySorteig) {
        this.nombre = nombre;
        this.anySorteig = anySorteig;
        amigos = new ArrayList<Amigo>();
        importeTotal = 0;
    }

    // Método para añadir un amigo a la colla
    public static void addAmigo(String nombre, int numLoteria, double importe) {
        amigos.add(new Amigo(nombre, numLoteria, importe));
        importeTotal += importe;
    }

    public static void opcionesMenu() {
        switch (opcion) {
            case 1:
                opcionSorteo();
                break;

            case 2:
                opcionGrupo();
                break;

            case 3:
                opcionEscogerIdioma();
                break;
        }
    }

    public static BufferedReader AbrirFicheroLectura(String nomFichero, boolean crear) {
        BufferedReader br = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el reader para poder leer el fichero¡
            FileReader reader;
            try {
                reader = new FileReader(f);
                // Buffered reader para poder leer más comodamente
                br = new BufferedReader(reader);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return br;
    }

    public static void CerrarFichero(BufferedReader br) {
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void LeerFichero() {

        // Creamos el enlace con el fichero en el disco
        BufferedReader bufEs = AbrirFicheroLectura(FICHERO_ES, true);
        BufferedReader bufCat = AbrirFicheroLectura(FICHERO_CAT, true);
        BufferedReader bufEusk = AbrirFicheroLectura(FICHERO_EUSK, true);
        BufferedReader bufGal = AbrirFicheroLectura(FICHERO_GAL, true);

        CerrarFichero(bufEs);
        CerrarFichero(bufCat);
        CerrarFichero(bufEusk);
        CerrarFichero(bufGal);

    }

    /**
     * Esculls l'idioma
     */
    public static void MenuIdioma() {
        System.out.println("1-Español");
        System.out.println("2-Català");
        System.out.println("3-Euskeraz");
        System.out.println("4-Galego");
        opcionIdioma = scan.nextInt();
    }

    /**
     * Enssenya el menu
     */
    public static void Menu() {
        numLinea = 0;
        try {
            do {
                //Imprimeix les 3 opcions del menu
                LeerLineaIdioma();
                numLinea++;
            } while (numLinea < 6);
            opcion = scan.nextInt();

            opcionesMenu();

        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Si escull l'opció de canviar d'idioma
    public static void opcionEscogerIdioma() {
        //  numLinea=6;
        try {
            do {
                //Imprimeix les opcions d'idiomes
                LeerLineaIdioma();
                numLinea++;
            } while (numLinea < 11);
            opcionIdioma = scan.nextInt();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Si escull l'opció del sorteig
    public static void opcionSorteo() {
        numLinea = 11;
        try {
            //Inserta l'any del sorteig:
            LeerLineaIdioma();
            any = scan.next();
            File f = AbrirFichero("Sorteig" + any + ".bin", true);
            numLinea++;
            LeerLineaIdioma();
            mostrarPremis();
            boleto boletoIntroducido = new boleto();
            // IntroduirNum(boletoIntroducido);
            ComprobarNumeroBinario();

        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Método para comprobar los premios de la colla
    // Método para crear una colla a partir de los datos introducidos por el usuario
    public static void opcionGrupo() {
        numLinea = 20;

        try {

            LeerLineaIdioma();
            String nombre = scan.next();

            numLinea++;
            //numLinea=21
            LeerLineaIdioma();
            int anySorteig = Integer.parseInt(scan.next());

            boolean seguirAñadiendo = true;
            while (seguirAñadiendo) {
                //numLinea=22
                numLinea++;
                LeerLineaIdioma();
                String nombreAmigo = scan.next();

                //numLinea=23
                numLinea++;
                LeerLineaIdioma();
                System.out.println(nombreAmigo);
                int numLoteria = Integer.parseInt(scan.next());

                numLinea++;
                LeerLineaIdioma();
                System.out.println(nombreAmigo);
                double importe = Double.parseDouble(scan.next());
                while (importe <= 5 || importe >= 60 || importe % 5 != 0) {
                    numLinea++;
                    LeerLineaIdioma();
                    importe = Double.parseDouble(scan.next());
                }

                numLinea++;
                LeerLineaIdioma();
                String respuesta = scan.next();
                seguirAñadiendo = respuesta.equals("s");
            }

        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Utilitza un arxiu o un altre depenen de l'idioma escollit
     *
     * @return
     * @throws IOException
     */
    public static String LeerLineaIdioma() throws IOException {
        String linea = "";
        switch (opcionIdioma) {
            case 1:
                linea = Files.readAllLines(Paths.get("espanol.txt")).get(numLinea);
                System.out.println(linea);
                break;
            case 2:
                linea = Files.readAllLines(Paths.get("catalan.txt")).get(numLinea);
                System.out.println(linea);
                break;
            case 3:
                linea = Files.readAllLines(Paths.get("euskera.txt")).get(numLinea);
                System.out.println(linea);
                break;
            case 4:
                linea = Files.readAllLines(Paths.get("gallego.txt")).get(numLinea);
                System.out.println(linea);
                break;
        }
        return linea;
    }

    public static void main(String[] args) {
        /*
        int result = menu();
        while (result != 0) {
            gestionMenu(result);
            result = menu();
        }
        //LeerFichero();
         */
        LeerFichero();
        MenuIdioma();
        while (opcion != 4) {
            Menu();
        }

    }

}
