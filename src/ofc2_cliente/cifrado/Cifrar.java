/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofc2_cliente.cifrado;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ResourceBundle;
import static javafx.application.Application.launch;
import javax.crypto.Cipher;

/**
 *
 * @author 2dam
 */
public class Cifrar {
 
     public String cifrarTexto(String mensaje) {
        byte[] encodedMessage = null;
        String password = null;
        try {
            // Recuperar Clave pública
            String publicKeyString=ResourceBundle.getBundle("ofc2_cliente.config.RESTful").getString("PublicKey");
            byte fileKey[] = hexStringToByteArray(publicKeyString);
            System.out.println("Tamaño Publica -> " + fileKey.length + " bytes");

            //Generamos una instancia de KeyFactory para el algoritmo RSA
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");//...;
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(fileKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            //Ciframos el mensaje con el algoritmo RSA modo ECB y padding PKCS1Padding
            Cipher cipher= Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            encodedMessage= cipher.doFinal(mensaje.getBytes());
            
            //convertimos la contrasenia en hexadecimal
            password= hexadecimal(encodedMessage);
            
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }
     
     public static String hexadecimal(byte[] encryptedText) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        
        for (int j = 0; j < encryptedText.length; j++) {
            buf.append(hexDigit[(encryptedText[j] >> 4) & 0x0f]);
            buf.append(hexDigit[encryptedText[j] & 0x0f]);
        }
         System.out.println("Hexadecimal: "+buf.toString());
        return buf.toString();
    }
    
     
     private byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
     
      private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
     public void generatePrivateKey() {

        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024); // Inicializamos el tama�o a 1024 bits
            KeyPair keypair = generator.generateKeyPair();
            PublicKey publicKey = keypair.getPublic(); // Clave P�blica
            PrivateKey privateKey = keypair.getPrivate(); // Clave Privada

            // Publica
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
            FileOutputStream fileOutputStream = new FileOutputStream("PublicKey.dat");
            fileOutputStream.write(x509EncodedKeySpec.getEncoded());
            fileOutputStream.close();
            
            // Privada
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            fileOutputStream = new FileOutputStream("PrivateKey.dat");
            fileOutputStream.write(pKCS8EncodedKeySpec.getEncoded());
            fileOutputStream.close();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
      public byte[] hexStringToByteArray(String password) {
        int len = password.length();
        byte[] mensajeByte = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            mensajeByte[i / 2] = (byte) ((Character.digit(password.charAt(i), 16) << 4)
                    + Character.digit(password.charAt(i + 1), 16));
        }
        return mensajeByte;
    }
     
      public static void main(String[] args) {
        Cifrar c= new Cifrar();
        
        c.cifrarTexto("Prueba");
        //c.generatePrivateKey();
    }
      
}
