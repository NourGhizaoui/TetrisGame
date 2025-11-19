
package com.mycompany.tetrisgame.models.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//GameLogger est une classe utilitaire pour enregistrer tous les événements importants du jeu.
//Elle utilise le Singleton Pattern, ce qui signifie qu’il n’existe qu’une seule instance de logger dans tout le jeu.
public class GameLogger {

    private static GameLogger instance;   // instance unique
    private PrintWriter writer;

    // Private constructor pour Singleton
    //Le constructeur est privé, impossible de créer plusieurs instances depuis l’extérieur.
    private GameLogger() {
        try {
            writer = new PrintWriter(new FileWriter("logs/game.log", true), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode publique pour récupérer l'instance unique
    public static GameLogger getInstance() {
        if (instance == null) {
            instance = new GameLogger();
        }
        return instance;
    }

    // Méthode pour logguer un événement
    //Ajoute automatiquement un timestamp au message
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.println("[" + timestamp + "] " + message);
        System.out.println("LOG: " + message); // affichage console optionnel
    }
    
    
    private void write(String type, String message) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        writer.println("[" + timestamp + "] [" + type + "] " + message);
        System.out.println("LOG: [" + type + "] " + message);
    }
    
    
  // --- TYPES DE LOG ---
    public void info(String message) {
        write("INFO", message);
    }

    public void state(String object, String oldState, String newState) {
        write("STATE", object + ": " + oldState + " -> " + newState);
    }

    public void event(String message) {
        write("EVENT", message);
    }

    public void decorator(String decoratorName, String target, String action) {
        write("DECORATOR", decoratorName + " " + action + " to " + target);
    }

    public void error(String message) {
        write("ERROR", message);
    }
    
    // Fermer le logger proprement (à appeler à la fin du jeu)
    //Ferme correctement le fichier à la fin du jeu pour éviter les pertes de données.
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
