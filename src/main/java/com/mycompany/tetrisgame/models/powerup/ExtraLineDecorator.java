/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tetrisgame.models.powerup;

import com.mycompany.tetrisgame.models.grid.Grid;
import com.mycompany.tetrisgame.models.pieces.Piece;
import com.mycompany.tetrisgame.models.utils.GameLogger;


//Son rôle : ajouter une ligne vide en bas de la grille quand la pièce est posée.
//Il peut ajouter un effet spécial, ici sur le Grid.
public class ExtraLineDecorator extends PiecePowerUp {

    private Grid grid;

    public ExtraLineDecorator(Piece piece, Grid grid) {
        super(piece);
        this.grid = grid;
        GameLogger.getInstance().log("Power-up ExtraLine appliqué à la pièce");
    }

    // Quand la pièce est posée, ajoute une ligne vide en bas
    @Override
    public void drop() {
        decoratedPiece.drop();
        // Ajouter une ligne en bas de la grille
      //  grid.getChildren().add(new com.tetris.model.grid.Line(grid.getChildren().get(0).getChildren().size()));
        GameLogger.getInstance().log("Extra ligne ajoutée à la grille !");
    }
}