/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoautomatas;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Usuario
 */
public class ManejoErrores {
   
    private Connection conexion;
    
    public ManejoErrores(){
       try {
            conexion = DriverManager.getConnection("jdbc:mariadb://localhost:3303/tokens","root","felipe");
        } catch (SQLException ex) {
            Logger.getLogger(ManejoErrores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean BuscarErrores(String examinar){
      String auxiliar,contrario;
      auxiliar = "";
       char[] var =  new char[3];
       var[0] = examinar.charAt(0);
       var[1] = examinar.charAt(examinar.length() - 2);
       var[2] = examinar.charAt(examinar.length() - 1); 
      if(verificar(var)){
          try {
              
              auxiliar = ""+ examinar.charAt(0) + examinar.charAt(1);  
             PreparedStatement stm = conexion.prepareStatement("SELECT ntokens,ctokens FROM tokens WHERE ntokens LIKE '"+auxiliar+"%'"); //de esta forma tambien se obtiene el filtro SEGUNDA FORMA
            ResultSet rs = stm.executeQuery();
             /*  Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT ntokens FROM tokens");*/
         if(rs.next() && examinar.charAt(0) != 95){
            
             contrario = examinar.toLowerCase();
            if(examinar.equals(rs.getString(1))){
              stm = conexion.prepareStatement("UPDATE tokens SET ctokens = ? WHERE ntokens = ?");
              stm.setInt(1, rs.getInt(2) + 1);
              stm.setString(2, examinar);
              stm.executeUpdate();
            }
            else if(contrario.equals(rs.getString(1))){
             return false;
            }
                   
          }
         else {
             //AQUI SE PONE EL IDENTIFICADOR
           }
          } catch (SQLException ex) {
              Logger.getLogger(ManejoErrores.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      else
            System.out.println("Hay que descomponerlo");
      
     return true;
    }
    
    private boolean verificar(char[]  var){
     boolean bool = false;
      byte index = 0;

         while(index < var.length){
           
           if( (var[index] > 64 && var[index] < 91) || (var[index] > 96 && var[index] < 123 ) || var[index] == 95){
             bool = true;
            }
            else{
             bool = false;
             index = (byte)var.length;
            }
            
           index++; 
         }
         System.out.println(var);
     return bool;
    }
}
