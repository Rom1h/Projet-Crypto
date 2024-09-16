package main.java.com.MV;

public class Pop extends Instruction {
    private int[] ind;

    Pop(){
        super(TypeInstruction.POP);
    }

    Pop(int ind1,int ind2){
        super(TypeInstruction.POP2);
        ind=new int[]{ind1,ind2};
    }

    public int[] getInd(){
        return ind;
    }

    public String toString(){
        switch (getType()) {
            case POP:
                return "Pop()";
            
            default:
                return "Pop("+ind[0]+","+ind[1]+")";
        }
    }
    
   
}
