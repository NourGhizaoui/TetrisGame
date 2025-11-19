
package com.mycompany.tetrisgame.models.grid;

import java.util.ArrayList;
import java.util.List;


//Line représente une ligne entière de la grille de Tetris, composée de plusieurs cellules (Cell).
//C’est un Composite dans le Pattern COMPOSITE : Contient plusieurs Leaf (Cell)
public class Line extends GridComponent {
    private List<Cell> cells = new ArrayList<>();

    
    //Crée une ligne avec le nombre de cellules width.
    //Chaque cellule est initialement vide (Cell.filled = false).
    public Line(int width) {
        for (int i = 0; i < width; i++) {
            cells.add(new Cell());
        }
    }

    @Override
    public void add(GridComponent component) {
        if (component instanceof Cell) {
            cells.add((Cell) component);
        }
    }

    @Override
    public void remove(GridComponent component) {
        cells.remove(component);
    }

    @Override
    public List<GridComponent> getChildren() {
        return new ArrayList<>(cells);
    }

    
    //Vérifie si toutes les cellules de la ligne sont remplies.
    @Override
    public boolean isFilled() {
        for (Cell cell : cells) {
            if (!cell.isFilled()) return false;
        }
        return true;
    }

    
    //Vide toutes les cellules de la ligne.
    //Utilisée lorsque la ligne est complète et doit disparaître.
    public void clear() {
        for (Cell cell : cells) {
            cell.setFilled(false);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell c : cells) {
            sb.append(c.toString());
        }
        return sb.toString();
    }
}
