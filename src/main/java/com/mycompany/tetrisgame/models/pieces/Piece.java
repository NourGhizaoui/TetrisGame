/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisgame.models.pieces;

import com.mycompany.tetrisgame.models.utils.GameLogger;

public class Piece {
    private int x, y;           // position sur la grille
    private int[][] shape;      // matrice de la pièce (1 = rempli, 0 = vide) ; matrice carrée qui définit la forme de la pièce
    private PieceState state;
   //Son état courant (PieceState) pour gérer les actions (gauche, droite, rotation, descente)
    
      private int typeIndex; // 0=I, 1=O, 2=T, etc.

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int index) {
        this.typeIndex = index;
    }

    public Piece(int[][] shape) {
        this.shape = shape; //Initialise la pièce avec une forme donnée
        this.state = new IdleState(); // état initial
        this.x = 3;  // position de départ sur la grille  +  Positionne la pièce en haut du plateau
        this.y = 0;
    }

    public void setState(PieceState state) {
        this.state = state;
        GameLogger.getInstance().log("Pièce état : " + state.getClass().getSimpleName());
    }

    public PieceState getState() {
        return state;
    }

    public void moveLeft() {
        state.moveLeft(this);
    }

    public void moveRight() {
        state.moveRight(this);
    }

    public void rotate() {
        state.rotate(this);
    }

    public void drop() {
        state.drop(this);
    }

    public void rotateShape() {
        // Rotation 90° matrice
        int n = shape.length;
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
        GameLogger.getInstance().log("Pièce tournée");
    }

    // Getters & Setters : Pour accéder à la position et à la forme de la pièce depuis le Grid ou le GameBoard
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int[][] getShape() { return shape; }
    
    
      // Rotation inverse (anti-horaire)
    public void rotateBack() {
        int n = shape.length;
        int m = shape[0].length;
        int[][] rotated = new int[m][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated[m - 1 - j][i] = shape[i][j];
            }
        }
        shape = rotated;
    }
}


//La pièce ne fait pas elle-même les mouvements, elle demande à son état courant de 
//décider comment agir → c’est exactement le Pattern STATE.