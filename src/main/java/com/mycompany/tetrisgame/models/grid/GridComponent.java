
package com.mycompany.tetrisgame.models.grid;

import java.util.List;


//C’est une classe abstraite qui sert de base pour le Pattern COMPOSITE.
//En Composite, on a des objets simples (Leaf) et des objets composés (Composite).
//Une ligne ou une pièce entière → Composite
//permet de traiter des objets simples et des objets composés de la même façon.
public abstract class GridComponent {
    // Méthodes pour Composite
    public void add(GridComponent component) { }
    public void remove(GridComponent component) { }
    public List<GridComponent> getChildren() { return null; }

    // Vérifie si la cellule/ligne est remplie
    public abstract boolean isFilled();
}
