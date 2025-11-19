# Tetris Game - JavaFX

## Description
Ce projet est une implémentation du jeu **Tetris** en Java avec **JavaFX**.  
Le jeu intègre plusieurs **Design Patterns** pour organiser le code de manière modulaire et maintenable, et propose une interface graphique simple et fonctionnelle.

### Fonctionnalités
- Jouabilité classique de Tetris (déplacement, rotation, chute rapide des pièces)
- Gestion des niveaux et du score
- Pause et reprise du jeu
- Suppression automatique des lignes complètes
- Interface graphique responsive avec JavaFX
- Implémentation de **Design Patterns** :
  - State
  - Singleton
  - Factory
  - Observer
  - Decorator (optionnel selon l’implémentation)

## Structure du projet
TetrisGame/
│
├─ src/
│ ├─ application/ # Classe Main et configuration JavaFX
│ ├─ controllers/ # Contrôleurs pour le jeu et les states
│ ├─ models/ # Classes de données (Grid, Cell, Piece, GameState)
│ └─ utils/ # Classes utilitaires (Logger, Settings)
│
├─ resources/ # Fichiers FXML, CSS et images
│
├─ README.md # Documentation du projet
└─ .gitignore # Ignorer fichiers temporaires, binaires et IDE


## Installation

1. Cloner le dépôt :
```bash
git clone https://github.com/votre-utilisateur/TetrisGame.git
cd TetrisGame
Ouvrir le projet dans NetBeans, IntelliJ IDEA ou tout IDE compatible JavaFX.

S’assurer que le JDK 17+ ou supérieur est installé et que JavaFX est configuré.

Exécuter la classe principale Main.java.
Utilisation

Touches directionnelles : déplacer et faire tourner les pièces

Espace : chute rapide

P : pause/reprise du jeu

Les lignes complètes sont supprimées automatiquement et le score est mis à jour.
