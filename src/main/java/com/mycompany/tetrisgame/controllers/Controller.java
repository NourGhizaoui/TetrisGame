package com.mycompany.tetrisgame.controllers;

import com.mycompany.tetrisgame.models.game.GameContext;
import com.mycompany.tetrisgame.models.game.GameStatee;
import com.mycompany.tetrisgame.models.game.MenuState;
import com.mycompany.tetrisgame.models.game.PauseState;
import com.mycompany.tetrisgame.models.game.PlayingState;
import com.mycompany.tetrisgame.models.grid.Cell;
import com.mycompany.tetrisgame.models.grid.Grid;
import com.mycompany.tetrisgame.models.pieces.Piece;
import com.mycompany.tetrisgame.models.pieces.PieceFactory;
import com.mycompany.tetrisgame.models.powerup.SlowDownDecorator;
import com.mycompany.tetrisgame.models.utils.GameLogger;
import com.mycompany.tetrisgame.models.utils.GameSettings;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.Label; // <- pour Label

public class Controller {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private Label scoreLabel;

    private GraphicsContext gc;
    private Grid grid;
    private Piece currentPiece;
    private double lastUpdate = 0;
    private int score = 0;
    private int level = 1;
    private int dropSpeed; // vitesse de descente (millisecondes)
    private AnimationTimer timer; // boucle principale du jeu

    // Configure la grille et la vitesse via GameSettings (Singleton).
    // Crée une nouvelle pièce et démarre la boucle de jeu.
    // Active la capture des touches pour contrôler la pièce.
    public void initialize() {
        gc = gameCanvas.getGraphicsContext2D();

        // Charger les paramètres
        GameSettings settings = GameSettings.getInstance();
        grid = new Grid(settings.getGridWidth(), settings.getGridHeight());
        dropSpeed = settings.getInitialDropSpeed();

        spawnNewPiece();
        startGameLoop();

        // Log démarrage
        GameLogger.getInstance().log("Jeu démarré");

        // Focus sur Canvas pour capter les touches
        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPressed);

        gameContext = new GameContext();
        gameContext.setController(this); // on lie le Controller
        gameContext.setState(new PlayingState());

        // --- Pattern State ---
      /*  gameContext = new GameContext();
        gameContext.setController(this);
        gameContext.setState(new MenuState()); */
// on démarre dans le menu

    }

    // Génération des pièces (Factory + Decorator)
    // Factory → crée aléatoirement une des 7 formes classiques (I, O, T, L, J, S,
    // Z)
    // Decorator → ajoute un power-up aléatoire (SlowDownDecorator) qui ralentit la
    // chute
    private void spawnNewPiece() {
        Piece piece = PieceFactory.createRandomPiece();

        if (Math.random() < 0.2) {
            piece = new SlowDownDecorator(piece);
        }

        currentPiece = piece;
        currentPiece.setX(grid.getWidth() / 2 - 2);
        currentPiece.setY(0);

        GameLogger.getInstance().log("Nouvelle pièce générée");

    }

    private void startGameLoop() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0)
                    lastUpdate = now;

                if ((now - lastUpdate) / 1_000_000 > dropSpeed) {
                    update();
                    lastUpdate = now;
                }

                render();
            }
        };
        timer.start();
    }
    /*
     * private void update() {
     * currentPiece.setY(currentPiece.getY() + 1);
     * 
     * if (checkCollision()) {
     * currentPiece.setY(currentPiece.getY() - 1);
     * placePiece();
     * 
     * int linesCleared = grid.clearFullLines();
     * if (linesCleared > 0) {
     * score += linesCleared * GameSettings.getInstance().getPointsPerLine();
     * GameLogger.getInstance().log("Lignes complétées: " + linesCleared);
     * }
     * 
     * spawnNewPiece();
     * 
     * if (checkCollision()) {
     * GameLogger.getInstance().log("Game Over");
     * stopGame();
     * }
     * }
     * }
     */

    // Mise à jour de la pièce (STATE)
    // STATE Pattern → la méthode drop() de la pièce dépend de son état (IdleState,
    // MovingState, DroppedState)
    // vérifie les collisions et pose la pièce si nécessaire
    // Supprime les lignes complètes (COMPOSITE)
    private void update() {
        currentPiece.drop();

        if (checkCollision()) {
            // Revenir à la position précédente
            currentPiece.setY(currentPiece.getY() - 1);

            // Poser la pièce dans la grille
            placePiece();

            // Vérifier et supprimer les lignes complètes
            int linesCleared = grid.clearFullLines();
            if (linesCleared > 0) {
                score += linesCleared * GameSettings.getInstance().getPointsPerLine();
                // scoreLabel.setText("Score: " + score);

                Platform.runLater(() -> scoreLabel.setText("Score: " + score));

                GameLogger.getInstance().log("Lignes complétées: " + linesCleared);
                System.out.println("Score actuel: " + score);
                // scoreLabel.setText("Score: " + score);
                // Afficher le score mis à jour
            }

            // Générer une nouvelle pièce
            spawnNewPiece();

            // **Game Over uniquement si la nouvelle pièce est déjà en collision**
            if (checkCollision()) {
                GameLogger.getInstance().log("Game Over");
                stopGame(); // Affiche Game Over et score final
            }
        }
    }

    private Color getPieceColor(Piece piece) {
        GameSettings settings = GameSettings.getInstance();
        // Assumons que Piece a une méthode getTypeIndex() qui retourne 0 à 6 selon le
        // type I,O,T,L,J,S,Z
        int index = piece.getTypeIndex();
        return settings.getPieceColors()[index];
    }

    // cOMPOSITE Pattern → chaque Cell et Line fait partie de la grille, ce qui
    // permet de dessiner toutes les lignes facilement.
    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        double cellWidth = gameCanvas.getWidth() / grid.getWidth();
        double cellHeight = gameCanvas.getHeight() / grid.getHeight();

        // Dessiner la grille
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Cell cell = grid.getCell(x, y);
                gc.setFill(cell.isFilled() ? Color.GRAY : Color.LIGHTGRAY);
                gc.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }

        // Dessiner pièce actuelle
        // Dessiner pièce actuelle
        // Dessiner pièce actuelle
        int[][] shape = currentPiece.getShape();
        Color color = GameSettings.getInstance().getPieceColors()[currentPiece.getTypeIndex()];
        gc.setFill(color);

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int x = currentPiece.getX() + j;
                    int y = currentPiece.getY() + i;
                    gc.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                    gc.strokeRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }
            }
        }

    }

    private boolean checkCollision() {
        int[][] shape = currentPiece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int x = currentPiece.getX() + j;
                    int y = currentPiece.getY() + i;

                    if (x < 0 || x >= grid.getWidth() || y >= grid.getHeight())
                        return true;

                    if (grid.getCell(x, y).isFilled())
                        return true;
                }
            }
        }
        return false;
    }

    private void placePiece() {
        int[][] shape = currentPiece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int x = currentPiece.getX() + j;
                    int y = currentPiece.getY() + i;
                    grid.getCell(x, y).setFilled(true);
                }
            }
        }
        GameLogger.getInstance().log("Pièce posée dans la grille");
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    // Contrôles clavier
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                currentPiece.setX(currentPiece.getX() - 1);
                if (checkCollision())
                    currentPiece.setX(currentPiece.getX() + 1);
                break;
            case RIGHT:
                currentPiece.setX(currentPiece.getX() + 1);
                if (checkCollision())
                    currentPiece.setX(currentPiece.getX() - 1);
                break;
            case DOWN:
                currentPiece.setY(currentPiece.getY() + 1);
                if (checkCollision())
                    currentPiece.setY(currentPiece.getY() - 1);
                break;
            case UP:
                currentPiece.rotate();
                if (checkCollision())
                    currentPiece.rotateBack();
                break;
            default:
                break;
        }

        render();
    }

    private void stopGame() {
        timer.stop();
        System.out.println("Game Over! Score final: " + score);
        // GameLogger.getInstance().log("Jeu en pause");

    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    @FXML
    private Button pauseButton;

    private boolean isPaused = false;

    private GameContext gameContext;

    @FXML
    private void handlePause() {
        GameStatee state = gameContext.getState();

        // Si on est en PlayingState -> pause
        // Si on est en PauseState -> resume
        if (state instanceof PlayingState) {
            gameContext.pause();
        } else if (state instanceof PauseState) {
            gameContext.resume();
        }
    }

    /*
     * @FXML
     * private void handlePause() {
     * if (!isPaused) {
     * timer.stop(); // Stoppe la boucle principale
     * pauseButton.setText("Resume");
     * GameLogger.getInstance().log("Jeu en pause");
     * } else {
     * timer.start(); // Reprend la boucle
     * pauseButton.setText("Pause");
     * GameLogger.getInstance().log("Jeu repris");
     * }
     * isPaused = !isPaused;
     * }
     * 
     */

}
