package com.mycompany.tetrisgame.controllers;

import com.mycompany.tetrisgame.models.grid.Cell;
import com.mycompany.tetrisgame.models.grid.Grid;
import com.mycompany.tetrisgame.models.pieces.Piece;
import com.mycompany.tetrisgame.models.pieces.PieceFactory;
import com.mycompany.tetrisgame.models.powerup.SlowDownDecorator;
import com.mycompany.tetrisgame.models.utils.GameLogger;
import com.mycompany.tetrisgame.models.utils.GameSettings;
import com.mycompany.tetrisgame.models.game.GameContext;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML private Canvas gameCanvas;
    @FXML private VBox pauseMenu;
    @FXML private VBox gameOverMenu;

    @FXML private Button pauseButton;
    @FXML private Button resumeButton;
    @FXML private Button retryButton;
    @FXML private Button mainMenuButton;

    @FXML private Label scoreLabel;
    @FXML private Label finalScoreLabel;
    @FXML private Label levelLabel;

    private GraphicsContext gc;
    private Grid grid;
    private Piece currentPiece;

    private int score = 0;
    private int level = 1;
    private int totalLinesCleared = 0;
    private final int LINES_PER_LEVEL = 10;

    private int dropSpeed;
    private AnimationTimer timer;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private boolean isPaused = false;

    private long lastMoveTime = 0;
    private long moveInterval = 100_000_000;

    private final int SINGLE = 40;
    private final int DOUBLE = 100;
    private final int TRIPLE = 300;
    private final int TETRIS = 1200;

    @FXML
    public void initialize() {

        gc = gameCanvas.getGraphicsContext2D();

        GameSettings settings = GameSettings.getInstance();
        grid = new Grid(settings.getGridWidth(), settings.getGridHeight());
        dropSpeed = settings.getInitialDropSpeed();

        GameLogger.getInstance().info("Game started");
        GameLogger.getInstance().state("Game: MENU -> PLAYING");

        spawnNewPiece();

        pauseMenu.setVisible(false);
        gameOverMenu.setVisible(false);

        resumeButton.setOnAction(e -> togglePause());
        retryButton.setOnAction(e -> restartGame());
        mainMenuButton.setOnAction(e -> goToMainMenu());

        gameCanvas.setFocusTraversable(true);
        gameCanvas.setOnKeyPressed(this::handleKeyPressed);
        gameCanvas.setOnKeyReleased(this::handleKeyReleased);

        startGameLoop();
    }

    private void addScore(int linesCleared) {

        int points = switch (linesCleared) {
            case 1 -> SINGLE * level;
            case 2 -> DOUBLE * level;
            case 3 -> TRIPLE * level;
            case 4 -> TETRIS * level;
            default -> 0;
        };

        score += points;
        totalLinesCleared += linesCleared;

        GameLogger.getInstance().state("Lines cleared: " + linesCleared);

        int newLevel = totalLinesCleared / LINES_PER_LEVEL + 1;

        if (newLevel > level) {
            level = newLevel;

            dropSpeed = Math.max(50, 800 - (level - 1) * 80);

            GameLogger.getInstance().logGameState(score, level, totalLinesCleared, "Level Up!");

            Platform.runLater(() -> levelLabel.setText("Level: " + level));
        }

        Platform.runLater(() -> scoreLabel.setText("Score: " + score));
    }

    private void startGameLoop() {
        timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) lastUpdate = now;

                double delta = (now - lastUpdate) / 1_000_000.0;
                if (delta > dropSpeed) {
                    update();
                    lastUpdate = now;
                }

                if (now - lastMoveTime > moveInterval) {
                    if (leftPressed) moveLeft();
                    if (rightPressed) moveRight();
                    if (downPressed) moveDown();
                    lastMoveTime = now;
                }

                render();
            }
        };

        timer.start();
    }

    private void spawnNewPiece() {
        Piece piece = PieceFactory.createRandomPiece();

        if (Math.random() < 0.2) {
            piece = new SlowDownDecorator(piece);
            GameLogger.getInstance().decorator("SlowDown applied to Piece");
        }

        currentPiece = piece;

        currentPiece.setX(grid.getWidth() / 2 - 2);
        currentPiece.setY(0);

        GameLogger.getInstance().state("New piece spawned: " + currentPiece.getClass().getSimpleName());
    }

    private void update() {

        currentPiece.drop();

        if (checkCollision()) {
            currentPiece.setY(currentPiece.getY() - 1);
            placePiece();

            int lines = grid.clearFullLines();

            if (lines > 0) {
                addScore(lines);
            }

            spawnNewPiece();

            if (checkCollision()) stopGame();
        }
    }

    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        double cw = gameCanvas.getWidth() / grid.getWidth();
        double ch = gameCanvas.getHeight() / grid.getHeight();

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Cell cell = grid.getCell(x, y);
                gc.setFill(cell.isFilled() ? cell.getColor() : javafx.scene.paint.Color.LIGHTGRAY);
                gc.fillRect(x * cw, y * ch, cw, ch);
                gc.strokeRect(x * cw, y * ch, cw, ch);
            }
        }

        int[][] shape = currentPiece.getShape();
        javafx.scene.paint.Color color =
                GameSettings.getInstance().getPieceColors()[currentPiece.getTypeIndex()];
        gc.setFill(color);

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int x = currentPiece.getX() + j;
                    int y = currentPiece.getY() + i;
                    gc.fillRect(x * cw, y * ch, cw, ch);
                    gc.strokeRect(x * cw, y * ch, cw, ch);
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

                    if (x < 0 || x >= grid.getWidth() || y >= grid.getHeight()) return true;
                    if (grid.getCell(x, y).isFilled()) return true;
                }
            }
        }
        return false;
    }

    private void placePiece() {
        int[][] shape = currentPiece.getShape();
        javafx.scene.paint.Color color =
                GameSettings.getInstance().getPieceColors()[currentPiece.getTypeIndex()];

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    Cell c = grid.getCell(currentPiece.getX() + j, currentPiece.getY() + i);
                    c.setFilled(true);
                    c.setColor(color);
                }
            }
        }

        GameLogger.getInstance().state("Piece placed at Y=" + currentPiece.getY());
    }

    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == null) return;

        switch (event.getCode()) {
            case LEFT -> { if (!isPaused) { leftPressed = true; GameLogger.getInstance().state("Player pressed LEFT"); } }
            case RIGHT -> { if (!isPaused) { rightPressed = true; GameLogger.getInstance().state("Player pressed RIGHT"); } }
            case DOWN -> { if (!isPaused) { downPressed = true; GameLogger.getInstance().state("Player pressed DOWN"); } }
            case UP -> {
                if (!isPaused) {
                    currentPiece.rotate();
                    if (checkCollision()) currentPiece.rotateBack();
                    else GameLogger.getInstance().state("Piece rotated");
                }
            }
            case ESCAPE -> togglePause();
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        if (event.getCode() == null) return;

        switch (event.getCode()) {
            case LEFT -> leftPressed = false;
            case RIGHT -> rightPressed = false;
            case DOWN -> downPressed = false;
        }
    }

    private void moveLeft() {
        currentPiece.setX(currentPiece.getX() - 1);
        if (checkCollision()) currentPiece.setX(currentPiece.getX() + 1);
        else GameLogger.getInstance().state("Player moved LEFT");
    }

    private void moveRight() {
        currentPiece.setX(currentPiece.getX() + 1);
        if (checkCollision()) currentPiece.setX(currentPiece.getX() - 1);
        else GameLogger.getInstance().state("Player moved RIGHT");
    }

    private void moveDown() {
        currentPiece.setY(currentPiece.getY() + 1);
        if (checkCollision()) currentPiece.setY(currentPiece.getY() - 1);
        else GameLogger.getInstance().state("Player moved DOWN");
    }

    @FXML
    public void togglePause() {
        if (!isPaused) {
            GameLogger.getInstance().state("Game paused");

            timer.stop();
            leftPressed = rightPressed = downPressed = false;
            pauseMenu.setVisible(true);
            gameCanvas.setFocusTraversable(false);
        } else {
            GameLogger.getInstance().state("Game resumed");

            pauseMenu.setVisible(false);
            lastMoveTime = System.nanoTime();
            timer.start();
            gameCanvas.setFocusTraversable(true);
            gameCanvas.requestFocus();
        }
        isPaused = !isPaused;
    }

    private void stopGame() {
        timer.stop();

        GameLogger.getInstance().state("Game: PLAYING -> GAME_OVER");
        GameLogger.getInstance().info("Final score: " + score);

        gameOverMenu.setVisible(true);
        finalScoreLabel.setText("Score: " + score);
    }

    private void restartGame() {
        GameLogger.getInstance().info("Game restarted");

        gameOverMenu.setVisible(false);
        score = 0;
        level = 1;
        totalLinesCleared = 0;
        dropSpeed = GameSettings.getInstance().getInitialDropSpeed();

        scoreLabel.setText("Score: 0");
        levelLabel.setText("Level: 1");

        grid.clearAllCells();
        spawnNewPiece();

        timer.start();
    }

    private void goToMainMenu() {
        GameLogger.getInstance().state("Game exited -> Main Menu");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/tetrisgame/MainMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) gameCanvas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
