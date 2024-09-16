package main.java.com.MV;

public class Push extends Instruction {
    private int[] ind;
    Push(int ind) {
        super(TypeInstruction.PUSH);
        this.ind=new int[]{ind};
    }
    Push(int ind1,int ind2,int ind3,int ind4) {
        super(TypeInstruction.PUSH4);
        this.ind=new int[]{ind1,ind2,ind3,ind4};
    }
    public int[] getInd(){
        return ind;
    }
    public String toString(){
        switch (getType()) {
            case PUSH:
                return "Push("+ind[0]+")";
            
            default:
                return "Push("+ind[0]+","+ind[1]+","+ind[2]+","+ind[3]+")";
        }
    } 
    
}
