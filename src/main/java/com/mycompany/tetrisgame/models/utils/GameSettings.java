
package com.mycompany.tetrisgame.models.utils;

import javafx.scene.paint.Color;



//C’est une classe de configuration globale pour ton Tetris.

//Elle contient toutes les valeurs importantes : taille de la grille, vitesse de descente, couleurs des pièces, points, niveaux, etc.
//Elle utilise le Singleton Pattern pour avoir une seule instance accessible partout dans le jeu.
public class GameSettings {

    // Singleton pour avoir un seul objet de configuration
    private static GameSettings instance;

    // Taille de la grille
    private int gridWidth = 10;
    private int gridHeight = 20;

    // Vitesse initiale de descente (en millisecondes)
    private int initialDropSpeed = 500;

    // Couleurs des pièces (I, O, T, L, J, S, Z)
    private Color[] pieceColors = new Color[] {
        Color.CYAN,     // I
        Color.YELLOW,   // O
        Color.PURPLE,   // T
        Color.ORANGE,   // L
        Color.BLUE,     // J
        Color.GREEN,    // S
        Color.RED       // Z
    };

    // Niveau actuel
    private int level = 1;

    // Points par ligne complétée
    private int pointsPerLine = 100;

    // Private constructor pour Singleton
    private GameSettings() {}

    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }

    // Getters & Setters
    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public int getInitialDropSpeed() {
        return initialDropSpeed;
    }

    public void setInitialDropSpeed(int initialDropSpeed) {
        this.initialDropSpeed = initialDropSpeed;
    }

    public Color[] getPieceColors() {
        return pieceColors;
    }

    public void setPieceColors(Color[] pieceColors) {
        this.pieceColors = pieceColors;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPointsPerLine() {
        return pointsPerLine;
    }

    public void setPointsPerLine(int pointsPerLine) {
        this.pointsPerLine = pointsPerLine;
    }
}
