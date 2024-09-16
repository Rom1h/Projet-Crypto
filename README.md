# Projet-Crypto

Ce projet contient l'implémentation de plusieurs algorithmes cryptographiques ainsi que des mécanismes de transfert sécurisé et de calcul sur des circuits logiques. Ce fichier README explique les principales parties du projet, leur rôle et comment les utiliser.

## Structure du projet

Le projet est organisé en plusieurs modules distincts, chacun ayant une fonction bien définie.

### 1. Système AES
- **Fichier** : `AES.java`
- Cette partie contient l'implémentation d'AES (Advanced Encryption Standard), avec des méthodes pour la génération de clés, le chiffrement et le déchiffrement de messages.

### 2. Système RSA
- **Fichier** : `RSA.java`
- Implémente le système RSA (Rivest-Shamir-Adleman), avec des méthodes pour la génération de paires de clés, le chiffrement et le déchiffrement.

### 3. Algorithme de Pollard
- **Fichier** : `Pollard.java`
- Contient une implémentation de l'algorithme de Pollard pour la recherche de facteurs d'un nombre entier, utilisé dans certains contextes de cryptographie.

### 4. Protocole de Transfert Inconscient (Oblivious Transfer)
- **Fichier** : `ObliviousTransferRSA.java`
- Implémente un protocole de transfert inconscient (Oblivious Transfer - OT) basé sur RSA, permettant l'échange sécurisé de messages sans révélation complète des informations entre les parties.

### 5. Circuit Logique
- **Fichiers** : `Circuit.java`, `BinaryGate.java`, `InputNode.java`, `Node.java`, `NotGate.java`, `OutputNode.java`, `TypeNode.java`
- Ce module permet de modéliser des circuits logiques. Les fichiers contiennent des représentations de différentes portes logiques (XOR, AND, NOT), des nœuds d'entrée/sortie, et des méthodes pour créer et évaluer des circuits.

#### Création d'un circuit
- Créer un circuit avec `new Circuit()`.
- Ajouter des nœuds d’entrée avec `new InputNode(nom)` (nom doit être sous forme "InAi" ou "InBi").
- Ajouter des portes logiques (NOT, XOR, AND) via les classes correspondantes (`NotGate`, `BinaryGate`).
- Ajouter des nœuds de sortie avec `new OutputNode(nom)`.
- Finaliser le circuit avec `circuit.addNode(node)` pour chaque nœud créé.

### 6. Machine Virtuelle
- **Fichiers** : `VirtualMachine.java`, `CircuitCompiler.java`
- Ce module permet de simuler une machine virtuelle capable d'exécuter des instructions sur les circuits logiques définis. `VirtualMachine.java` contient les opérations de la machine virtuelle, tandis que `CircuitCompiler.java` traduit un circuit en instructions exécutables.

### 7. Préparation d'un Circuit Brouillé
- **Fichiers** : `CircuitAlice.java`, `CircuitBrouille.java`
- Implémente la préparation des circuits brouillés, où des clés AES sont assignées aux nœuds d'un circuit, et un circuit brouillé est construit.

### 8. Évaluation des Circuits Brouillés
- **Fichier** : `BobsEval.java`
- Implémente l'évaluation d'un circuit brouillé par Bob. Le fichier gère la réception des informations du circuit brouillé et l'évaluation correcte des portes logiques après déchiffrement des messages.

## Instructions d'utilisation

### Compilation et exécution des tests

Pour compiler le projet et exécuter les tests, placez-vous dans le répertoire `myproject` et exécutez la commande suivante :
```bash
make run
```
Tous les tests sont exécutés dans la classe `src/test/java/com/example/Tests.java`.

### Tests spécifiques

#### AES
Vous pouvez tester l'implémentation AES en appelant la fonction suivante :
```java
AES.testAES(message)
```

#### RSA
Pour tester RSA, utilisez la fonction suivante, en remplaçant les paramètres par le nombre de tests (`n`) et la taille des clés :
```java
RSA.testRSA(n, bitsLength)
```

#### Pollard
Testez l'algorithme de Pollard soit avec des nombres aléatoires :
```java
testPollardWithRandomNumbers(int nombre_de_test, int bitLength)
```
ou sur un seul nombre :
```java
pollardFactor(BigInteger n)
```

#### Protocole de Transfert Inconscient (OT)
Testez le protocole OT avec cinq messages (modifiable) en appelant :
```java
loopTest()
```
ou en utilisant des chaînes de caractères :
```java
OT(String m1, String m2, int b)
```

#### Circuit Logique
Pour créer un circuit et tester son évaluation, vous pouvez utiliser :
```java
Circuit moncircuit = minCircuit(n)
testExhaustif(moncircuit, n)
```

#### Machine Virtuelle
Testez la machine virtuelle avec un circuit et des entrées pour Alice et Bob :
```java
execute(moncircuit, alice, bob)
```

#### Préparation et Évaluation du Circuit Brouillé
- Créez un circuit `c`.
- Préparez Alice avec :
```java
CircuitAlice prep_Alice = new CircuitAlice(c)
```
- Évaluez avec Bob :
```java
EvalBob eval_bob = new EvalBob(c, prep_Alice)
eval_bob.eval_circuit_brouiller()
```

## Auteurs
- Lin Pascal
- Houard Romain
- Xia Didier
- Fonseca Rodrigues
- Loiseau Poilpré Zoé
