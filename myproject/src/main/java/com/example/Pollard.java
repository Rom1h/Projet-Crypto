package main.java.com.example;
import java.math.BigInteger;
import java.util.Random;

public class Pollard {
    public static BigInteger pollardFactor(BigInteger n) {
        BigInteger x = BigInteger.valueOf(2);
        BigInteger y = BigInteger.valueOf(2);
        BigInteger d = BigInteger.ONE;
        
        while (d.equals(BigInteger.ONE)) {
            x = f(x, n);
            y = f(f(y, n), n);
            d = x.subtract(y).gcd(n);
        }
        
        return d;
    }
    
    private static BigInteger f(BigInteger x, BigInteger n) {
        return x.pow(2).add(BigInteger.ONE).mod(n);
    }
    public static BigInteger generateRandomBigInteger(int bitLength) {
        Random random = new Random();
        return new BigInteger(bitLength, random);
    }
    public static void testPollardWithRandomNumbers(int numberOfTests, int bitLength) {
        for (int i = 0; i < numberOfTests; i++) {
            BigInteger randomNum = generateRandomBigInteger(bitLength);
            System.out.println("Nombre aleatoire sur 128 bits " + randomNum);
            try {
                BigInteger factor = pollardFactor(randomNum);
                System.out.println("One factor of " + randomNum + " is " + factor);
            } catch (Exception e) {
                System.out.println("Failed to factorize " + randomNum);
            }
        }
    }
    
}


