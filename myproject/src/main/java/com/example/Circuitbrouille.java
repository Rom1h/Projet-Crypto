package main.java.com.example;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;


import main.java.com.example.Circuit.Node;
import main.java.com.example.Circuit.TypeNoeud;
import cryptoprojet.myproject.src.main.java.com.example.AES;

public class Circuitbrouille {
    public TypeNoeud type;
    public ArrayList<Node> inputs;
    public Node noeud;
    public ArrayList<String> circuitbrouille ;



    public Circuitbrouille(Node noeud){
        this.noeud = noeud;
        this.inputs = noeud.inputs;
        type = noeud.type;
        circuitbrouille = new ArrayList<String>();
        genereTB();
        Collections.shuffle(circuitbrouille);

    }

    public void genereTB (){
        try {
            boolean bool = false;
            for (Node outputNode : noeud.output) {
                if (outputNode.type == TypeNoeud.OUTB) {
                    bool = true;
                    break;
                }
            }
            if(!(noeud.type == TypeNoeud.INA || noeud.type == TypeNoeud.INB || noeud.type == TypeNoeud.OUTA|| noeud.type == TypeNoeud.OUTB)){
                if (bool){//si le output contion outb
                    if (noeud.type == TypeNoeud.NOT ){
                        String s = AES.encrypt("0",noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                        String ss = AES.encrypt("1",noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                        circuitbrouille.add(s);
                        circuitbrouille.add(ss);
                    }else if (noeud.type == TypeNoeud.AND){
                        String s = AES.encrypt("0",noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                        String ss = AES.encrypt(s,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur0);
                        circuitbrouille.add(ss);

                        String s2 = AES.encrypt("0",noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                        String ss2 = AES.encrypt(s2,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur0);
                        circuitbrouille.add(ss2);

                        String s3 = AES.encrypt("0",noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                        String ss3 = AES.encrypt(s3,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                        circuitbrouille.add(ss3);

                        String s4 = AES.encrypt("1",noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                        String ss4 = AES.encrypt(s4,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                        circuitbrouille.add(ss4);
                    }else{
                        String s = AES.encrypt("0",noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                        String ss = AES.encrypt(s,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur1);
                        circuitbrouille.add(ss);

                        String s2 = AES.encrypt("1",noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                        String ss2 = AES.encrypt(s2,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur0);
                        circuitbrouille.add(ss2);

                        String s3 = AES.encrypt("1",noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                        String ss3 = AES.encrypt(s3,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                        circuitbrouille.add(ss3);

                        String s4 = AES.encrypt("0",noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                        String ss4 = AES.encrypt(s4,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                        circuitbrouille.add(ss4);
                    }

        
                }
                else if (noeud.type == TypeNoeud.NOT ){
                    String s = AES.encrypt(AES.secretKeyToString(noeud.K0),noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                    String ss = AES.encrypt(AES.secretKeyToString(noeud.K1),noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                    circuitbrouille.add(s);
                    circuitbrouille.add(ss);
                }
                else if (noeud.type == TypeNoeud.AND){
                    String s = AES.encrypt(AES.secretKeyToString(noeud.K0),noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                    String ss = AES.encrypt(s,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur0);
                    circuitbrouille.add(ss);

                    String s2 = AES.encrypt(AES.secretKeyToString(noeud.K0),noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                    String ss2 = AES.encrypt(s2,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur0);
                    circuitbrouille.add(ss2);

                    String s3 = AES.encrypt(AES.secretKeyToString(noeud.K0),noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                    String ss3 = AES.encrypt(s3,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                    circuitbrouille.add(ss3);

                    String s4 = AES.encrypt(AES.secretKeyToString(noeud.K1),noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                    String ss4 = AES.encrypt(s4,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                    circuitbrouille.add(ss4);
                }
                else{
                    String s = AES.encrypt(AES.secretKeyToString(noeud.K0),noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                    String ss = AES.encrypt(s,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur0);
                    circuitbrouille.add(ss);

                    String s2 = AES.encrypt(AES.secretKeyToString(noeud.K1),noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                    String ss2 = AES.encrypt(s2,noeud.inputs.get(1).K0,noeud.inputs.get(1).vecteur0);
                    circuitbrouille.add(ss2);

                    String s3 = AES.encrypt(AES.secretKeyToString(noeud.K1),noeud.inputs.get(0).K0,noeud.inputs.get(0).vecteur0);
                    String ss3 = AES.encrypt(s3,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                    circuitbrouille.add(ss3);

                    String s4 = AES.encrypt(AES.secretKeyToString(noeud.K0),noeud.inputs.get(0).K1,noeud.inputs.get(0).vecteur1);
                    String ss4 = AES.encrypt(s4,noeud.inputs.get(1).K1,noeud.inputs.get(1).vecteur1);
                    circuitbrouille.add(ss4);
        
                }
            }else{
                this.type = null;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
            InvalidKeyException | InvalidAlgorithmParameterException |
            IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chiffrement : " + e.getMessage());
        }
    }












}
