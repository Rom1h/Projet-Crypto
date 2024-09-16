package main.java.com.example.Circuit;

public class OutputNode extends Node{

    public OutputNode(String name,Node input) {
        super(name);
        this.inputs.add(input);
        input.output.add(this);
        if(name.toLowerCase().startsWith("outa"))type=TypeNoeud.OUTA;
        else type=TypeNoeud.OUTB;
        
        //TODO Auto-generated constructor stub
        
    }
    @Override
    public boolean getOutPutValue(){
        outputValue= inputs.get(0).getValue();
        return outputValue;
    }
    
}
