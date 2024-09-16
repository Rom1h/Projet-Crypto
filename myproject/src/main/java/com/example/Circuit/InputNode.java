package main.java.com.example.Circuit;

public class InputNode extends Node {
    public InputNode(String name) {
        super(name);
        if(name.startsWith("InA")) type = TypeNoeud.INA;
        else type = TypeNoeud.INB;
         // Extraire l'indice en utilisant substring
        if (name.length() > 3) { 
            String indexPart = name.substring(3); // Extraire tout après les trois premiers caractères
            try {
                setIndice(Integer.parseInt(indexPart)); // Convertir la partie numérique en entier
            } catch (NumberFormatException e) {
                System.out.println("Format de nombre incorrect dans le nom du nœud: " + name);
            }
        }
    }
    public void setOutputVal(boolean val){
        outputValue=val;
    }
    @Override
    public boolean getOutPutValue(){
        return outputValue;
    }
    
}
