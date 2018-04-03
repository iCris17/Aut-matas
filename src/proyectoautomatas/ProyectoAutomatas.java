/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas;

import Clases.ManejoErrores;

/**
 *
 * @author igeni
 */
public class ProyectoAutomatas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      /* String hola  = "HolA";
       String ver = hola.toLowerCase();
        System.out.println("Palabra: "+ver);*/
        String cadena = "int _indentidicador false memoria float if Float",var;
        ManejoErrores manejo = new ManejoErrores();
        int index = 0;
    
        do{
            var = "";
           while(index < cadena.length() && cadena.charAt(index)!= 32){
             var = var + cadena.charAt(index);
             index++;
           }
           index++;
           
            if(manejo.BuscarErrores(var))
            System.out.println("La logica esta bien");
        else 
            System.out.println("Error en el analisis lexico");
           
        }while(index < cadena.length());
       
      
    }
    
}
