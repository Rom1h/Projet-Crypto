package main.java.com.example.Circuit;

public class NotGate extends Node {
    public NotGate(String label,Node input) {
        super(label);
        this.inputs.add(input);
        input.output.add(this);
        type=TypeNoeud.NOT;
    }
    
    @Override
    public boolean getOutPutValue() {
        outputValue=!inputs.get(0).getValue();
        return outputValue;
    }
}
