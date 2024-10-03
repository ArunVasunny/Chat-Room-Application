package org.example.chatroom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        try
        {
            while(!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();
                System.out.println("A new Client has Connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch(Exception ex)
        {

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