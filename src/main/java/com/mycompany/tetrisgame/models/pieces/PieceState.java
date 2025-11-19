/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//Permet d’utiliser le Pattern STATE pour modifier le comportement d’une pièce à la volée sans toucher à la classe Piece
//Chaque méthode agit sur la pièce et peut être implémentée différemment selon le type de pièce ou son état spécial.
package com.mycompany.tetrisgame.models.pieces;
public abstract class PieceState {
    public abstract void moveLeft(Piece piece);
    public abstract void moveRight(Piece piece);
    public abstract void rotate(Piece piece);
    public abstract void drop(Piece piece);
}