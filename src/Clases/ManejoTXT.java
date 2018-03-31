/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

/**
 *
 * @author igeni
 */
public class ManejoTXT {
    FileOutputStream salida;
    public DefaultListModel leerArchivo(String ruta, DefaultListModel modelo){
        FileReader fr = null;
        BufferedReader br = null;
        int contlinea = 1;
        modelo.clear();
        //Cadena de texto donde se guardara el contenido del archivo
        try
        {
            //ruta puede ser de tipo String o tipo File
            //si usamos un File debemos hacer un import de esta clase
            //File ruta = new File( "/home/usuario/texto.txt" );
            fr = new FileReader( ruta );
            br = new BufferedReader( fr );
  
            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while( ( linea = br.readLine() ) != null )
            {
                modelo.addElement(contlinea + "    " + linea);
                contlinea++;
            }
        }catch( Exception e ){  }
        //finally se utiliza para que si todo ocurre correctamente o si ocurre
        //algun error se cierre el archivo que anteriormente abrimos
        finally
        {
            try{
                br.close();
            }catch( Exception ex ){}
        }
        //Se imprime el contenido
        return modelo;
    }
    
    public void leerArchivo(String ruta, JTextArea textarea)
    {
        FileReader fr = null;
        BufferedReader br = null;
        String contenido = "";
        boolean saltar = false;
        //Cadena de texto donde se guardara el contenido del archivo
        try
        {
            //ruta puede ser de tipo String o tipo File
            //si usamos un File debemos hacer un import de esta clase
            //File ruta = new File( "/home/usuario/texto.txt" );
            fr = new FileReader( ruta );
            br = new BufferedReader( fr );
  
            String linea;
            
            //Obtenemos el contenido del archivo linea por linea
            while( ( linea = br.readLine() ) != null )
            {
                if (saltar == true)
                    contenido += "\n";
                contenido += linea;
                saltar = true;
            }
            textarea.setText(contenido);
        }catch( Exception e ){  }
        //finally se utiliza para que si todo ocurre correctamente o si ocurre
        //algun error se cierre el archivo que anteriormente abrimos
        finally
        {
            try{
                br.close();
            }catch( Exception ex ){}
        }
    }
    
    public String ModificarArchivo(File archivo, String contenido)
    {
        String respuesta = null;
        try{
            salida = new FileOutputStream(archivo);
            byte[] bytesTxt = contenido.getBytes();
            salida.write(bytesTxt);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ManejoTXT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManejoTXT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
        
    }
}