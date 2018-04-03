/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author Usuario
 */
public class GrupoTokens {
   private String tokens;
   private short cantidad;
    
    public GrupoTokens(){
     tokens = "";
     cantidad = 0;
    }
    
    public void setTokens(String to){ tokens = to; }
    public String getTokens(){ return tokens; } 
    
    public void setCantidad(){ cantidad++; }
    public short getCantidad(){ return cantidad; }
    
}
