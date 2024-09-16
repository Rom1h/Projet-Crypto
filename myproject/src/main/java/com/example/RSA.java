package main.java.com.example;

import java.math.BigInteger;
import java.util.Random;

public class RSA{
    private static Random rand = new Random();
    private static BigInteger partKey_p, partKey_q,e; //exposant de chiffrement; d = exposant d echiffrement
    private static BigInteger[] euclide;
    public static BigInteger key;   // define la clef publique

    public RSA(){
        genKey();
    }

    public static void genKey(){
        // genere des cles partielles p & q autant que p ou q sont pas premiers
        do {
            partKey_p = new BigInteger(128, 1, rand);
            partKey_q = new BigInteger(128, 1, rand);
            
        } while (!(partKey_p.isProbablePrime(1) && partKey_q.isProbablePrime(1)));


        key = partKey_p.multiply(partKey_q);
    }

    public static BigInteger encryptMsg(BigInteger msg){
        BigInteger psyDeKey = (partKey_p.subtract(BigInteger.ONE)).multiply(    // (p-1)*(q-1) avec p et q premier entre eux
                               partKey_q.subtract(BigInteger.ONE));
           // 1 <= e <= psy(n)          
        do{       
            e = new BigInteger(psyDeKey.bitCount(), rand).add(BigInteger.ONE);
            euclide = euclideEtendu(e, psyDeKey);
        } while (!euclide[0].equals(BigInteger.ONE));
        
        BigInteger encryptedMSG = msg.modPow(e, key);   // msg^e mod n
        return encryptedMSG;
    }

    public static BigInteger decryptMsg(BigInteger eMsg){
        BigInteger d = euclide[1];
        BigInteger decryptedMsg = eMsg.modPow(d, key);
        return decryptedMsg;
    }

    // verifie si les deux valeurs passé en paramêtre son prémiers entre eux 
    private static BigInteger[] euclideEtendu(BigInteger a, BigInteger b){
        BigInteger[] result = new BigInteger[3];

        if(a.equals(BigInteger.ZERO)){
            result[0] = b;
            result[1] = BigInteger.ZERO;
            result[2] = BigInteger.ONE;
        }else{
            BigInteger[] tmp = euclideEtendu(b.mod(a), a);
            BigInteger gcd = tmp[0];
            BigInteger x = tmp[1];
            BigInteger y = tmp[2];

            result[0] = gcd; 
            result[1] = y.subtract(b.divide(a).multiply(x));
            result[2] = x;
        }
        return result;
    }
    public static BigInteger getPublicKey() {
        return key;
    }

    public static BigInteger getExponent() {
        return e; // Retourne l'exposant de chiffrement
    }

    public static void testRSA(int nbTests, int bitLenghts){
        new RSA();
        for (int i = 0; i < nbTests; i++) {
            BigInteger rndMsg = new BigInteger(bitLenghts, new Random());
            BigInteger eMsg = encryptMsg(rndMsg);
            BigInteger dMsg = decryptMsg(eMsg);

            if (!dMsg.equals(rndMsg)) {
                System.out.println("RSA failed in message: " + rndMsg);
            }else{
                System.out.println("RSA succed in test:" + i +"\nmessage: " + rndMsg 
                                    + "\nencrypt message: " + eMsg + "\ndecrypt message: " + dMsg);
            }
        }
    }
}