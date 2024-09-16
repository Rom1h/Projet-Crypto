package main.java.com.example.Circuit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class Circuit {
    Map<String, Node> circuit;
    
    public Circuit(){
        circuit=new HashMap<>();
    }

    public void addNode(Node n, String key) {
        circuit.put(key, n);
    }
    
    public Node getNode(String key) {
        Node node = circuit.get(key);
        return node;
    }
    public Map<String, Node> getCircuit(){
        return circuit;
    }
    public int getSize(){
        return circuit.size();
    }
    
    public static List<Node> getListTrie(Circuit circuit) {
        List<Node> nodesInTopologicalOrder = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        // Tri topologique
        for (Node node : circuit.getCircuit().values()) {
            if (!visited.contains(node)) {
                topologicalSort(node, visited, nodesInTopologicalOrder);
            }
        }
    
        // Divisez la liste en trois parties : INA, INB, Gate , OutA, OutB
        List<Node> inaNodes = new ArrayList<>();
        List<Node> inbNodes = new ArrayList<>();
        List<Node> otherNodes = new ArrayList<>();
        List<Node> outaNodes = new ArrayList<>();
        List<Node> outbNodes = new ArrayList<>();
        for (Node node : nodesInTopologicalOrder) {
            if (node.getType() == TypeNoeud.INA) {
                inaNodes.add(node);
            } 
            else if (node.getType() == TypeNoeud.INB) {
                inbNodes.add(node);
            } 
            else if (node.getType() == TypeNoeud.OUTA) {
                outaNodes.add(node);
            }
            else if (node.getType() == TypeNoeud.OUTB) {
                outbNodes.add(node);
            }
            else {
                otherNodes.add(node);
            }
        }
        // Tri les indices des noeuds d'entrée
        Collections.sort(inaNodes, Comparator.comparing(Node::getIndice));
        Collections.sort(inbNodes, Comparator.comparing(Node::getIndice));
    
        List<Node> finalList = new ArrayList<>();
        finalList.addAll(inaNodes);
        finalList.addAll(inbNodes);
        finalList.addAll(otherNodes);
        finalList.addAll(outaNodes);
        finalList.addAll(outbNodes);
    
        return finalList;
    }
    public static Map<String,Boolean> evaluateCircuit(Map<String, Node> circuit, Map<String, Boolean> inputValues) {
        List<Node> nodesInTopologicalOrder = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        // Tri topologique
        for (Node node : circuit.values()) {
            if (!visited.contains(node)) {
                topologicalSort(node, visited, nodesInTopologicalOrder);
            }
        }

        
        // Évaluation des noeuds dans l'ordre topologique
        Map<String, Boolean> outputValues = new HashMap<>();

        for (Node node : nodesInTopologicalOrder) {
            if (node.inputs.isEmpty()) {
                // Nœud d'entrée
                node.setValue(inputValues.get(node.name));
                outputValues.put(node.name,node.getValue());
            }
            else{
                node.getOutPutValue();
                outputValues.put(node.name,node.getValue());
            }
        }
        return outputValues;    
        
    }
    public static void afficheCircuit(Circuit circuit){
        for(Node node : circuit.circuit.values()){
            System.out.println(node.getName()+" -> val :"+node.getValue());
        }
    }
    
    public static void afficheHashMap(Map<String,Boolean> circuit){
        for (Map.Entry<String, Boolean> entry : circuit.entrySet()) {
            // Afficher la clé et la valeur
            System.out.println(entry.getKey() + "-> "  + entry.getValue());
        }
    }
    private static void topologicalSort(Node node, Set<Node> visited, List<Node> nodesInTopologicalOrder) {
        visited.add(node);
        for (Node input : node.inputs) {
            if (!visited.contains(input)) {

                topologicalSort(input, visited, nodesInTopologicalOrder);
            }
        }
        nodesInTopologicalOrder.add(node);
    }
    public static Circuit generateCircuit(){
        Circuit circuit=new Circuit();
       
        InputNode inA=new InputNode("InA");
        InputNode inb=new InputNode("InB");
        NotGate not=new NotGate("Not", inA);
        BinaryGate and=new BinaryGate("And", inA, inb, TypeNoeud.AND);
        BinaryGate xor=new BinaryGate("Xor", not, and, TypeNoeud.XOR);
        OutputNode outA=new OutputNode("outA", xor);
        OutputNode outB=new OutputNode("outB", xor);
        circuit.addNode(inA,"InA");
        circuit.addNode(inb, "InB");
        circuit.addNode(not, "Not");
        circuit.addNode(and, "And");
        circuit.addNode(xor, "Xor");
        circuit.addNode(outA, "outA");
        circuit.addNode(outB, "outB");
        return circuit;
    }
    /*Le circuit verifie pour chaque bits si 
    ils sont strictement inférieur et si c'est le cas il verifie si les bits précedents sont tous egaux*/
    public static Circuit minCircuit(int n){
        Circuit min=new Circuit();
       // Création des noeuds d'entrée pour A et B
        InputNode[] inA = new InputNode[n];
        InputNode[] inB = new InputNode[n];
        for (int i = 0; i < n; i++) {
            inA[i] = new InputNode("InA" + i);
            inB[i] = new InputNode("InB" + i);
            min.addNode(inA[i], inA[i].getName());
            min.addNode(inB[i], inB[i].getName());
        }
        
        NotGate[] notA = new NotGate[n];
        BinaryGate[] andAB = new BinaryGate[n];
        Node[] lastEq=new Node[n];//on verifie si les bits précedents sont egaux 
        Node[] res=new Node[n]; // stock le résultat
        for (int i = 0; i <n; i++) {
            // Création des portes pour A < B
            notA[i] = new NotGate("NotA" + i, inA[i]);
            andAB[i] = new BinaryGate("AndAB" + i, notA[i], inB[i], TypeNoeud.AND);
            min.addNode(notA[i], notA[i].getName());
            min.addNode(andAB[i], andAB[i].getName());
            
            if (i == 0) {
                BinaryGate xor=new BinaryGate("xor"+i, inA[i], inB[i],TypeNoeud.XOR);
                //NotGate notXor=new NotGate("notEq"+i, xor);
                lastEq[i]=new NotGate("lastEq"+i, xor);
                res[i]=andAB[i];
                min.addNode(xor, xor.getName());
                min.addNode(lastEq[i], lastEq[i].getName());
            } else {
                BinaryGate xor=new BinaryGate("xor"+i, inA[i], inB[i], TypeNoeud.XOR);
                NotGate not=new NotGate("notEq"+i, xor);
                lastEq[i]=new BinaryGate("lastEq"+i, lastEq[i-1], not, TypeNoeud.AND);
                BinaryGate and1=new BinaryGate("and"+i, lastEq[i-1],andAB[i] ,TypeNoeud.AND);
                NotGate notAndRes=new NotGate("notAndRes"+i, and1);
                NotGate notAnd=new NotGate("notGate"+(i-1), res[i-1]);
                BinaryGate and2=new BinaryGate("resAnd"+i, notAndRes, notAnd, TypeNoeud.AND);
                res[i]=new NotGate("or"+i, and2);
                min.addNode(xor, xor.getName());
                min.addNode(not, not.getName());
                min.addNode(lastEq[i], lastEq[i].getName());
                min.addNode(and1, and1.getName());
                min.addNode(and2, and2.getName());
                min.addNode(notAndRes, notAndRes.getName());
                min.addNode(notAnd, notAnd.getName());
                min.addNode(res[i], res[i].getName());


            }
        }
        
        for(int i =0;i<n;i++){
            BinaryGate andResA=new BinaryGate("andResA"+i, res[n-1], min.getNode("InA"+i), TypeNoeud.AND);
            NotGate notB=new NotGate("notB"+i, res[n-1]);
            BinaryGate andResB=new BinaryGate("andResB"+i,min.getNode("InB"+i),notB,TypeNoeud.AND);
            BinaryGate xorRes=new BinaryGate("xorRes"+i, andResA, andResB, TypeNoeud.XOR);
            OutputNode outA=new OutputNode("OutA"+i,xorRes);
            OutputNode outB=new OutputNode("OutB"+i,xorRes);
            min.addNode(andResA, andResA.getName());
            min.addNode(notB, notB.getName());
            min.addNode(andResB, andResB.getName());
            min.addNode(xorRes, xorRes.getName());
            min.addNode(outA, outA.getName());
            min.addNode(outB, outB.getName());


        }
        return min;
    }
    public static void testExhaustif(Circuit circuit, int n) {
        int totalCases = 1 << (2 * n); // 2^n * 2^n = 2^(2n)
        int correctCount = 0;
    
        for (int i = 0; i < totalCases; i++) {
            Map<String, Boolean> inputValues = new HashMap<>();
            
            // Configurer les valeurs d'entrée pour chaque bit
            for (int bit = 0; bit < n; bit++) {
                inputValues.put("InA" + bit, (i & (1 << bit)) != 0);
                inputValues.put("InB" + bit, (i & (1 << (bit + n))) != 0);
            }
    
            // Évaluer le circuit avec les valeurs d'entrée actuelles
            Map<String, Boolean> outputValues = evaluateCircuit(circuit.circuit, inputValues);
    
            // Convertir les valeurs binaires en entiers pour les comparaisons correctes
            int aInt = binaryToInt(inputValues, "InA", n);
            int bInt = binaryToInt(inputValues, "InB", n);
            boolean aIsLess = aInt < bInt;
    
        
    
            // Déterminer la sortie attendue pour chaque bit et vérifier si elle correspond
            boolean correct = true;
            for (int bit = 0; bit < n; bit++) {
                boolean expected = aIsLess ? inputValues.get("InA" + bit) : inputValues.get("InB" + bit);
                boolean actualOutputA = outputValues.get("OutA" + bit);
                boolean actualOutputB = outputValues.get("OutB" + bit);
               
                
                if (expected != actualOutputA || expected != actualOutputB) {
                    correct = false;
                    System.out.println("Échec sur le cas: A=" + inputValuesToString(inputValues, "InA", n) +
                                       ", B=" + inputValuesToString(inputValues, "InB", n) +
                                       " à bit " + bit + " (Attendu: " + expected + ", Obtenu: " + actualOutputA + ")");
                    break;
                }
            }
    
            if (correct) {
                correctCount++;
            }
        }
    
        System.out.println("Nombre de tests réussis: " + correctCount + " sur " + totalCases);
    }
    
    private static int binaryToInt(Map<String, Boolean> values, String prefix, int n) {
        int result = 0;
        // L'index des bits est inversé ici pour correspondre à votre spécification
        for (int bit = 0; bit < n; bit++) {
            int shift = (n - 1) - bit;  // Calcule le décalage nécessaire
            result |= (values.get(prefix + bit) ? 1 : 0) << shift;
        }
        return result;
    }
    public static void testBinaryToInt(){
        Map<String,Boolean> test=new HashMap<>();
        test.put("InA0", true);
        test.put("InA1", false);
        System.out.println(binaryToInt(test, "InA", 2));
    }
    
    private static String inputValuesToString(Map<String, Boolean> values, String prefix, int n) {
        StringBuilder sb = new StringBuilder();
        for (int bit = 0; bit < n; bit++) {
            sb.append(values.get(prefix + bit) ? '1' : '0');
        }
        return sb.toString();
    }    
    public static void main(String[] args) {
        // Set inputs and evaluate

        
        //Circuit min=minCircuit(8);
        //afficheHashMap(evaluateCircuit(min.circuit, inputValues));
        //afficheCircuit(min);
        // Créer le circuit pour min8
        Circuit min2 = minCircuit(8);

        // Tester toutes les combinaisons possibles pour min8
        testExhaustif(min2, 8);
        //testBinaryToInt();

    }
    

 

    
}
