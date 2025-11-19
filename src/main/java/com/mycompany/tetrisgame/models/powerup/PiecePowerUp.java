
package com.mycompany.tetrisgame.models.powerup;

import com.mycompany.tetrisgame.models.pieces.Piece;


//Decorator Pattern : ajouter dynamiquement des fonctionnalités aux pièces sans modifier la classe Piece.
// rôle : ajouter des comportements supplémentaires aux pièces (comme power-ups ou effets spéciaux) sans modifier la classe Piece originale.
//Exemple : Une pièce « HeavyPiece » descend plus vite ; Une pièce « RainbowPiece » change de couleur
public abstract class PiecePowerUp extends Piece {
    protected Piece decoratedPiece;

    public PiecePowerUp(Piece piece) {
        super(piece.getShape()); // copie la forme de base
        this.decoratedPiece = piece;
    }

    // On délègue les actions à la pièce décorée
    @Override
    public void moveLeft() {
        decoratedPiece.moveLeft();
    }

    @Override
    public void moveRight() {
        decoratedPiece.moveRight();
    }

    @Override
    public void rotate() {
        decoratedPiece.rotate();
    }

    @Override
    public void drop() {
        decoratedPiece.drop();
    }

    @Override
    public void setX(int x) {
        decoratedPiece.setX(x);
    }

    @Override
    public void setY(int y) {
        decoratedPiece.setY(y);
    }

    @Override
    public int getX() {
        return decoratedPiece.getX();
    }

    @Override
    public int getY() {
        return decoratedPiece.getY();
    }

    @Override
    public int[][] getShape() {
        return decoratedPiece.getShape();
    }
}
