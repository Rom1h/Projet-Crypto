README

Les documents à lire sont ce readme et le rapport. Le readme comporte les informations essentielles pour l’exécution des tests et les différentes parties du code. Le rapport quant à lui contient plus d’informations sur la façon dont ont été réalisées les solutions aux 9 questions.
Chaque question a un petit paragraphe explicatif du code mis en place.

Le code se divise en 8 parties principales:

-Le Système AES
	Cette partie sert à répondre directement à la question 1. On a un unique fichier AES.java qui possède les méthodes de génération de clé de chiffrement et de déchiffrement.

- Le Système RSA
	Cette partie sert à répondre directement à la question 2. Cette partie est représentée par un unique fichier RSA.java qui contient un générateur de clé qui est invoqué une fois lors la création de l’objet, une méthode pour chiffrer des messages et une autre pour les déchiffrer.

- Pollard 
	Cette section vise à répondre directement à la question 3. Elle se trouve dans un seul fichier nommé Pollard.java, qui contient la fonction qui recherche un facteur d'un nombre donné.

- Protocol OT 
	Cette section vise à répondre directement à la question 4. Elle se trouve dans un fichier unique nommé ObliviousTransferRSA.java, qui contient le déroulement complet d'un protocole de transfert inconscient (OT), avec toutes les étapes réparties sur plusieurs fonctions distinctes.

-Le Circuit:
	Cette partie sert à répondre directement aux questions 5 et 6. Le fichier principal de cette partie est le fichier Circuit.java qui contient la génération du circuit et son évaluation.
Les fichiers BinaryGate.java, InputNode.java, Node.java, NotGate.java,OutpoutNode.java et TypeNode.java sont des fichiers outils qui servent à la représentation du circuit.

Pour créer votre propre circuit il faut suivre les étapes suivant :
-Tout d’abords vous créer un nouveau circuit avec new Circuit().
-Créer les noeuds d’entré avec new InputNode(nom) qui prends en paramètre un nom qui doit être de la forme “InAi” ou bien “InBi’ ou i est l’indice du noeud
	-Créer des portes Not avec new NotGate(nom,input) qui prends en paramètre un nom et un noeud d’entré
	-Créer des portes XOR et AND avec new BinaryGate(nom,input1,input2,type) prends en paramètre un nom, deux noeuds d’entré et un type qui peut NoeudType.XOR ou bien NoeudType.AND
	-Créer des noeuds de sortie avec new OutputNode(nom) qui prends en paramètre un nom qui doit être de la forme “OutAi” ou bien “OutBi’ ou i est l’indice du noeud
	-Pour ajouter vos noeuds au circuit vous pouvez faire appel à la fonction circuit.addNode(node)


-La Machine Virtuelle:
	Cette partie sert à répondre directement à la question 7. Cette partie utilise les Circuits de la partie correspondante. Cette partie possède 2 fichiers principaux VirtualMachine.java et CircuitCompiler.java. VirtualMachine représente la machine virtuelle et contient toutes les actions liées à celle-ci. CircuitCompiler se charge de prendre un circuit et d’attribuer en fonction de celui-ci des instructions à Alice et Bob. Les autres fichiers à savoir Affectation.java, Expression.java, Instruction.java, Pop.java et Push.java servent comme outils afin de représenter les différentes instructions d’Alice et de Bob.

-La Préparation d’Alice 
Cette section répond directement à la question 8. Elle utilise les circuits de la section correspondante et comporte deux fichiers : CircuitAlice.java et CircuitBrouille.java. Le fichier CircuitAlice.java traite principalement de l'attribution des clés AES aux nœuds du circuit. Quant au fichier CircuitBrouille.java, il construit le circuit brouillé en fonction du nœud donné en argument dans son constructeur.

-L'évaluation de Bob 
Cette section répond directement à la question 9. Elle se trouve dans un fichier unique nommé BobsEval.java. Ce fichier reçoit le circuit et la préparation d'Alice comme arguments lors de sa création. Bob utilise la fonction eval_circuit_brouiller() pour évaluer les circuits brouillés.Pour les nœuds AND et XOR qui nécessitent un double déchiffrement, Bob déchiffre une première fois, puis appelle la fonction decrypt2nd(String cipherText, SecretKey primaryKey, SecretKey secondaryKey, Node n) pour le second déchiffrement. Ensuite, il utilise la fonction checkDecryptedValue(String decryptedText, Node n) pour vérifier si le texte déchiffré contient un message valide, généralement "0" ou "1".Pour les portes NOT, Bob déchiffre et appelle également la fonction checkDecryptedValue() pour la validation. Lorsqu'un message correct est trouvé via checkDecryptedValue(), la clé correspondante est sauvegardée dans la liste keyList, permettant de la réutiliser plus tard dans le processus d'évaluation.









Pour compiler les tests il suffit d'exécuter la commande make run en se plaçant dans le répertoire myproject. Tous les tests sont exécutés dans la class /src/test/java/com/example/Tests.java.

-AES : 
Pour tester AES vous pouvez appeler la fonction AES.testAES(message) dans lequel vous pouvez entrer un message.

-RSA:
Pour tester le système RSA il est nécessaire seulement d’appeler la fonction RSA.testRSA(n, bitsLength) en remplaçant n pour la quantité de tests que vous voulez faire et bitsLength pour la taille des messages voulu souhaitées.  

-Pollard 
Pour tester des BigInteger générés aléatoirement, vous pouvez utiliser la fonction testPollardWithRandomNumbers(int nombre_de_test, int bitLength) pour effectuer plusieurs tests, ou simplement utiliser la fonction pollardFactor(BigInteger n) pour en évaluer un seul.

- Protocol OT 
Pour tester le protocole OT, il existe une fonction loopTest() qui réalise un protocole OT sur cinq messages. Le nombre de messages est modifiable en changeant le paramètre de la fonction aliceGenereMessage(5), en le remplaçant par le nombre souhaité. Une autre version des tests consiste à utiliser des chaînes de caractères avec la fonction OT(String m1, String m2, int b), où b correspond à la valeur choisie par Bob.

-Circuit: 
Pour créer un circuit min sur des tailles d’entrées n, il suffit de faire: 
	Circuit moncircuit =  minCircuit(n) en remplaçant n par la valeur souhaitée.
Pour tester l’évaluation du circuit sur toutes les valeurs possibles d’une entrée de taille n:
testExhaustif(moncircuit, n) avec moncircuit un circuit min généré avant et n
la taille des entrées du circuit.

-Machine Virtuelle:
Pour tester la VM il faut créer un circuit, créer des variables d’entrées pour Alice et pour Bob de taille n (il faut que le circuit prenne des entrées de taille n). Ces entrées sous la forme	d’un tableau de booléens. Pour ensuite générer et exécuter les instructions sur la VM:
execute(moncircuit, alice, bob) avec moncircuit un circuit, alice et bob les valeurs d’entrées respectives d’Alice et de Bob.

-Préparation d’Alice et Évaluation de Bob 
Pour tester la préparation d'Alice et l'évaluation de Bob, procédez comme suit :
Créer le circuit : Commencez par créer un circuit, que vous appellerez c.
Initialiser la préparation d'Alice: Créez une instance de CircuitAlice nommée prep_Alice en passant le circuit c comme argument. Cela mettra en place la préparation initiale d'Alice.
Initialiser l'évaluation de Bob: Créez une instance de EvalBob nommée eval_bob, en fournissant le circuit c et la préparation d'Alice (prep_Alice) comme arguments.
Évaluer le circuit brouillé: Appelez la fonction eval_bob.eval_circuit_brouiller() pour évaluer le circuit brouillé. Cela permet à Bob d'évaluer les circuits en utilisant les informations préparées par Alice.


Lin Pascal
Houard Romain
Xia Didier
Fonseca Rodrigues 
Loiseau Poilpré Zoé

