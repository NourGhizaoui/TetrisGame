package com.mycompany.tetrisgame.models.grid;

import javafx.scene.paint.Color;

// Cell est une cellule individuelle de la grille (Leaf dans le Pattern COMPOSITE)
public class Cell extends GridComponent {
    
    // Indique si la cellule est remplie (true) ou vide (false)
    private boolean filled = false;

    // Couleur de la cellule
    private Color color = Color.LIGHTGRAY; // couleur par défaut si vide

    @Override
    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    // Définir la couleur de la cellule (utilisé pour garder la couleur de la pièce)
    public void setColor(Color color) {
        this.color = color;
    }

    // Récupérer la couleur actuelle de la cellule
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return filled ? "X" : ".";
    }
}
