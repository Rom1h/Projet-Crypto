package main.java.com.MV;
import java.util.List;

import main.java.com.MV.Expression.ExpressionType;
import main.java.com.example.Circuit.Node;
public class CircuitCompiler {
    public static void compileCircuit(int n,List<Node> circuit, List<Instruction> aliceCommands, List<Instruction> bobCommands) {
        int count = 0;
        Node input1;
        Node input2;
        for (Node node : circuit) {
            node.setIndice(count);
            // Instructions for Alice
            switch (node.type) {
                case INA: // IN A

                    aliceCommands.add(new Affectation(n, new Expression(Expression.ExpressionType.RND, n, n)));//new Affectation(n,new Expression("rnd"));
                    aliceCommands.add(new Affectation(n+1,new Expression(Expression.ExpressionType.XOR,n,node.getIndice())));
                    aliceCommands.add(new Push(n+1));
                    aliceCommands.add(new Affectation(node.getIndice(),new Expression(Expression.ExpressionType.VAL, n,n))); //new Affectation(node.getIndice(),n);
                    bobCommands.add(new Affectation(node.getIndice(),new Pop()));
                    break;
                case INB: // IN B

                    bobCommands.add(new Affectation(n, new Expression(ExpressionType.RND, n, n)));//new Affectation(n,new Expression("rnd"));
                    bobCommands.add(new Affectation(n+1,new Expression(ExpressionType.XOR,n,node.getIndice())));
                    bobCommands.add(new Push(n+1));
                    bobCommands.add(new Affectation(node.getIndice(),new Expression(ExpressionType.VAL, n, n))); 
                    Affectation affPop=new Affectation(node.getIndice(),new Pop());
                   
                    aliceCommands.add(affPop);
                    break;
                case NOT: // NOT
                    Node input=node.inputs.get(0);
                    Affectation aff=new Affectation(node.getIndice(),new Expression(ExpressionType.NEG,input.getIndice(), n));
                  
                    aliceCommands.add(aff);
                    bobCommands.add(new Affectation(node.getIndice(), new Expression(ExpressionType.VAL, input.getIndice(),n)));              
                    break;
                case AND: // AND

                    aliceCommands.add(new Affectation(n, new Expression(ExpressionType.RND, n, n)));
                    aliceCommands.add(new Affectation(n+1, new Expression(ExpressionType.RND, n, n)));
                    input1=node.inputs.get(0);
                    input2=node.inputs.get(1);
                    
                    aliceCommands.add(new Affectation(n+2,new Expression(ExpressionType.XOR, n, input1.getIndice())));
                    aliceCommands.add(new Affectation(n+3,new Expression(ExpressionType.XOR, n+1, input2.getIndice())));
                    aliceCommands.add(new Push(n,n+2,n+1,n+3));
                    aliceCommands.add(new Affectation(n, new Expression(ExpressionType.XOR, n, n+1)));
                    Affectation aff1 =new Affectation(node.getIndice(), new Expression(ExpressionType.AND, input1.getIndice(),input2.getIndice()));
                    Affectation aff2=new Affectation(node.getIndice(), new Expression(ExpressionType.XOR, node.getIndice(), n));

                    aliceCommands.add(aff1);
                    aliceCommands.add(aff2);
                    bobCommands.add(new Affectation(node.getIndice(),new Pop(input2.getIndice(),input1.getIndice())));
                    bobCommands.add(new Affectation(n, new Expression(ExpressionType.AND,input1.getIndice(),input2.getIndice())));
                    bobCommands.add(new Affectation(node.getIndice(), new Expression(ExpressionType.XOR, node.getIndice(),n)));
                    break;
                case XOR: // XOR
                    
                    input1=node.inputs.get(0);
                    input2=node.inputs.get(1);

                    aliceCommands.add(new Affectation(node.getIndice(), new Expression(ExpressionType.XOR, input1.getIndice(),input2.getIndice())));
                    bobCommands.add(new Affectation(node.getIndice(), new Expression(ExpressionType.XOR, input1.getIndice(),input2.getIndice())));
                    break;
                case OUTA : //OUTA
                    input1=node.inputs.get(0);
                    aliceCommands.add(new Affectation(node.getIndice(),new Pop()));
                    aliceCommands.add(new Affectation(node.getIndice(),new Expression(ExpressionType.XOR, node.getIndice(), input1.getIndice())));
                    bobCommands.add(new Push(input1.getIndice()));
                    bobCommands.add(new Affectation(node.getIndice(), new Expression(ExpressionType.VAL,input1.getIndice(),n)));
                    break;
                case OUTB : //OUTB
                    input1=node.inputs.get(0);
                    
                    bobCommands.add(new Affectation(node.getIndice(),new Pop()));
                    bobCommands.add(new Affectation(node.getIndice(),new Expression(ExpressionType.XOR, node.getIndice(), input1.getIndice())));
                    aliceCommands.add(new Push(input1.getIndice()));
                    aliceCommands.add(new Affectation(node.getIndice(), new Expression(ExpressionType.VAL,input1.getIndice(),n)));
                    break;
            }
            count++;
        }
    }
   
}

