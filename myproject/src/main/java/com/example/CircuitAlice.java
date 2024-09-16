package main.java.com.example;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cryptoprojet.myproject.src.main.java.com.example.AES;
import main.java.com.example.Circuit.Circuit;
import main.java.com.example.Circuit.TypeNoeud;
import main.java.com.example.Circuit.Node;
import java.util.Random;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.IvParameterSpec;

public class CircuitAlice {
   private static Circuit circuit;
   public ArrayList<Circuitbrouille> listCircuitBrouille = new ArrayList<Circuitbrouille>();
   public SecretKey shareKey;
   /*
      premier clef de alice 
    * list<tableBrouiller>
      
    */

    public CircuitAlice(Circuit circuit){
        this.circuit = circuit;
        Ajout_des_clef(generateRandom());
        prep_circuit_brouiller();
    }
    public static int generateRandom() {
        Random random = new Random();
        return random.nextInt(2); 
    }
    public void Ajout_des_clef(int n ) {
        try {
            for (Node node : circuit.getCircuit().values()) {
                boolean bool = false;
                // Vérifier si le nœud est une sortie ou connecté à une sortie
                if (node.type == TypeNoeud.OUTA || node.type == TypeNoeud.OUTB) {
                    bool = true;
                } else {
                    for (Node outputNode : node.output) {
                        if (outputNode.type == TypeNoeud.OUTA || outputNode.type == TypeNoeud.OUTB) {
                            bool = true;
                            break;
                        }
                    }
                }
    
                if (!bool) {
                    // Première clé
                    SecretKey key0 = AES.generateKey();
                    IvParameterSpec vector0 = AES.generateIv();
                    String message0 = "0";
                    String encrypt0 = AES.encrypt(message0, key0, vector0);
                    node.K0 = key0;
                    node.vecteur0 = vector0;
                    node.msg0 = encrypt0;
                    if(node.type == TypeNoeud.INA && n == 0) {
                        System.out.println("Alice pick 0");
                        shareKey = node.K0;
                    }; 
                    // Deuxième clé
                    SecretKey key1 = AES.generateKey();
                    IvParameterSpec vector1 = AES.generateIv();
                    String message1 = "1";
                    String encrypt1 = AES.encrypt(message1, key1, vector1);
                    node.K1 = key1;
                    node.vecteur1 = vector1;
                    node.msg1 = encrypt1;
                    if(node.type == TypeNoeud.INA && n == 1) {
                        System.out.println("Alice pick 1");
                        shareKey = node.K1; 
                    }
                }
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException |
                 BadPaddingException e) {
            e.printStackTrace(); // Affiche la trace de l'erreur pour le débogage
            System.out.println("Erreur lors de l'ajout des clés: " + e.getMessage());
        }
    }
    
    public static String generateRandomBinaryString() {
        // Utilise SecureRandom pour générer un nombre aléatoire (0 ou 1)
        SecureRandom random = new SecureRandom();
        int binaryDigit = random.nextInt(2); // Génère soit 0 soit 1
        
        // Convertit le chiffre en chaîne de caractères
        String binaryString = String.valueOf(binaryDigit);
        
        return binaryString;
    }
    public void prep_circuit_brouiller(){
        for (Node node : circuit.getCircuit().values()) {
            Circuitbrouille c = new Circuitbrouille(node);
            listCircuitBrouille.add(c);
        }
    }

}
