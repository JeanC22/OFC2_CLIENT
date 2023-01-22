/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.cifrado;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import javax.crypto.Cipher;

/**
 *
 * @author 2dam
 */
public class Cifrar {
 
     public byte[] cifrarTexto(String mensaje) {
        byte[] encodedMessage = null;
        try {
            // Recuperar Clave pública
            byte fileKey[] = ResourceBundle.getBundle("PropertiesFile").getString("PublicKey").getBytes();;
            System.out.println("Tamaño Publica -> " + fileKey.length + " bytes");

            //Generamos una instancia de KeyFactory para el algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");//...;
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            //Ciframos el mensaje con el algoritmo RSA modo ECB y padding PKCS1Padding
            Cipher cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage= cipher.doFinal(mensaje.getBytes());
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedMessage;
    }
}
