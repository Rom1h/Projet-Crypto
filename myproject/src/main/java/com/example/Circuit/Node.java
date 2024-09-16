package main.java.com.example.Circuit;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.SecretKey;
public abstract class Node {
    private int indice;
    public TypeNoeud type;
    public String name;
    public ArrayList<Node> output;
    public ArrayList<Node> inputs;
    public boolean outputValue;
    public String instructionA;
    public String instructionB;
    public String msg0, msg1,evaluation;
    public SecretKey K0 ,K1;
    public IvParameterSpec vecteur0,vecteur1;
    public Node(String name){
        this.name=name;
        inputs=new ArrayList<>();
        output=new ArrayList<>();
    
    }
    //Noeud pour circuit VM;
    public Node(String name,String instrA,String instrB){
        this.name=name;
        inputs=new ArrayList<>();
        this.instructionA=instrA;
        this.instructionB=instrB;
    }
    public void setValue(boolean b){
        outputValue=b;
    }
    public boolean getValue(){
        return outputValue;
    }
    public String getName(){
        return name;
    }

    public void setIndice(int ind){
        this.indice=ind;
    }

    public int getIndice(){
        return indice;
    }
    public TypeNoeud getType(){
        return type;
    }
    public abstract boolean getOutPutValue();
}
