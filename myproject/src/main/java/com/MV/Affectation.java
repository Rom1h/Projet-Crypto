package main.java.com.MV;


public class Affectation extends Instruction {
    private int indice;
    private Expression exp;
    private Pop pop;
    public Affectation(int indice,Expression exp){
        super(TypeInstruction.AFF);
        this.indice=indice;
        this.exp=exp;
    }

    public Affectation(int indice,Pop pop){
        super(TypeInstruction.AFFPOP);
        this.indice=indice;
        this.pop=pop;
    }

    public void compile(boolean[] tab){
        tab[indice]=exp.evaluate(tab);
    }
    public Pop getPop(){
        return pop;
    }
    public int getInd(){
        return indice;
    }

    public Expression getExpression(){
        return exp;
    }
    public String toString(){
        switch (getType()) {
            case AFF:
                return "X"+indice+"="+exp.toString();
                
            default:
                return "X"+indice+"=" +pop.toString();
            
        }
        
    }
    
}
