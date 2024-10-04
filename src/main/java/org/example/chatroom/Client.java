package org.example.chatroom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username)
    {
        try
        {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        }
        catch(Exception ex)
        {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    //This will send the username to client handler
    public void sendMessage()
    {
        try{
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner sc = new Scanner(System.in);
            while(socket.isConnected())
            {
                String messageToSend = sc.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (Exception ex)
        {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage()
    {
        new Thread(new Runnable()
        {

            @Override
            public void run() {
                String mesgFromGroupChat;

                while(socket.isConnected())
                try
                {
                    mesgFromGroupChat = bufferedReader.readLine();
                    System.out.println(mesgFromGroupChat);

                }
                catch(Exception ex)
                {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }

        }).start();

    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {

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

    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Your Username ");
        String username = sc.nextLine();
        Socket socket = new Socket("localhost",7272);

        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}