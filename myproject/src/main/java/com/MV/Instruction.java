package main.java.com.MV;


public abstract class Instruction {
    public enum TypeInstruction{
        AFF,AFFPOP,PUSH,PUSH4,POP,POP2
    }
    private TypeInstruction type;
    
    Instruction(TypeInstruction type){
        this.type=type;
    }

    public TypeInstruction getType(){
        return type;
    }
    public abstract String toString();

}
