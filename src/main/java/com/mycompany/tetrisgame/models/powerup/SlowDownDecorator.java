
package com.mycompany.tetrisgame.models.powerup;

import com.mycompany.tetrisgame.models.pieces.Piece;
import com.mycompany.tetrisgame.models.utils.GameLogger;


//C’est un décorateur concret qui modifie le comportement de descente d’une pièce
//Hérite de PiecePowerUp, donc :
// . Elle conserve tous les comportements de la pièce originale (déplacement, rotation, position)
// . Elle peut ajouter ou modifier uniquement ce qui l’intéresse, ici la descente (drop())
public class SlowDownDecorator extends PiecePowerUp {

    public SlowDownDecorator(Piece piece) {
        super(piece);
        GameLogger.getInstance().log("Power-up SlowDown appliqué à la pièce");
    }


//Ici, le comportement original de descente est modifié :
//On attend 300 ms avant de descendre → la pièce descend plus lentement
//Ensuite, on appelle la descente normale decoratedPiece.drop()
    @Override
    public void drop() {
        // descente plus lente
        try {
            Thread.sleep(300); // ralentit la chute
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        decoratedPiece.drop();
    }
}