//Code for Server Side
//Created By:- Aniket Kumar Jha
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GroupChatServer {
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int port = 12345;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for clients to connect...");

            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String serverMessage = scanner.nextLine();
                        broadcastMessage("Server: " + serverMessage, null);
                    }
                }
            });
            serverThread.start();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                synchronized (clientHandlers) {
                    clientHandlers.add(clientHandler);
                }
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String formattedMessage = "[" + timestamp + "] " + message;

        synchronized (clientHandlers) {
            for (ClientHandler client : clientHandlers) {
                if (client != sender) {
                    client.sendMessage(formattedMessage);
                }
            }
        }
        if (sender != null) {
            sender.sendMessage("[Message sent] " + message);
        }
    }

    public static void sendNotificationToAllClients(String notification) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String formattedNotification = "[" + timestamp + "] " + notification;
        
        synchronized (clientHandlers) {
            for (ClientHandler client : clientHandlers) {
                client.sendMessage(formattedNotification);
            }
        }
    }

    public static Set<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private String clientName;
    private boolean running = true;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            output.println("Enter your name: ");
            clientName = input.readLine();

            System.out.println(clientName + " has connected.");
            GroupChatServer.sendNotificationToAllClients(clientName + " has joined the chat!");

            String message;
            while (running && (message = input.readLine()) != null) {
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println(clientName + ": " + message);
                GroupChatServer.broadcastMessage(clientName + ": " + message, this);
            }
        } catch (IOException e) {
            System.err.println("Error with client " + clientName + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }

            synchronized (GroupChatServer.getClientHandlers()) {
                GroupChatServer.getClientHandlers().remove(this);
            }
            GroupChatServer.sendNotificationToAllClients(clientName + " has left the chat.");
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }
}