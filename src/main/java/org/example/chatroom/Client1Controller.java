package org.example.chatroom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.Socket;

public class Client1Controller {

    @FXML
    private TextField ipAddress;

    @FXML
    private TextField portNumber;

    @FXML
    private TextField userName;


    //NEED TO ADD ALERT MESSAGE WHEN TEXFIELD IS EMPTY
    public void start(ActionEvent event) {
        try {

            if (ipAddress.getText().isEmpty() || portNumber.getText().isEmpty() || userName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please fill all fields!");
                alert.showAndWait();
                return;
            }

            //Retrieving User input
            String ip = ipAddress.getText();
            int port = Integer.parseInt(portNumber.getText());
            String username = userName.getText();

            //Client socket
            Socket socket = new Socket(ip, port);
            Client client = new Client(socket, username);

            // Load the new FXML file (chat window)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chat.fxml"));
            Parent chatRoot = loader.load();

            //Passing client object to client2controller
            Client2Controller client2Controller = loader.getController();
            client2Controller.setClient(client);


            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(chatRoot);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
