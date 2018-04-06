
package Clases;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author root
 */
public class ManejoErrores {
    private ArrayList<GrupoTokens> Mayusculas;
    private ArrayList<GrupoTokens> Minusculas;
    private ArrayList<GrupoTokens> Guion;
    private ArrayList<GrupoTokens> PalabrasReser;
    private ArrayList<GrupoTokens> Numeros;
    private ArrayList<GrupoTokens> Operador;
    private ArrayList<GrupoTokens> Signos;
    
    
    public ManejoErrores(){            
            Mayusculas = new ArrayList<>();
            Minusculas = new ArrayList<>();
            Guion = new ArrayList<>();
            PalabrasReser = new ArrayList<>();
            Numeros = new ArrayList<>();
            Signos = new ArrayList<>();
            Operador = new ArrayList<>();
           // InsertarPalabrasReservadas();       
    }
    
    //Funcion que verifica, si hay que descomponer o no
    public boolean BuscarErrores(String examinar){
    
      if(examinar.length() > 1){
       char[] var =  new char[3];
       var[0] = examinar.charAt(0);
       var[1] = examinar.charAt(examinar.length() - 2);
       var[2] = examinar.charAt(examinar.length() - 1); 
     
       if(verificar(var)){
         
          if(TokensIdentificador(examinar))
             return true;
          else 
              return false;
                   
       }
       else 
             return DescomponerTokens(examinar);
      }
      else
             return InsertarCaracter(examinar.charAt(0));
          
    }
    
    //Metodo que verifica si es un tokens o un identificador
    private boolean TokensIdentificador(String examinar){
        
         boolean estado = false; 
         boolean encontrado = false;
         String contrario = examinar.toLowerCase();
          
          short index = 0;
          //Busca si la palabra mandada es una palabra reservada
          if(Escaneo(examinar)){
              
                while(index < PalabrasReser.size() && !encontrado){
                
                        if(examinar.equals(PalabrasReser.get(index).getTokens())){
                               PalabrasReser.get(index).setCantidad();
                               encontrado = true;
                         }
                        else if(contrario.equals(PalabrasReser.get(index).getTokens())){
                                    index = (short)PalabrasReser.size();
                                    estado = true;
                         }
              
                  index++;
               }
                          
                if(!encontrado && !estado){
                     //Insertando un identificador
             
                  if(Escaneo(examinar)){
                     InsercionIdentificador(examinar);
                     return true;
                   }
                  else
                   return false;
               
                }
                else if(estado)//Si hay un tokens mal escrito          
                   return false;  
          }
          else 
              return DescomponerTokens(examinar);
          
        return encontrado;
    }
  
    
   //Funcion que inserta un indentificador en los arrays
    private void InsercionIdentificador(String tokens){
   
        if( tokens.charAt(0) > 64 || tokens.charAt(0) < 91){//Identificador con inicial mayuscula       
             InsertarDatoArray(Mayusculas,tokens);
         }
        else if(tokens.charAt(0) > 96 || tokens.charAt(0) < 123){//Indetificador con inicial minuscula          
             InsertarDatoArray(Minusculas,tokens);
        }
        else if(tokens.charAt(0) == 95){//Identificador con inicial _
              InsertarDatoArray(Guion,tokens);
        }
    }
    
    //Funcion que insertar tokens o identificadores
    private void InsertarArray(ArrayList<GrupoTokens> array,String tokens){
     GrupoTokens nuevo = new GrupoTokens();
     nuevo.setTokens(tokens);
     nuevo.setCantidad();
     array.add(nuevo);
    }
    
    //Metodo que escanea el indentificador, retorna true si son del mismo tipo
    //y un false si hay un caracter no correspondiente 
    private boolean Escaneo(String indentidicador){
       boolean estado = false;
       short index = 0;
   
              if((indentidicador.charAt(index) > 64 && indentidicador.charAt(index) < 91) || (indentidicador.charAt(index) > 96 && indentidicador.charAt(index) < 123 )
                || (indentidicador.charAt(index)== 95)){  
                  estado = true;
                  index++;
                     while(index < indentidicador.length() && estado){
                
                          if((indentidicador.charAt(index) > 64 && indentidicador.charAt(index) < 91) || (indentidicador.charAt(index) > 96 && indentidicador.charAt(index) < 123 )
                              || (indentidicador.charAt(index)== 95)|| (indentidicador.charAt(index) > 47 && indentidicador.charAt(index) < 58))
                                 estado = true;
                          else 
                            estado = false;
                        
                          index++;           
              }
                     
           }  
        
      return estado;
    }
    
    //Funcion que verifica si hay que descomponer la cadena 
    // Ej _identi --> Retorna true
    //Ej var12 o var=5; --> Retorna false
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
     return bool;
    }
    
    //Funcion que evalua la descomposicion de una cadena
    //Eje if(var1>=4) --> if,(,var1,>=,4,)
    private boolean DescomponerTokens(String tokens){
        
        short index = 0;
        ArrayList<GrupoTokens> auxliar = new ArrayList<>();
        String var = "";
        while(index < tokens.length()){
            
            if(Analizar(tokens.charAt(index))) var = var +tokens.charAt(index); //Analizar retorna true si son del mismo tipo
            else {
                //Si es lo contrario inserta el signo u operador, y el analizado
                  InsertarArray(auxliar, var);
                  boolean estado = false;
                  if(tokens.charAt(index) == 60){ //Evalua si es un <=
                      if( (index+1) != tokens.length()){ 
                          estado = tokens.charAt(index + 1) == 61?true:false; 
                          if(estado){ InsertarArray(auxliar,"<="); index++; }}
                  }
                  else if(tokens.charAt(index) == 61){ //Evalua si es un ==
                      if( (index+1) != tokens.length()){ 
                          estado = tokens.charAt(index + 1) == 61?true:false;
                          if(estado){ InsertarArray(auxliar,"=="); index++; }
                      }
                  }
                  else if(tokens.charAt(index) == 62){//Evalua si es un >=
                      if( (index+1)!= tokens.length()){ 
                          estado = tokens.charAt(index + 1) == 61?true:false;
                          if(estado){ InsertarArray(auxliar,">="); index++; }
                      }
                  }
                  
                  //Si no es ninguna de las anteriores, insertar el signo u operador
                  if(!estado)
                  InsertarArray(auxliar,tokens.charAt(index)+"");
                  
                  var = "";
              }
                  
            index++;
        }
        //Caundo la cadena no se descompone
        if(var.length() == tokens.length()){
            
            InsertarArray(auxliar, var);
        }
        else if(var.length() > 0)
            InsertarArray(auxliar, var);
        
        boolean estado = true;   
       index = 0;
       //Bucle que verifica donde va cada string encontrado
       //Retorna false si hay algun caracter no correspodiente al lenguaje
        while(index < auxliar.size() && estado){
            
            if(auxliar.get(index).getTokens().length() != 0){
                
                if(auxliar.get(index).getTokens().length() > 1){
                    if(!InsertarNumeroTokensOperador(auxliar.get(index).getTokens())) estado = false;
                }
                else{
                    if(!InsertarCaracter(auxliar.get(index).getTokens().charAt(0))) estado = false; }
                      
         
            }
      
            index++;
        }
    return estado;
    }
    
    //Funcion que inserta un caracter -->  (,b,3
    private boolean InsertarCaracter(char caracter){
      boolean estado = false;
        if((caracter > 64 && caracter < 91) || (caracter > 96 && caracter < 123)){//Insercion de una letra
            InsercionIdentificador(caracter+"");
            estado = true;
        }
        else if( caracter==34 || caracter==40 || caracter==41 || caracter==123 || caracter==125 || caracter==34 || caracter==44 || caracter==59){//Insercion (,),{,},",,
            
                InsertarDatoArray(Signos,caracter+"");
                estado = true;
        }
        else if( caracter==43 || caracter==45 || caracter==47 || caracter==42 || caracter==37 || caracter==60 || caracter==61 || caracter==62){//Insercion /,*,-,+,%,=,<,>
            
             InsertarDatoArray(Operador,caracter+"");
             estado = true;
        }
        else if(caracter > 47 && caracter < 58){//Insercion de 0-9
            
             InsertarDatoArray(Numeros,caracter+"");     
             estado = true;       
        }
        else 
            estado = false;
        
      return estado;    
    }
    
    //Funcion que inserta un Numero, Tokens, Operador
    private boolean InsertarNumeroTokensOperador(String dato){
        
        if(dato.charAt(0) > 47 && dato.charAt(0) < 58){//Si lo que se inserta son numeros
          if(EscaneoNumeros(dato)){
              
              InsertarDatoArray(Numeros,dato); //Inserta el numero al ArrayList numeros
                     
              return true;
          }
          else return false;
       }
       else if(dato.charAt(0) == 60 || dato.charAt(0)==61 || dato.charAt(0)==62){//Si son operadores >,=,<
            
            InsertarDatoArray(Operador,dato);
            
            return true;
        }
       else 
           return TokensIdentificador(dato);//Inserta un indentificador
    }
    
    //Funcion que inserta un elemento a los arraylist, cualquier array list
    private void InsertarDatoArray(ArrayList<GrupoTokens> array,String dato){
          if(array.size() > 0){
                     short index = 0;
                      while (index < array.size() && !array.get(index).getTokens().equals(dato)) index++; //Busca si esta en la coleccion
                
                      if(index < array.size() && array.get(index).getTokens().equals(dato)) array.get(index).setCantidad();
                      else InsertarArray(array, dato);   
                     
         }
         else InsertarArray(array,dato);
    }
    
    //Funcion que verifica si no hay un caracter diferente a la coleccion de numeros
    private boolean EscaneoNumeros(String dato){
      boolean estado = true;
      short index = 0;
         
      while(index < dato.length() && estado ){
        
          if(!(dato.charAt(index) > 47 && dato.charAt(index) < 58)) estado = false;
          
          index++;
      }
      
      return estado;  
    }
    
    //Funcion que verifica si hay que romper una cadena o son del mismo tipo
    private boolean Analizar(char caracter){
      
        if((caracter > 64 && caracter <91) || (caracter > 96 && caracter < 123) || caracter==95){//letras A,a
           return true;
        }
        else if(caracter > 47 &&  caracter < 58){//numeros
         return true;
        }
        else{
           switch(caracter){
               case 37://%
                   return false; 
               case 40: //(
                   return false;
               case 41://)
                   return false;
               case 42://*
                    return false;
               case 43: //+
                     return false;
               case 44: //,
                   return false;
               case 45://-
                   return  false;
               case 47:///
                   return false;
               case 59://;
                   return false;
               case 123://{
                   return false;
               case 125://}
                   return false;
               case 60://<
                   return false;
               case 61://=
                   return false;
               case 62://>
                   return false;
               case 38://&
                   return false;
               case 34://"
                   return false;
               default://Otro caracter que no esta en el lenguaje
                   return false;
           }
        }
     
    }
    
    //Funcion que Agrega los tokens,identificadores, numeros al defaulttablemodel 
    public DefaultTableModel mostrar(){
           
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Token");
        modelo.addColumn("Tipo");
        modelo.addColumn("Cantidad");
           
           short index = 0;
           while(index < PalabrasReser.size()){
               
               if(PalabrasReser.get(index).getCantidad() != 0)
                   modelo.addRow(new Object[]{PalabrasReser.get(index).getTokens(),"Palabra Reservada",PalabrasReser.get(index).getCantidad()});
          
               index++;
           }

           index = 0;
           while(index < Mayusculas.size()){
               modelo.addRow(new Object[]{Mayusculas.get(index).getTokens(),"Identificador",Mayusculas.get(index).getCantidad()});
               index++;
           }
           index = 0;
            while(index < Minusculas.size()){
              modelo.addRow(new Object[]{Minusculas.get(index).getTokens(),"Identificador",Minusculas.get(index).getCantidad()});
               index++;
           }
            index = 0;
           while(index < Guion.size()){
               modelo.addRow(new Object[]{Guion.get(index).getTokens(),"Identificador",Guion.get(index).getCantidad()});
               index++;
           }
           
           index = 0;
           while(index < Signos.size()){
              modelo.addRow(new Object[]{Signos.get(index).getTokens(),"Signos",Signos.get(index).getCantidad()});
               index++;
           }
           
           index = 0;
           while(index < Operador.size()){
               modelo.addRow(new Object[]{Operador.get(index).getTokens(),"Operador",Operador.get(index).getCantidad()});
               index++;
           }
           
           index = 0;
           while(index < Numeros.size()){
              modelo.addRow(new Object[]{Numeros.get(index).getTokens(),"Numeros",Numeros.get(index).getCantidad()});
               index++;
           }
          
     return modelo;
    }
    
    //Funcion que insertar las palabras reservadas al array list PalabrasReser
    private void InsertarPalabrasReservadas(){
      GrupoTokens v1,v2,v3,v4,v5,v6,v7,v8,v9,v10;
      
      v1 = new GrupoTokens(); v1.setTokens("int");
      PalabrasReser.add(v1);
      
      v2 = new GrupoTokens(); v2.setTokens("float");
      PalabrasReser.add(v2);
      
      v3 = new GrupoTokens(); v3.setTokens("bool");
      PalabrasReser.add(v3);
      
      v4 = new GrupoTokens(); v4.setTokens("string");
      PalabrasReser.add(v4);
      
      v5 = new GrupoTokens(); v5.setTokens("if");
      PalabrasReser.add(v5);
      
      v6 = new GrupoTokens(); v6.setTokens("else");
      PalabrasReser.add(v6);
      
      v7 = new GrupoTokens(); v7.setTokens("while");
      PalabrasReser.add(v7);
      
      v8 = new GrupoTokens(); v8.setTokens("do");
      PalabrasReser.add(v8);
      
      v9 = new GrupoTokens(); v9.setTokens("true");
      PalabrasReser.add(v9);
      
      v10 = new GrupoTokens(); v10.setTokens("false");
      PalabrasReser.add(v10);
      
    }
    
    //Funcion que renicia todos los arraylist utilizados
    public void ReinicioArrayList(){
            Mayusculas.clear();
            Minusculas.clear();
            Guion.clear();
            PalabrasReser.clear();
            Numeros.clear();
            Signos.clear();
            Operador.clear();
            InsertarPalabrasReservadas(); 
    }
    
}
