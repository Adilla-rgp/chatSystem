package br.edu.chatsystem.server;

import br.edu.chatsystem.auth.User;
import br.edu.chatsystem.common.Message;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private User user;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {
            writer.write("login|Digite seu nome:\n");
            writer.flush();

            String input = reader.readLine();
            if (input != null && input.startsWith("login|")) {
                String name = input.split("\\|", 2)[1].trim();

                if (name.isEmpty()) {
                    writer.write("login|Nome inválido. Encerrando conexão.\n");
                    writer.flush();
                    socket.close();
                    return;
                }

                user = new User(name, writer);
                ChatServer.users.add(user);
                broadcast("Servidor", name + " entrou no chat.");

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("message|")) {
                        String msg = line.split("\\|", 2)[1];
                        broadcast(user.getName(), msg);
                    } else if (line.equals("logout|")) {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("[Handler] Erro: " + e.getMessage());
        } finally {
            if (user != null) {
                ChatServer.users.remove(user);
                broadcast("Servidor", user.getName() + " saiu do chat.");
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("[Handler] Erro ao fechar socket.");
                }
            }
        }
    }

    private void broadcast(String from, String message) {
        Message msg = new Message(from, message, "texto");
        for (User u : ChatServer.users) {
            u.sendMessage(msg);
        }
    }
}
