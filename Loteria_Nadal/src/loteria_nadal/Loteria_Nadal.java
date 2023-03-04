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

    /*Aqu? declarem unes constants globals, on guardem els premis i la quantitat
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
    static final int VEINTIOCHO = 28;
    static final int CINCO = 5;
    static final int TRECE = 13;
    static final int DIECIOCHO = 18;
    static final int VEINTE = 20;
    static final int VEINTIDOS = 22;
    static final int SESENTA = 60;
    static final int DOS = 2;

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

    //Variables globals
    public static int numLinea = 0;
    public static int opcionIdioma = 0;
    public static int opcion = 0;

    static String any;

    /*Cada boleto, t? un numero de 5 xifres i un premi assignat (o no)*/
    public static class boleto {

        int numero;
        int premio;

    }

    /**
     * Si el numero no ha sigut premiat en cap dels premis del bombo petit es
     * calculen les variacions
     *
     * @param numero
     * @return aquesta funci? ens retorna el premi per aquells numeros que no
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
     * funcio per tornar el numero aleatori i descartar-lo pel seg?ent numero
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
     * funcio per demanar un numero enter
     *
     * @param missatge
     * @return ens retorna el numero v?lid
     */
    static int Entero(String missatge) {
        int result;

        //System.out.println(missatge);
        try {
            while (!scan.hasNextInt()) {
                numLinea = VEINTIOCHO;
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

        while (tam != CINCO) {
            try {
                numLinea = VEINTIOCHO - 1;
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
            numLinea = TRECE;
            LeerLineaIdioma();
            System.out.println(LeerNumerosPremiadosBinario(1).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosPremiadosBinario(DOS).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosPremiadosBinario(DOS + 1).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosPremiadosBinario(CINCO - 1).numero + " " + LeerNumerosPremiadosBinario(CINCO).numero);
            numLinea++;
            LeerLineaIdioma();
            System.out.println(LeerNumerosPremiadosBinario(CINCO + 1).numero + " " + LeerNumerosPremiadosBinario(CINCO + DOS).numero
                    + " " + LeerNumerosPremiadosBinario(DIEZ - DOS).numero + " " + LeerNumerosPremiadosBinario(DIEZ - 1).numero + " " + LeerNumerosPremiadosBinario(DIEZ).numero
                    + " " + LeerNumerosPremiadosBinario(DIEZ + 1).numero + " " + LeerNumerosPremiadosBinario(TRECE - 1).numero + " " + LeerNumerosPremiadosBinario(TRECE).numero);
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * funcion que nos servir? para leer los diferentes numeros del sorteo y poder
     * devolver los que buscamos, en este caso lo usamos para devolver los primeros
     * premios
     * @param posicion
     * @return 
     */
    public static boleto LeerNumerosPremiadosBinario(int posicion) {
        boleto blt = new boleto();

        String nombre = "Sorteig" + any + ".bin";
        int linea = 1;
        DataInputStream dis = AbrirFicheroLecturaBinario(nombre, true);
        boolean trobat = false;
        blt = LeerNumerosSorteo(dis);
        while (trobat != true) {

            if (posicion == linea) {
                trobat = true;
            } else {
                blt = LeerNumerosSorteo(dis);
                linea++;
            }
        }

        CerrarFicheroBinarioInput(dis);
        return blt;
    }
    
    /**
     * funcion que nos sirve para cerrar un archivo binario del que estamos leyendo
     * @param dis 
     */
    public static void CerrarFicheroBinarioInput(DataInputStream dis) {
        try {
            dis.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * funcion para cerrar un archivo binario en el que hemos escrito
     * @param dos 
     */
    public static void CerrarFicheroBinario(DataOutputStream dos) {
        try {
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * funcio que leer? tanto el numero del boleto como el premio que tiene 
     * @param dis
     * @return 
     */
    public static boleto LeerNumerosSorteo(DataInputStream dis) {
        boleto blt = new boleto();

        try {
            blt.numero = dis.readInt();
            blt.premio = dis.readInt();

        } catch (IOException ex) {
            blt = null;
        }
        return blt;
    }
    
    /**
     * funcion que usaremos para comprobar que premio tiene el numero introducido
     * por el usuario
     */
    public static void ComprobarNumeroBinario() {
        try {
            String nombre = "Sorteig" + any + ".bin";
            DataInputStream dis = AbrirFicheroLecturaBinario(nombre, true);

            boleto boletoIntroducido = new boleto();
            numLinea = DIECIOCHO;
            boletoIntroducido.numero = Entero(LeerLineaIdioma());
            boolean trobat = false;
            boleto blt = LeerNumerosSorteo(dis);
            while (trobat != true) {

                if (blt.numero == boletoIntroducido.numero) {
                    numLinea++;
                    LeerLineaIdioma();
                    System.out.println(blt.premio + " ?");
                    trobat = true;
                } else {
                    blt = LeerNumerosSorteo(dis);
                }
            }
            CerrarFicheroBinarioInput(dis);
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * funcion que nos sirve para crear un DataInput para leer un archivo binario
     * @param nomFichero
     * @param crear
     * @return 
     */
    public static DataInputStream AbrirFicheroLecturaBinario(String nomFichero, boolean crear) {
        DataInputStream dis = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero?
            FileInputStream reader;
            try {
                reader = new FileInputStream(f);
                // PrintWriter para poder escribir m?s comodamente
                dis = new DataInputStream(reader);
            } catch (IOException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dis;
    }

    
    /**
     * funcion que, en caso de no existir el sorteo del a?o seleccionado, crear?
     * un nuevo archivo y simular? el sorteo. Si ya existe simplemente lo 
     * abrir?
     * @param nomFichero
     * @param crear
     * @return 
     */
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
    
    /**
     * funcion con la que grabaremos cada boleto que se genere en la simulacion
     * @param numeros
     * @param i
     * @param any 
     */
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
    
    /**
     * funcion que nos permite cerrar un fichero binario en el que hemos escrito
     * @param dos 
     */
    public static void CerrarFicheroBinarioOutput(DataOutputStream dos) {
        try {
            dos.flush();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * funcion que abrir? el fichero y nos devolver? un DataOutputStream para
     * poder escribir en un archivo
     * @param nomFichero
     * @param crear
     * @param blnAnyadir
     * @return 
     */
    public static DataOutputStream AbrirFicheroEscrituraBinario(String nomFichero, boolean crear, boolean blnAnyadir) {
        DataOutputStream dos = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el writer para poder escribir en el fichero?
            FileOutputStream writer;
            try {
                writer = new FileOutputStream(f, blnAnyadir);
                // PrintWriter para poder escribir m?s comodamente
                dos = new DataOutputStream(writer);
            } catch (IOException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return dos;
    }

    //A?adir parametros amigos
    public static class Amigo {

        String nombre;
        int anySorteig;
        int numLoteria;
        double importe;

        Amigo(String nombre, int numLoteria, int anySorteig, double importe) {
            this.nombre = nombre;
            this.anySorteig = anySorteig;
            this.numLoteria = numLoteria;
            this.importe = importe;
        }
    }
    /**
     * variables globales para las collas
     */
    static String nombre;
    static int anySorteig;
    static int numLoteria;
    static List<Amigo> amigos;
    static double importe;
    static double importeTotal;

    public void Colla(String nombre, int anySorteig) {
        this.nombre = nombre;
        this.anySorteig = anySorteig;
        amigos = new ArrayList<Amigo>();
        importeTotal = 0;
    }

    // Metodo para a?adir un amigo a la colla
    public static void addAmigo(String nombre, int anySorteig, int numLoteria, double importe) {
        amigos.add(new Amigo(nombre, anySorteig, numLoteria, importe));
        importeTotal += importe;
    }
    /**
     * Esta funcion nos sirve para gestionar las diferentes opciones
     */
    
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
    /**
     * funcion para abrir el buffered reader y poder leer un archivo de texto
     * @param nomFichero
     * @param crear
     * @return 
     */
    public static BufferedReader AbrirFicheroLectura(String nomFichero, boolean crear) {
        BufferedReader br = null;
        File f = AbrirFichero(nomFichero, crear);

        if (f != null) {
            // Declarar el reader para poder leer el fichero?
            FileReader reader;
            try {
                reader = new FileReader(f);
                // Buffered reader para poder leer m?s comodamente
                br = new BufferedReader(reader);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return br;
    }
    
    /**
     * funcion para cerrar el buffered reader
     * @param br 
     */
    public static void CerrarFichero(BufferedReader br) {
        try {
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * funcion con diferentes lectores para los diferentes idiomas
     */
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
        System.out.println("1-Espa?ol");
        System.out.println("2-Catal?");
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
            } while (numLinea < CINCO + 1);
            opcion = scan.nextInt();

            opcionesMenu();

        } catch (IOException ex) {
            Logger.getLogger(Loteria_Nadal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Si escull l'opci? de canviar d'idioma
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

    //Si escull l'opci? del sorteig
    public static void opcionSorteo() {
        numLinea = DIEZ + 1;
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

    // Metodo para comprobar los premios de la colla
    // Metodo para crear una colla a partir de los datos introducidos por el usuario
    public static void opcionGrupo() {
        numLinea = VEINTE;

        try {

            LeerLineaIdioma();
            String nombre = scan.next();

            numLinea++;
            LeerLineaIdioma();
            int anySorteig = Integer.parseInt(scan.next());

            boolean seguirAnadiendo = true;
            while (seguirAnadiendo) {

                numLinea = VEINTIDOS;
                LeerLineaIdioma();
                String nombreAmigo = scan.next();

                numLinea++;
                LeerLineaIdioma();
                System.out.println(nombreAmigo);
                int numLoteria = Integer.parseInt(scan.next());

                numLinea++;
                LeerLineaIdioma();
                System.out.println(nombreAmigo);
                double importe = Double.parseDouble(scan.next());
                while (importe <= CINCO || importe >= SESENTA || importe % CINCO != 0) {
                    numLinea = VEINTE + CINCO;
                    LeerLineaIdioma();
                    importe = Double.parseDouble(scan.next());
                }

                numLinea = VEINTE + CINCO + 1;
                LeerLineaIdioma();
                String respuesta = scan.next();
                seguirAnadiendo = respuesta.equals("s");
            }
           
            importeTotal = importe;
            //Print collas
            System.out.println();
            System.out.println("+===============================================================+");
            System.out.println("|\tANY\t|\tMEMBRES\t|\tDINERS\t|\tPREMI\t|");
            System.out.println("+===============================================================+");

            System.out.printf("|\t%-5s\t|\t%-5d\t|\t%-5s\t|\t%-5s\t|\n", anySorteig, 2, importeTotal, 10);
            System.out.println("+===============================================================+");

            System.out.println("+===============================================================+");
            System.out.println("|\tNOM\t|\tNUMERO\t|\tDINERS\t|\tPREMI\t|");
            System.out.println("+===============================================================+");

            System.out.printf("|\t%-5s\t|\t%-5d\t|\t%-5s\t|\t%-5s\t|\n", nombre, numLoteria, importe, 10);
            System.out.println("+===============================================================+");
            System.out.println();

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

        LeerFichero();
        MenuIdioma();
        while (opcion != CINCO - 1) {
            Menu();
        }

    }

}