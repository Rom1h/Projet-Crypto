package main.java.com.MV;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import main.java.com.MV.Instruction.TypeInstruction;
import main.java.com.example.Circuit.Circuit;
import main.java.com.example.Circuit.Node;

public class VirtualMachine {
    private boolean[] variablesAlice;
    private boolean[] variablesBob;
    private Stack<Boolean> bufferAtoB;
    private Stack<Boolean> bufferBtoA;
    private LinkedList<Instruction> instrA;
    private LinkedList<Instruction> instrB;
    private Random random;

    public VirtualMachine(int n) {
        variablesAlice = new boolean[n+4];
        variablesBob = new boolean[n+2];
        bufferAtoB=new Stack<>();
        bufferBtoA=new Stack<>();
        instrA=new LinkedList<>();
        instrB=new LinkedList<>();
        random = new Random();
    }

    // Instruction: rnd()
    public boolean rnd() {
        return random.nextBoolean();
    }

    // Instruction: push(x)
    public void push(boolean value,Stack<Boolean> buff) {
            buff.push(value);
           
    }

    // Instruction: pop()
    public boolean pop(Stack<Boolean> buff) {
        boolean value = buff.pop();


        return value;
    }

    // Instruction: push(x1, x2, x3, x4)
    public void push(boolean x1, boolean x2, boolean x3, boolean x4,Stack<Boolean> buff) {
        buff.push(x1);
        buff.push(x2);
        buff.push(x3);
        buff.push(x4);
        
    }

    // Instruction: pop(x1, x2)
    public boolean pop(boolean y1, boolean y2,Stack<Boolean> buff) {
        
       // Supposons que le tampon contient quatre valeurs booléennes x1, x2, x3, x4 dans cet ordre.
       boolean x4 = buff.pop();
       boolean x3 = buff.pop();
       boolean x2 = buff.pop();
       boolean x1 = buff.pop();

       boolean notY1 = !y1;
       boolean notY2 = !y2;

       boolean result = (y1 && x2) ^ (notY1 && x1) ^ (y2 && x4) ^ (notY2 && x3);

       return result;
    }

    public void testPop2(){
        Stack<Boolean> buff=new Stack<>();
        buff.push(false);
        buff.push(false);
        buff.push(false);
        buff.push(true);
        System.out.println(pop(true,false,buff));

    }

    public void executeInst(){
        int indA=0;
        int indB=0;
        
        while(indA<instrA.size()||indB<instrB.size()){
            
            if(indA<instrA.size()){
                if(executeInstrA(instrA.get(indA))){
                    indA++;
                }
            }
            if(indB<instrB.size()){
                if(executeInstrB(instrB.get(indB))){
                    indB++;
                }
            }
       
        }
    }

    public boolean executeInstrA(Instruction inst){
        switch (inst.getType()) {
            case AFF:
                ((Affectation)inst).compile(variablesAlice);
               
                return true;
            case AFFPOP:
                Pop pop=((Affectation)inst).getPop();
               if(pop.getType() ==TypeInstruction.POP){
                if(bufferBtoA.isEmpty()){return false;}
                boolean res=pop(bufferBtoA);
                variablesAlice[((Affectation)inst).getInd()]=res;

               }
               else{
                if(bufferBtoA.size()<4){return false;}
                
                boolean res= pop(variablesAlice[pop.getInd()[0]],
                                variablesAlice[pop.getInd()[1]],
                                bufferBtoA);
                
                variablesAlice[((Affectation)inst).getInd()]=res;
               }
               break;
            case PUSH :
                Push push=(Push)inst;

                push(variablesAlice[push.getInd()[0]],bufferAtoB);
                break;
            
                   
            case PUSH4 :
            Push push4=(Push)inst;
           
            push(
                variablesAlice[push4.getInd()[0]],
                variablesAlice[push4.getInd()[1]],
                variablesAlice[push4.getInd()[2]],
                variablesAlice[push4.getInd()[3]],
                bufferAtoB);
    
            break;
        default : return true;
           
        }
        return true;
    }

    public boolean executeInstrB(Instruction inst){
        switch (inst.getType()) {
            case AFF:
                ((Affectation)inst).compile(variablesBob);
                return true;
            case AFFPOP:
                Pop pop=((Affectation)inst).getPop();
               if(pop.getType() ==TypeInstruction.POP){
                if(bufferAtoB.isEmpty())return false;
                boolean res=pop(bufferAtoB);
                variablesBob[((Affectation)inst).getInd()]=res;
               
               }
               else{
                if(bufferAtoB.size()<4)return false;
                boolean res=pop(variablesBob[pop.getInd()[0]],variablesBob[pop.getInd()[1]],bufferAtoB);
                variablesBob[((Affectation)inst).getInd()]=res;
               }
               break;
            case PUSH :
            
                Push push=(Push)inst;
                if(push.getType()==TypeInstruction.PUSH){
                    push(variablesBob[push.getInd()[0]],bufferBtoA);
                }
                else{
                    push(variablesBob[push.getInd()[0]],
                        variablesBob[push.getInd()[1]],
                        variablesBob[push.getInd()[2]],
                        variablesBob[push.getInd()[3]],
                        bufferBtoA);
                }
                break;
            default : return true;
           
        }
        return true;
    }

    public static void execute(Circuit circuit,boolean[] valueA,boolean[] valueB){
        VirtualMachine vm =new VirtualMachine(circuit.getSize());
        //On initalise les valeurs INA de Alice
        for(int i = 0;i<valueA.length;i++){
            vm.variablesAlice[i]=valueA[i];
        }
        
        //On initalise les valeurs INB de Bob
        for(int i = 0;i<valueB.length;i++){
            vm.variablesBob[valueA.length+i]=valueB[i];
        }

        //On effectue le tri topologique du circuit
        List<Node> ctrie = Circuit.getListTrie(circuit);
        
        //On créé génére les instructions de Alice et Bob
        CircuitCompiler.compileCircuit(circuit.getSize(), ctrie, vm.instrA, vm.instrB);
        vm.executeInst();//On execute les instructions
        
        int indiceOutA=circuit.getSize()-valueA.length*2;//On récupere l'indice des noeuds de sortie de Alice
        
        int k=0;

        System.out.println("Resultat Alice");
        for(int i =indiceOutA;i<circuit.getSize()-valueA.length;i++){
            System.out.println("OutA"+k+" : " +vm.variablesAlice[i]);    
            k++;
        }
        
        int indiceOutB=circuit.getSize()-valueB.length;

        System.out.println("Resultat Bob");
        k=0;
        for(int i =indiceOutB;i<circuit.getSize();i++){
            System.out.println("OutB"+k+" : " +vm.variablesBob[i]);  
            k++;
        }
        
    }
    
    public static void main(String[] args) {
       
        System.out.println("Test min(01011010,00011010)");
        Circuit min=Circuit.minCircuit(8);
        boolean[] valA={false,true,false,true ,true,false,true,false};
        boolean[] valB={false,false,false,true,true ,false,true,false};
        execute(min, valA, valB);//test pour l'execution de la machine virtuelle pour la fonction min(01011010,00011010)

        System.out.println("Test a<=b pour a=1 et b=0");
        Circuit isMin=Circuit.generateCircuit();
        boolean[] valA1={true};
        boolean[] valB1={false};
        execute(isMin, valA1, valB1);//test pour la fonction valA1<=valB2
        
        System.out.println("Test a<=b pour a=0 et b=1");
        boolean[] valA2={false};
        boolean[] valB2={true};
        execute(isMin, valA2, valB2);

    }
}
