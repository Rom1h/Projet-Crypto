package main.java.com.example.Circuit;

public class BinaryGate extends Node{
    public BinaryGate(String label, Node input1, Node input2, TypeNoeud type) {
        super(label);
        this.inputs.add(input1);
        this.inputs.add(input2);
        this.type = type;
        input1.output.add(this);
        input2.output.add(this);
    }
    
    @Override
    public boolean getOutPutValue() {
        boolean val1 = inputs.get(0).getValue();
        boolean val2 = inputs.get(1).getValue();
        
        switch(type) {
            case AND:
                outputValue=val1 && val2;
                break;          
            case XOR:
                outputValue=val1 ^ val2;
                break;
            default:
                return false;
        }
        return outputValue;
    }

}   
