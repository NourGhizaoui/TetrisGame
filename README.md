# Tetris Game – JavaFX

## Description
Ce projet est une implémentation complète du jeu **Tetris** en Java avec JavaFX.  
Le jeu comporte :
- Une grille dynamique  
- Des pièces Tetris (I, O, T, L, J, S, Z) générées automatiquement  
- Un système de score  
- La suppression automatique des lignes complètes  
- Un menu principal, un écran de pause et un écran Game Over  
- La gestion des états du jeu grâce au **State Pattern**  

Ce projet a été développé dans le cadre d’un projet académique.

---

## Membres du Groupe
- Nour Ghizaoui
- Aymen rezgui
- Ben hareb amine
- -Hamza snoussi
## Technologies Utilisées
- **Langage :** Java 24 (NetBeans Default)  
- **Framework GUI :** JavaFX  
- **Build :** Maven (pom.xml déjà configuré)  
- **Logging :** GameLogger (custom)  
- **IDE :** NetBeans 25  
- **Architecture :** MVC + Design Patterns  

### 1. **State Pattern**
Utilisé dans le package :
Gère les états :
- Menu (`MainMenu.fxml`)
- En jeu (`GameView.fxml`)
- Pause (`PauseMenu.fxml`)
- GameOver (`GameOver.fxml`)

Chaque état a un comportement différent.

---
### 2. **Decorator Pattern**
 – Power-ups 
Implémenté dans : Package models.powerup
PiecePowerUp (classe abstraite)

### 3. **Composite  Pattern**

Implémenté dans :

GridComponent (interface)

Cell (feuille)

Piece (composite contenant plusieurs blocs)

➡️ Une pièce est composée de plusieurs cellules

### 4. **Factory Pattern**
Package :
- `PieceFactory` génère automatiquement les pièces :
  - I, O, T, L, J, S, Z

### 5. **Singleton Pattern**
Dans le package :
com.mycompany.tetrisgame.models.utils

markdown
Copier le code
- `GameSettings`  
- `GameLogger`  

Ces classes sont instanciées une seule fois pour tout le projet.

## Installation

### Prérequis
- JDK 24
- Maven installé  
- JavaFX configuré

1. **Cloner le dépôt**
```bash
git clone https://github.com/votre-utilisateur/TetrisGame.git
Compiler : mvn clean install

Exécuter :  mvn javafx:run

Utilisation (Commandes du Jeu)

Flèche gauche → déplacer la pièce à gauche

Flèche droite → déplacer la pièce à droite

Flèche haut → rotation de la pièce

Flèche bas → descente plus rapide

Espace → chute instantanée

P → pause / reprise

Échap → retour au menu (si implémenté)


---
