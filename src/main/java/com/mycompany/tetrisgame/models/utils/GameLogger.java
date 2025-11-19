package com.mycompany.tetrisgame.models.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameLogger {

    public enum LogCategory { INFO, STATE, DECORATOR, DEBUG }

    private static GameLogger instance;
    private PrintWriter writer;

    private GameLogger() {
        try {
            writer = new PrintWriter(new FileWriter("logs/game.log", true), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameLogger getInstance() {
        if (instance == null) {
            instance = new GameLogger();
        }
        return instance;
    }

    // Méthode générale
    public void log(LogCategory category, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String formatted = "[" + timestamp + "] [" + category + "] " + message;

        writer.println(formatted);
        System.out.println(formatted);
    }

    // Compatibilité avec l'ancien code (log("msg"))
    public void log(String message) {
        log(LogCategory.INFO, message);
    }

    // Méthodes que ton Controller demande :
    public void info(String message) {
        log(LogCategory.INFO, message);
    }

    public void state(String message) {
        log(LogCategory.STATE, message);
    }

    public void decorator(String message) {
        log(LogCategory.DECORATOR, message);
    }

    public void debug(String message) {
        log(LogCategory.DEBUG, message);
    }

    public void logGameState(int score, int level, int totalLinesCleared, String message) {
        String logMessage = String.format(
                "Score: %d | Level: %d | Total Lines: %d | %s",
                score, level, totalLinesCleared, message
        );
        log(LogCategory.STATE, logMessage);
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
