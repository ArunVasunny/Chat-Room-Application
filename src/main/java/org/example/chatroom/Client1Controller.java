package org.example.chatroom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Client1Controller {

    @FXML
    private TextField ipAddress;

    @FXML
    private TextField portNumber;

    @FXML
    private TextField userName;

    public void getUsername()
    {
        String username = userName.getText();
    }

    public void getPort()
    {
        String port = portNumber.getText();
    }

    public void getIp()
    {
        String ip = ipAddress.getText();
    }

    public void start(ActionEvent event) {
        try {
            // Load the new FXML file (chat window)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent chatRoot = loader.load();

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(chatRoot);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
