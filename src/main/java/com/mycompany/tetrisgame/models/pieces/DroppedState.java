
package com.mycompany.tetrisgame.models.pieces;


//ela correspond exactement au moment où une pièce touche le bas du plateau ou une autre pièce et devient fixée dans le Grid.
public class DroppedState extends PieceState {

    @Override
    public void moveLeft(Piece piece) {
        // plus de mouvement
    }

    @Override
    public void moveRight(Piece piece) {
        // plus de mouvement
    }
    

    @Override
    public void rotate(Piece piece) {
        // plus de rotation
    }

    @Override
    public void drop(Piece piece) {
        // déjà posé
    }
}