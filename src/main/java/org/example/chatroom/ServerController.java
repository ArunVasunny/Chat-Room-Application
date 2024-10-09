package org.example.chatroom;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.*;
import java.util.ResourceBundle;


public class ServerController implements Initializable {

    @FXML
    private Label ipAddress;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{

            String ip = Inet4Address.getLocalHost().getHostAddress();
            ipAddress.setText("IP Address:--  " + ip);

        }
        catch(UnknownHostException ue)
        {
            ue.printStackTrace();
        }
    }
}