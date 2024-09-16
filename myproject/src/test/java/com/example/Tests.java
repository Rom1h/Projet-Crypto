package test.java.com.example;

import main.java.com.example.RSA;
import cryptoprojet.myproject.src.main.java.com.example.AES;
import main.java.com.MV.VirtualMachine;
import main.java.com.example.Circuit.Circuit;
import main.java.com.example.Pollard;
import main.java.com.example.BobsEval;
import main.java.com.example.CircuitAlice;
import main.java.com.example.ObliviousTransferRSA;
public class Tests {




    public static void main(String[] args){

        //Test pour Systeme AES
        System.out.println("Test AES");
        AES.testAES("Bonjour");

        //Test pour Systeme RSA Q2
        System.out.println("\nTest RSA");
        RSA.testRSA(5, 128);

        //Test pour Pollart Q3 
        System.out.println("\nTest Pollard");
        Pollard.testPollardWithRandomNumbers(2, 128);

        //Test pour OT Q4 
        System.out.println("\nTest OT");
        String m1 = "Hello, World!";
        String m2 = "Goodbye, World!";
        System.out.println("Bob choisie 0 "+ObliviousTransferRSA.OT(m1, m2, 0));
        System.out.println("Bob choisie 1 "+ObliviousTransferRSA.OT(m1, m2, 1));

        //Tests pour le Circuit questions 5 et 6
        System.out.println("\nTests pour le circuit min8");
        Circuit min2 = Circuit.minCircuit(8);
        Circuit.testExhaustif(min2, 8);

        //Tests pour la Machine Virtuelle question 7
        System.out.println("\nTests pour la machine Virtuelle");
        System.out.println("Test min(01011010,00011010)");
        Circuit min=Circuit.minCircuit(8);
        boolean[] valA={false,true,false,true ,true,false,true,false};
        boolean[] valB={false,false,false,true,true ,false,true,false};
        VirtualMachine.execute(min, valA, valB);//test pour l'execution de la machine virtuelle pour la fonction min(01011010,00011010)

        System.out.println("Test a<=b pour a=1 et b=0");
        Circuit isMin=Circuit.generateCircuit();
        boolean[] valA1={true};
        boolean[] valB1={false};
        VirtualMachine.execute(isMin, valA1, valB1);//test pour la fonction valA1<=valB2
        
        System.out.println("Test a<=b pour a=0 et b=1");
        boolean[] valA2={false};
        boolean[] valB2={true};
        VirtualMachine.execute(isMin, valA2, valB2);//test pour la fonction valA1<=valB2

        // test pour circuit brouiller q8 9
        System.out.println("\nTest pour circuit brouiller");
        try {
            Circuit c = Circuit.generateCircuit(); // circuit SIMPLE
            CircuitAlice a = new CircuitAlice(c);
            BobsEval bob = new BobsEval(c, a);
            bob.eval_circuit_brouiller();
        } catch (Exception e) {
            e.printStackTrace(); // Assurez-vous de traiter l'exception ici pour éviter un crash
        }

    }
    
}
