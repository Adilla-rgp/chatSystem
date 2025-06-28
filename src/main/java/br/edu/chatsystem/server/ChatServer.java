package br.edu.chatsystem.server;
import br.edu.chatsystem.auth.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    public static final int PORT = 12345;
    public static final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("[Servidor] Iniciando servidor na porta " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[Servidor] Conexão de: " + socket.getRemoteSocketAddress());
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("[Servidor] Erro: " + e.getMessage());
        }
    }
}
