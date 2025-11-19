
package com.mycompany.tetrisgame.models.pieces;

//IdleState représente l’état initial ou inactif d’une pièce Tetris.
//IdleState = pièce immobile
public class IdleState extends PieceState {

    @Override
    public void moveLeft(Piece piece) {
        // pas de mouvement
    }

    @Override
    public void moveRight(Piece piece) {
        // pas de mouvement
    }

    @Override
    public void rotate(Piece piece) {
        // pas de rotation
    }

    
    //change l’état de la pièce en MovingState → la pièce peut maintenant bouger et tomber.
    @Override
    public void drop(Piece piece) {
        piece.setState(new MovingState());
    }
}