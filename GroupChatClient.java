//Code for Client Side
//Created By:- Aniket Kumar Jha
import java.io.*;
import java.net.*;

public class GroupChatClient {
    public static void main(String[] args) {
        String hostname = "Server IP Address!"; 
        int port = 12345;

        Socket socket = null;
        BufferedReader serverInput = null; 
        BufferedReader userInput = null;
        PrintWriter serverOutput = null;

        try {
            socket = new Socket(hostname, port);
            serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));
            serverOutput = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter your name: ");
            String name = userInput.readLine();
            serverOutput.println(name);

            final BufferedReader finalServerInput = serverInput;

            Thread listenerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String messageFromServer;
                        while ((messageFromServer = finalServerInput.readLine()) != null) {
                            System.out.println(messageFromServer);
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading from server: " + e.getMessage());
                    }
                }
            });
            listenerThread.start();

            String userMessage;
            while (true) {
                userMessage = userInput.readLine();
                serverOutput.println(userMessage);

                if (userMessage.equalsIgnoreCase("exit")) {
                    System.out.println("You have disconnected.");
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            try {
                if (serverInput != null) serverInput.close();
                if (userInput != null) userInput.close();
                if (serverOutput != null) serverOutput.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}