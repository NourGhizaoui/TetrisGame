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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Canvas gameCanvas;

    @FXML
    private VBox pauseMenu;

    @FXML
    private VBox gameOverMenu;

    @FXML
    private Button pauseButton;

    @FXML
    private Button resumeButton;

    @FXML
    private Button retryButton;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label finalScoreLabel;

    private GraphicsContext gc;
    private Grid grid;
    private Piece currentPiece;
    private int score = 0;
    private int dropSpeed;
    private AnimationTimer timer;
    private GameContext gameContext;

    // Movement flags
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean downPressed = false;
    private boolean isPaused = false;

    // Movement timer for smooth movement
    private long lastMoveTime = 0;
    private long moveInterval = 100_000_000; // 100ms per move

 @FXML
public void initialize() {
    gc = gameCanvas.getGraphicsContext2D();

    GameSettings settings = GameSettings.getInstance();
    grid = new Grid(settings.getGridWidth(), settings.getGridHeight());
    dropSpeed = settings.getInitialDropSpeed();

    gameContext = new GameContext();

    spawnNewPiece();

    pauseMenu.setVisible(false);
    gameOverMenu.setVisible(false);

    // Button actions
    resumeButton.setOnAction(e -> togglePause());
    retryButton.setOnAction(e -> restartGame());
    mainMenuButton.setOnAction(e -> goToMainMenu());  // works for Game Over menu

    // NEW: Pause menu buttons
    Button pauseMainMenuButton = (Button) pauseMenu.lookup("#mainMenuButton");
    if (pauseMainMenuButton != null) {
        pauseMainMenuButton.setOnAction(e -> goToMainMenu());
    }

    gameCanvas.setFocusTraversable(true);
    gameCanvas.setOnKeyPressed(this::handleKeyPressed);
    gameCanvas.setOnKeyReleased(this::handleKeyReleased);

    startGameLoop();
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

                // Smooth movement at fixed interval
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
        if (Math.random() < 0.2) piece = new SlowDownDecorator(piece);

        currentPiece = piece;
        currentPiece.setX(grid.getWidth() / 2 - 2);
        currentPiece.setY(0);

        GameLogger.getInstance().log("New piece spawned");
    }

    private void update() {
        currentPiece.drop();

        if (checkCollision()) {
            currentPiece.setY(currentPiece.getY() - 1);
            placePiece();

            int linesCleared = grid.clearFullLines();
            if (linesCleared > 0) {
                score += linesCleared * GameSettings.getInstance().getPointsPerLine();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        scoreLabel.setText("Score: " + score);
                    }
                });
            }

            spawnNewPiece();
            if (checkCollision()) stopGame();
        }
    }

    private void render() {
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        double cellWidth = gameCanvas.getWidth() / grid.getWidth();
        double cellHeight = gameCanvas.getHeight() / grid.getHeight();

        // Draw grid
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Cell cell = grid.getCell(x, y);
                gc.setFill(cell.isFilled() ? javafx.scene.paint.Color.GRAY : javafx.scene.paint.Color.LIGHTGRAY);
                gc.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }
        }

        // Draw current piece
        int[][] shape = currentPiece.getShape();
        javafx.scene.paint.Color color = GameSettings.getInstance().getPieceColors()[currentPiece.getTypeIndex()];
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
                    if (x < 0 || x >= grid.getWidth() || y >= grid.getHeight()) return true;
                    if (grid.getCell(x, y).isFilled()) return true;
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
    }

private void handleKeyPressed(KeyEvent event) {
    KeyCode code = event.getCode();
    if (code != null) {
        switch (code) {
            case LEFT:
                if (!isPaused) leftPressed = true;
                break;
            case RIGHT:
                if (!isPaused) rightPressed = true;
                break;
            case DOWN:
                if (!isPaused) downPressed = true;
                break;
            case UP:
                if (!isPaused) {
                    currentPiece.rotate();
                    if (checkCollision()) currentPiece.rotateBack();
                }
                break;
            case ESCAPE:
                togglePause();
                event.consume();
                break;
            default:
                break;
        }
    }
}

private void handleKeyReleased(KeyEvent event) {
    KeyCode code = event.getCode();
    if (code != null) {
        switch (code) {
            case LEFT:
                leftPressed = false;
                break;
            case RIGHT:
                rightPressed = false;
                break;
            case DOWN:
                downPressed = false;
                break;
            default:
                break;
        }
    }
}


    private void moveLeft() {
        currentPiece.setX(currentPiece.getX() - 1);
        if (checkCollision()) currentPiece.setX(currentPiece.getX() + 1);
    }

    private void moveRight() {
        currentPiece.setX(currentPiece.getX() + 1);
        if (checkCollision()) currentPiece.setX(currentPiece.getX() - 1);
    }

    private void moveDown() {
        currentPiece.setY(currentPiece.getY() + 1);
        if (checkCollision()) currentPiece.setY(currentPiece.getY() - 1);
    }

@FXML
public void togglePause() {
    if (!isPaused) {
        // Pause game loop
        timer.stop();

        // Reset movement flags
        leftPressed = false;
        rightPressed = false;
        downPressed = false;

        // Show pause menu
        pauseMenu.setVisible(true);
        pauseMenu.toFront();

        // Let buttons inside pause menu work
        pauseMenu.setMouseTransparent(false);

        // Remove focus from canvas temporarily
        gameCanvas.setFocusTraversable(false);
    } else {
        // Hide pause menu
        pauseMenu.setVisible(false);

        // Reset lastMoveTime to avoid jump
        lastMoveTime = System.nanoTime();

        // Resume game loop
        timer.start();

        // Refocus canvas to capture keys again
        gameCanvas.setFocusTraversable(true);
        gameCanvas.requestFocus();
    }
    isPaused = !isPaused;
}




    private void stopGame() {
        timer.stop();
        gameOverMenu.setVisible(true);
        gameOverMenu.toFront();
        finalScoreLabel.setText("Score: " + score);
    }

    private void restartGame() {
        gameOverMenu.setVisible(false);
        score = 0;
        scoreLabel.setText("Score: 0");
        grid.clearAllCells();
        spawnNewPiece();
        timer.start();
    }

    private void goToMainMenu() {
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
