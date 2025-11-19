module com.mycompany.tetrisgame {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.tetrisgame to javafx.fxml;
        opens com.mycompany.tetrisgame.controllers to javafx.fxml;

    
    exports com.mycompany.tetrisgame;
}
