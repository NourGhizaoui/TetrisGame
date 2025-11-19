package com.mycompany.tetrisgame.models.grid;

import java.util.ArrayList;
import java.util.List;

// Grid représente le plateau complet de Tetris, c’est-à-dire toutes les lignes de jeu.
// Elle est un Composite dans le Pattern COMPOSITE : Contient plusieurs Line (qui elles-mêmes contiennent des Cell)
public class Grid extends GridComponent {
    private List<Line> lines = new ArrayList<>();
    private int width;
    private int height;

    // Initialise toutes les lignes du plateau, chacune contenant width cellules vides.
    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        for (int i = 0; i < height; i++) {
            lines.add(new Line(width));
        }
    }

    public Line getLine(int index) {
        return lines.get(index);
    }

    public Cell getCell(int x, int y) {
        if (y < 0 || y >= height || x < 0 || x >= width) return null;
        GridComponent comp = lines.get(y).getChildren().get(x);
        return (comp instanceof Cell) ? (Cell) comp : null;
    }

    @Override
    public void add(GridComponent component) {
        if (component instanceof Line) {
            lines.add((Line) component);
        }
    }

    @Override
    public void remove(GridComponent component) {
        if (component instanceof Line) {
            lines.remove(component);
        }
    }

    @Override
    public List<GridComponent> getChildren() {
        return new ArrayList<>(lines);
    }

    @Override
    public boolean isFilled() {
        for (Line line : lines) {
            if (!line.isFilled()) return false;
        }
        return true;
    }

    // Supprime toutes les lignes complètes et retourne le nombre de lignes supprimées
    public int clearFullLines() {
        int cleared = 0;
        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).isFilled()) {
                lines.remove(i);
                lines.add(0, new Line(width)); // ajoute une ligne vide en haut
                cleared++;
            }
        }
        return cleared;
    }

    // **NOUVEAU** : supprime toutes les cellules pour un restart
    public void clearAllCells() {
        for (Line line : lines) {
            for (GridComponent cell : line.getChildren()) {
                if (cell instanceof Cell) {
                    ((Cell) cell).setFilled(false);
                }
            }
        }
    }

    public void printGrid() {
        for (Line line : lines) {
            System.out.println(line.toString());
        }
        System.out.println();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
