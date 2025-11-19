
package com.mycompany.tetrisgame.models.pieces;


//MovingState représente l’état actif d’une pièce qui tombe.
//Dans cet état, la pièce peut : Se déplacer à gauche/droite + Tourner  + Descendre automatiquement
public class MovingState extends PieceState {

    
    //Déplace la pièce d’une cellule vers la gauche.
    //Ici, pas encore de vérification de collision → il faudra l’ajouter pour éviter de sortir du plateau ou chevaucher une autre pièce.
    @Override
    public void moveLeft(Piece piece) {
        piece.setX(piece.getX() - 1);
    }

    @Override
    public void moveRight(Piece piece) {
        piece.setX(piece.getX() + 1);
    }

    
    //Encore une fois, il faudra vérifier les collisions après rotation.
    @Override
    public void rotate(Piece piece) {
        piece.rotateShape();
    }

    
    //Fait descendre la pièce d’une ligne.
    @Override
    public void drop(Piece piece) {
        piece.setY(piece.getY() + 1);
    }
}
