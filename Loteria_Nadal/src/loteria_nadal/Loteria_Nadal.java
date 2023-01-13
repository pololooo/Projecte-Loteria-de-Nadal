package loteria_nadal;

import java.util.*;
import java.util.Random;
public class Loteria_Nadal {
    
    static final int NUMPREMIOS = 100000;
    static int NUMGORDO;
    static final String PREMIOGORDO = "400.000€";
    static final String PREMIOSEGUNDO = "125.000€";
    static final String PREMIOTERCERO = "50.000€";
    static final String PREMIOCUARTO = "20.000€";
    static final String PREMIOQUINTO = "6.000€";
    static final String PREMIOPEDREA = "1.000€";
    static boleto[] numeros = new boleto[NUMPREMIOS];
    
    public static class boleto{
        int numero;
        String premio;
        
    }
    
    public static void simulacion(){
        Random ra = new Random();
        for(int i=1; i<NUMPREMIOS;i++){
            numeros[i] = new boleto();
          if(i==1){
              
              NUMGORDO = ra.nextInt(NUMPREMIOS);
              numeros[i].numero = NUMGORDO;
              numeros[i].premio = PREMIOGORDO;
          }  
          else if(i==2){
             
              int numero = ra.nextInt(NUMPREMIOS);
              numeros[i].numero = numero;
              numeros[i].premio = PREMIOSEGUNDO; 
          }
          else if(i==3){
              
              int numero = ra.nextInt(NUMPREMIOS);
              numeros[i].numero = numero;
              numeros[i].premio = PREMIOTERCERO;
          }
          else if(i==4 || i == 5){
              int numero = ra.nextInt(NUMPREMIOS);
              numeros[i].numero = numero;
              numeros[i].premio = PREMIOCUARTO;
          }
          else if(i<15){
              int numero = ra.nextInt(NUMPREMIOS);
              numeros[i].numero = numero;
              numeros[i].premio = PREMIOQUINTO;
          }
          else if(i<1808){
              int numero = ra.nextInt(NUMPREMIOS);
              numeros[i].numero = numero;
              numeros[i].premio = PREMIOPEDREA;
          }
          else{
              int numero = ra.nextInt(NUMPREMIOS);
              numeros[i].numero = numero;
              numeros[i].premio = "No te premi";
          }
                
            
        }
        
    }
    
    public static void main(String[] args) {
        
        
        
    }
    
}
