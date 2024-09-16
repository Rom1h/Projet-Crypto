package main.java.com.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import cryptoprojet.myproject.src.main.java.com.example.AES;
import main.java.com.example.Circuit.Circuit;
import main.java.com.example.Circuit.Node;
import main.java.com.example.Circuit.TypeNoeud;

public class BobsEval {
    public Circuit circ;
    public CircuitAlice prepAlice;
    public LinkedList<SecretKey> keyList;

    public BobsEval (Circuit circuit , CircuitAlice prepAlice) // a partir de la Bob a la cle d'alice dans prepAlice toute les TB et la cle de BOB
    throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        this.circ = circuit;
        this.prepAlice = prepAlice;
        this.keyList = new LinkedList<SecretKey>();
        this.keyList.add(prepAlice.shareKey);

        for (Node node : circ.getCircuit().values()) {
            if (node.type == TypeNoeud.INB) {
                int b = generateRandom();
                System.out.println("Bob pick "+b );
                String arg = ObliviousTransferRSA.OT(AES.secretKeyToString(node.K0), AES.secretKeyToString(node.K1), b);
                SecretKey BobKey = AES.stringToSecretKey(arg);
                this.keyList.add(BobKey);
                
                break;
            }
        }
    }


    public static int generateRandom() {
        Random random = new Random();
        return random.nextInt(2); 
    }
    public void eval_circuit_brouiller (){
        for (Node noeud : circ.getCircuit().values()) {
            if(!(noeud.type == TypeNoeud.INA || noeud.type == TypeNoeud.INB || noeud.type == TypeNoeud.OUTA|| noeud.type == TypeNoeud.OUTB)){
                Circuitbrouille c = find(noeud);
                String s = decryptCircuit(c,noeud);
                noeud.evaluation = s;
            }
        }
        for (Node node : circ.getCircuit().values()) {
            for (Node outputNode : node.output) {
                if (outputNode.type == TypeNoeud.OUTA || outputNode.type == TypeNoeud.OUTB) {
                    System.out.println("evaluation pour ce circuit "+node.evaluation);
                    break;
                }
            }
    }
        
    }
    public Circuitbrouille find (Node n ){
        for (Circuitbrouille circuit :prepAlice.listCircuitBrouille) {
            if(circuit.inputs == n.inputs)
                return circuit;
        }
        return null;
    }

    private String checkDecryptedValue(String decryptedText, Node n) {
        
        if (decryptedText.equals("0") || decryptedText.equals("1")) {
            return decryptedText;
        }
    
        String[] messages = { n.msg0, n.msg1 };
        IvParameterSpec[] vectors = { n.vecteur1, n.vecteur0 };
    
        for (String msg : messages) {
            for (IvParameterSpec vector : vectors) {
                try {
                    SecretKey key = AES.stringToSecretKey(decryptedText);
                    String decryptedMessage = AES.decrypt(msg, key, vector);
                    if (decryptedMessage.equals("0") || decryptedMessage.equals("1")) {
                        this.keyList.add(key);
                        return decryptedMessage;
                    }
                } catch (Exception e) {
                    // Ignorer les exceptions
                }
            }
        }
    
        return null;
    }
    private String decrypt2nd(String cipherText, SecretKey primaryKey, SecretKey secondaryKey, Node n) {
        IvParameterSpec[] vecteurs = {n.inputs.get(0).vecteur0, n.inputs.get(0).vecteur1};
        for (IvParameterSpec vecteur : vecteurs) {
            try {
                String decryptedText = AES.decrypt(cipherText, secondaryKey, vecteur);
                if(decryptedText.equals("0") || decryptedText.equals("1")){
                    this.keyList.add(secondaryKey);
                }
                String result = checkDecryptedValue(decryptedText, n);

                if (result != null ) {
                    return result;
                }
            } catch (Exception e) {
                // Handle or log exception
            }
        }
        return null; // No valid result found
    }
    public String decryptCircuit(Circuitbrouille c, Node n) {
        try {
            for (String chaine : c.circuitbrouille) {
                if (c.type == TypeNoeud.NOT) {
                    for (SecretKey key : keyList) {
                
                        try {
                            String test1 = AES.decrypt(chaine, key, n.inputs.get(0).vecteur1);
                            String test2 = AES.decrypt(chaine, key, n.inputs.get(0).vecteur0);
                
                            String result = checkDecryptedValue(test1, n);
                            if (result != null) {
                                return result;
                            }
                
                            result = checkDecryptedValue(test2, n);
                            if (result != null) {
                                return result;
                            }
                
                        } catch (Exception e) {}
                    }
                }else if(c.type == TypeNoeud.AND){
                    
                    for (SecretKey secretKey : keyList) {
                        try {
                            String test1_part1 = AES.decrypt(chaine, secretKey, n.inputs.get(1).vecteur0);
                            String test1_part1a = AES.decrypt(chaine, secretKey, n.inputs.get(1).vecteur1);
                            for (SecretKey secondaryKey : keyList) {
                                // Try decryptions with each combination
                                String result = decrypt2nd(test1_part1, secretKey, secondaryKey, n);
                                if (result == null) {
                                    result = decrypt2nd(test1_part1a, secretKey, secondaryKey, n);
                                }
                                if (result != null) {
                                    return result; // Return the first successful result
                                }
                            }
                        } catch (Exception e) {}
                    }
                }else{
                    for (SecretKey secretKey : keyList) {
                        try {
                            String test1_part1 = AES.decrypt(chaine, secretKey, n.inputs.get(1).vecteur0);
                            String test1_part1a = AES.decrypt(chaine, secretKey, n.inputs.get(1).vecteur1);
                            for (SecretKey secondaryKey : keyList) {
                                // Try decryptions with each combination
                                String result = decrypt2nd(test1_part1, secretKey, secondaryKey, n);
                                if (result == null) {
                                    result = decrypt2nd(test1_part1a, secretKey, secondaryKey, n);
                                }
                                if (result != null) {
                                    return result; // Return the first successful result
                                }
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // ou toute autre action en cas d'erreur
        }
    
    return null;
    }
    
}
