
package com.mycompany.tetrisgame.models.grid;

//Cell est une cellule individuelle de la grille, donc c’est un Leaf dans le Pattern COMPOSITE.
//Elle représente une seule case qui peut être vide ou remplie par un bloc.
public class Cell extends GridComponent {
    
    //Indique si la cellule est remplie (true) ou vide (false).
    //Par défaut, la cellule est vide (false).
    private boolean filled = false;

    
    //Vérifie si la cellule est remplie
    @Override
    public boolean isFilled() {
        return filled;
    }

    //Change l’état de la cellule
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        return filled ? "X" : ".";
    }
}