package br.edu.chatsystem.auth;

import br.edu.chatsystem.common.Message;

import java.io.BufferedWriter;
import java.io.IOException;

public class User {
    private final String name;
    private final BufferedWriter writer;

    public User(String name, BufferedWriter writer) {
        this.name = name;
        this.writer = writer;
    }

    public String getName() {
        return name;
    }

    public void sendMessage(Message message) {
        try {
            writer.write("[" + message.getTimestamp() + "] " + message.getFrom() + ": " + message.getContent() + "\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("[User] Erro ao enviar mensagem para " + name);
        }
    }
}

