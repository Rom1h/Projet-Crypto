package main.java.com.MV;
import java.util.Random;

public class Expression {
    private int ind1;
    private int ind2;
    private ExpressionType type;
    public enum ExpressionType{
        XOR,AND,RND,NEG,VAL
    }
    public Expression(ExpressionType type,int ind1,int ind2){
        this.type=type;
        this.ind1=ind1;
        this.ind2=ind2;
    }
    public boolean evaluate(boolean[] tab){
        switch (type) {
            case VAL:
                return tab[ind1];
            case NEG :
                return !tab[ind1];
            case XOR :
                return tab[ind1]^tab[ind2];
            case AND :
                return tab[ind1] && tab[ind2];
            case RND : 
                Random random = new Random();
                return random.nextBoolean();
        }
        return false;
    }
    public int getind1(){
        return ind1;
    }
    public int getind2(){
        return ind2;
    }
    public String toString(){
        switch (type) {
            case VAL:
                return ""+ind1;
            case NEG :
                return ""+ind1+"+1";
            case XOR :
                return ind1+"+"+ind2;
            case AND :
                return ind1+"x"+ind2;
            case RND :
                return "rand()";
        }
        return null;
    }
}
