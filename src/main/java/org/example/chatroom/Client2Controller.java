package org.example.chatroom;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Client2Controller {

    @FXML
    private TextField userMessg;

    @FXML
    private static VBox vboxMessages;

    @FXML
    private ScrollPane scroll;

    private Client client;

    public void setClient(Client client) {
        this.client = client;

        client.listenForMessage(vboxMessages);
    }

    public void sendMessage()
    {
        if(client != null && !userMessg.getText().isEmpty())
        {
            String message = userMessg.getText();
            client.sendMessage(message);
        }
    }

    public static void displayMessage(String message)
    {
        Label messageLabel = new Label(message);
        vboxMessages.getChildren().add(messageLabel);
    }

}
