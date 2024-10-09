package org.example.chatroom;

import java.io.IOException;
import java.net.*;

public class Server {

    private ServerSocket serverSocket;
    //Constructor
    public Server(ServerSocket serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    //Method for Starting server
    public void startServer()
    {
        System.out.println("Server Started");
        try
        {
            while(!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has Connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                String clientUsername = clientHandler.getClientUsername();
                System.out.println(clientUsername + " has Joined the Chat");
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    //Method to close server
    public void closeServerSocket()
    {
        try
        {
            if(serverSocket != null)
            {
                serverSocket.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }





    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(7272);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}