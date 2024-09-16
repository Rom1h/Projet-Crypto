package main.java.com.example;
import main.java.com.example.RSA;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObliviousTransferRSA {
    private static Random rand = new Random();
    private static RSA rsa = new RSA();
    private static BigInteger n = rsa.getPublicKey();
    private static BigInteger k_val;
    private static int b,nMessages ;

    // PrÃ©paration d'Alice (message x0....xn)
    public static BigInteger[] aliceGenereMessage(int nbMessages){
        nMessages = nbMessages;
        BigInteger[] x = new BigInteger[nbMessages];
        for (int i = 0; i < nbMessages; i++) {
            x[i] = generateRandomBigInteger(n);
        }
        return x;
    }
    public static BigInteger[] aliceGenereMessageString(String[] messages) {
        nMessages = messages.length;
        BigInteger[] x = new BigInteger[nMessages];
        for (int i = 0; i < nMessages; i++) {
            x[i] = new BigInteger(messages[i].getBytes(StandardCharsets.UTF_8));
        }
        return x;
    }
    // chiffrement des messages 
    public static BigInteger[] aliceChiffreMessage(BigInteger[] x){
        BigInteger[] encryptedX = new BigInteger[nMessages];
        for (int i = 0; i < nMessages; i++) {
            encryptedX[i] = rsa.encryptMsg(x[i]); 
            /*
            System.out.println("Alice envoie le message " + x[i]);
            System.out.println("chiffre " + encryptedX[i]);
            System.out.println("------------------------------------------------");
            */
        }
        return encryptedX;
    }
    public static int bobChoisieB(){
        b = rand.nextInt(nMessages);
        return b;// Selectionne aleatoirement un index de message entre 0 et n-1
    }
    public static BigInteger bobCalculeV(BigInteger [] encryptedX){
        BigInteger k = generateRandomBigInteger(n); // Bob choisit un nombre alÃ©atoire k
        k_val = k;
        BigInteger ke = k.modPow(rsa.getExponent(), n); // Calcul de k^e mod n
        BigInteger v = encryptedX[b].add(ke); // Bob envoie le message chiffrÃ© + k^e Ã  Alice
        return v;
    }
    public static BigInteger [] aliceCalculeKi(BigInteger [] encryptedX,BigInteger v){
        BigInteger[] kis = new BigInteger[nMessages];
        for (int i = 0; i < nMessages; i++) {
            kis[i] = rsa.decryptMsg(v.subtract(encryptedX[i])); // Calcul de ki = (v - xi)^d
        }
        return kis;
    }
    public static BigInteger[] aliceEnvoieMprime(BigInteger [] x,BigInteger [] kis){
        BigInteger[] m = new BigInteger[nMessages];
        for (int i = 0; i < nMessages; i++) {
            m[i] = x[i].add(kis[i]); 
        }
        return m;
    }
    public static BigInteger bobDechiffreMessage(BigInteger [] m){
        return m[b].subtract(k_val); // Calcul du message dÃ©chiffrÃ© par Bob
    }
    public static String bobDechiffreMessageString(BigInteger[] m) {
        BigInteger decrypted = m[b].subtract(k_val); // Calcul du message dÃ©chiffrÃ© par Bob
        return new String(decrypted.toByteArray(), StandardCharsets.UTF_8); // Convertir de BigInteger Ã  String
    }
    public static void loopTest(){
        BigInteger [] message = aliceGenereMessage(5);
        BigInteger [] encryptedX = aliceChiffreMessage(message);
        b = bobChoisieB();
        BigInteger v = bobCalculeV(encryptedX);
        BigInteger [] kis = aliceCalculeKi(encryptedX, v);
        BigInteger [] m = aliceEnvoieMprime(message, kis);
        System.out.println("Bob : "+ bobDechiffreMessage(m));
        
    }
    public static String OT(String x1, String x2, int bob) {
        String[] messages = {x1, x2};
        BigInteger[] message = aliceGenereMessageString(messages);
        BigInteger[] encryptedX = aliceChiffreMessage(message);
        b = bob;
        BigInteger v = bobCalculeV(encryptedX);
        BigInteger[] kis = aliceCalculeKi(encryptedX, v);
        BigInteger[] m = aliceEnvoieMprime(message, kis);
        String decryptedMessage = bobDechiffreMessageString(m);
        return decryptedMessage;
    }


    private static BigInteger generateRandomBigInteger(BigInteger n) {
        BigInteger result;
        do {
            result = new BigInteger(n.bitLength(), rand);
        } while (result.compareTo(n) >= 0 || result.equals(BigInteger.ZERO));
        return result;
    }
}