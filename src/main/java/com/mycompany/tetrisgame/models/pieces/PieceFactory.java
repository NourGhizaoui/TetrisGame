package com.mycompany.tetrisgame.models.pieces;

public class PieceFactory {
public static Piece createRandomPiece() {
    int type = (int) (Math.random() * 7); // type aléatoire
    Piece piece;

    switch(type) {
        case 0: piece = new Piece(new int[][]{{1,1,1,1}}); break; // I
        case 1: piece = new Piece(new int[][]{{1,1},{1,1}}); break; // O
        case 2: piece = new Piece(new int[][]{{0,1,0},{1,1,1}}); break; // T
        case 3: piece = new Piece(new int[][]{{1,0,0},{1,1,1}}); break; // L
        case 4: piece = new Piece(new int[][]{{0,0,1},{1,1,1}}); break; // J
        case 5: piece = new Piece(new int[][]{{0,1,1},{1,1,0}}); break; // S
        case 6: piece = new Piece(new int[][]{{1,1,0},{0,1,1}}); break; // Z
        default: piece = new Piece(new int[][]{{1,1,1,1}});
    }

    piece.setTypeIndex(type); // ← définit la couleur fixe selon le type
    return piece;
}

}
