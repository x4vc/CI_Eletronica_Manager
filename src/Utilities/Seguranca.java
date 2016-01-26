/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author victorcmaf
 */
public class Seguranca {
    
    public static String encriptar(String Data) throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(Data.getBytes(StandardCharsets.UTF_8) );
        
        return encodedString;
        
    }
    
    public static String desencriptar(String Data) throws Exception {
        String decodedString;
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(Data);
        decodedString = new String(decodedByteArray);
        return decodedString;
        
    }
    
    public static String stringToMD5(String strSenha){
        String string= strSenha;     
        try {
            
            MessageDigest m;
            m = MessageDigest.getInstance("MD5");
            m.update(string.getBytes(),0,string.length()); 
            BigInteger i = new BigInteger(1, m.digest()); 

            //Formatando o resuldado em uma cadeia de 32 caracteres, completando com 0 caso falte 
            string = String.format("%1$032X", i); 
        }catch(NoSuchAlgorithmException e){ 
            e.printStackTrace(); 
        }             
        return string; 
    }
    
}
