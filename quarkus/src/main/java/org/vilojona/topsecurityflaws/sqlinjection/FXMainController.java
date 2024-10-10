package org.vilojona.topsecurityflaws.sqlinjection;

import java.sql.SQLException;

import org.vilojona.topsecurityflaws.common.DBService;

import io.quarkiverse.fx.views.FxView;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@FxView("app")
@Dependent
public class FXMainController {

    @FXML
    private VBox chatBox;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label responseLabel;

    @FXML
    Parent root;

    @Inject
    DBService dbService;

    @FXML
    public void initialize() {
        var stage = new Stage();
        var scene = new Scene(this.root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    @ActivateRequestContext
    public void handleClickMeAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            var username = usernameField.getText();
            var password = passwordField.getText();

            Log.info("User area " + username + " is trying to login");

            if (!username.isEmpty()) {
                try {
                    String loginResponse = dbService.login(username, password);
                    responseLabel.setText(loginResponse);
                    responseLabel.setStyle("-fx-background-color:" + (loginResponse.contains("authenticated")? "green;" : "red;"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
