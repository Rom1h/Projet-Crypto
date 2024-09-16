package cryptoprojet.myproject.src.main.java.com.example;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AES {
    KeyGenerator keyGenerator;

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String input, SecretKey key,IvParameterSpec iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
        //initialisation du protocole de chiffrement
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        byte[] counter= iv.getIV();//initialisation du compteur

        byte[] text=input.getBytes();//on convertit le text en bytes

        byte[] encryptText = new byte[input.length()];

        //On chiffre le texte par bloc de 16 bits
        for(int i =0;i<input.length();i+=16){
            // Chiffrer le compteur
            byte[] cipherBlock = cipher.doFinal(counter);
            //xor du block de texte avec le compteur
            int blockLength = Math.min(16, text.length - i);
            for (int j = 0; j < blockLength; j++) {
                encryptText[i + j] = (byte)((text[i + j] ^ cipherBlock[j]));
            }
            // Incrémenter le compteur
            for (int k = counter.length - 1; k >= 0; --k) {
                if (++counter[k] != 0) {
                    break; // Incrément réussi, pas de débordement
                }
            }
        }
        
        return Base64.getEncoder().encodeToString(encryptText);
    }
        // Convertir un SecretKey en chaîne de caractères Base64
        public static String secretKeyToString(SecretKey secretKey) {
            byte[] keyBytes = secretKey.getEncoded(); // Obtenir les octets de la clé
            return Base64.getEncoder().encodeToString(keyBytes); // Convertir en Base64
        }
    
        // Convertir une chaîne Base64 en SecretKey
        public static SecretKey stringToSecretKey(String keyString) {
            byte[] keyBytes = Base64.getDecoder().decode(keyString); // Décoder Base64
            return new javax.crypto.spec.SecretKeySpec(keyBytes,"AES"); // Recréer la clé
        }

    public static String decrypt(String input, SecretKey key,IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,InvalidAlgorithmParameterException, InvalidKeyException,BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] counter= iv.getIV();
        byte[] text=Base64.getDecoder().decode(input);
        byte[] cipherText = new byte[input.length()];
        for(int i =0;i<input.length();i+=16){
            // Chiffrer le compteur
            byte[] cipherBlock = cipher.doFinal(counter);
            //xor du block de texte avec le compteur
            int blockLength = Math.min(16, text.length - i);
            for (int j = 0; j < blockLength; j++) {
                cipherText[i + j] = (byte)(text[i + j] ^ cipherBlock[j]);
            }
            // Incrémenter le compteur
            for (int k = counter.length - 1; k >= 0; --k) {
                if (++counter[k] != 0) {
                    break; // Incrément réussi, pas de débordement
                }
            }
        }
        
        return new String(cipherText, StandardCharsets.UTF_8).trim();
    }

    public static void testAES(String message){
        try {
            SecretKey key= generateKey();
            IvParameterSpec vector=generateIv();
            String encrypt=encrypt(message, key, vector);
            String decrypt=decrypt(encrypt, key, vector);
            
            System.out.println("Message Originale : "+message
                +"\nMessage crypté : "+encrypt 
                +"\nMessage decrypté : "+decrypt);

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }

}