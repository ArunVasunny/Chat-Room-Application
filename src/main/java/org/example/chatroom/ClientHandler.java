package org.example.chatroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket socket)
    {

        try
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the Chat!");
        }
        catch(Exception ex)
        {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }

    }

    public String getClientUsername()
    {
        return this.clientUsername;
    }

    @Override
    public void run() {
        String mesgFromClient;

        while(socket.isConnected())
        {
            try{
                mesgFromClient = bufferedReader.readLine();
                broadcastMessage(mesgFromClient);
            }
            catch (Exception ex)
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    private void broadcastMessage(String messageToSend) {
        for(ClientHandler clientHandler : clientHandlers)
        {
            try
            {
                if(!clientHandler.clientUsername.equals(clientUsername)) // if username isnt equal it'll display username to your chat
                {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();

                }
            }
            catch (Exception ex)
            {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }

    }

    public void removeClientHandler()
    {
        clientHandlers.remove(this);
        System.out.println( clientUsername + " has left the Chat");
        broadcastMessage("SERVER: " + clientUsername + " has left the Chat");
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

        removeClientHandler();
        try{
            if(bufferedReader != null)
            {
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null)
            {
                socket.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }




}
