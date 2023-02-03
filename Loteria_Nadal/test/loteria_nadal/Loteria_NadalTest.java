/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package loteria_nadal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author oscar
 */
public class Loteria_NadalTest {
    
    public Loteria_NadalTest() {
        
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of calcularPremio method, of class Loteria_Nadal.
     */
    @Test
    public void testCalcularPremio() {
        System.out.println("calcularPremio");
        
        int numero = 0;
        int expResult = 0;
        int result = Loteria_Nadal.calcularPremio(numero);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of darNumero method, of class Loteria_Nadal.
     */
    
    @Test
    public void testDarNumero() {
        System.out.println("darNumero");
        int expResult = 0;
        int result = Loteria_Nadal.darNumero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of generarNumero method, of class Loteria_Nadal.
     */
    @Test
    public void testGenerarNumero() {
        System.out.println("generarNumero");
        int expResult = 0;
        int result = Loteria_Nadal.generarNumero();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of simulacion method, of class Loteria_Nadal.
     */
    @Test
    public void testSimulacion() {
        System.out.println("simulacion");
        Loteria_Nadal.simulacion();
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of buscarPremio method, of class Loteria_Nadal.
     */
    @Test
    public void testBuscarPremio() {
        System.out.println("buscarPremio");
        Loteria_Nadal.boleto boletoIntroducido = null;
        int expResult = 0;
        int result = Loteria_Nadal.buscarPremio(boletoIntroducido);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of Entero method, of class Loteria_Nadal.
     */
    @Test
    public void testEntero() {
        System.out.println("Entero");
        String missatge = "";
        int expResult = 0;
        int result = Loteria_Nadal.Entero(missatge);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of ComprobarTam method, of class Loteria_Nadal.
     */
    @Test
    public void testComprobarTam() {
        Loteria_Nadal.boleto boleto = new Loteria_Nadal.boleto();
        System.out.println("ComprobarTam");
        boleto.numero = 123456;
        boolean expResult = false;
        boolean result = Loteria_Nadal.ComprobarTam(boleto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of llenarNums method, of class Loteria_Nadal.
     */
    @Test
    public void testLlenarNums() {
        System.out.println("llenarNums");
        Loteria_Nadal.llenarNums();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of mostrarPremis method, of class Loteria_Nadal.
     */
    @Test
    public void testMostrarPremis() {
        System.out.println("mostrarPremis");
        Loteria_Nadal.mostrarPremis();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of IntroduirNum method, of class Loteria_Nadal.
     */
    @Test
    public void testIntroduirNum() {
        System.out.println("IntroduirNum");
        Loteria_Nadal.boleto boletoIntroducido = null;
        Loteria_Nadal.IntroduirNum(boletoIntroducido);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of main method, of class Loteria_Nadal.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Loteria_Nadal.main(args);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
