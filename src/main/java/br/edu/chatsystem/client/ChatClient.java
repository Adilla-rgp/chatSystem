 
package br.edu.chatsystem.client;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in))
        ) {
            // Thread para receber mensagens do servidor
            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("[Cliente] Conexão encerrada.");
                }
            }).start();

            // Login
            String prompt = in.readLine();
            System.out.print(prompt);
            String name = console.readLine();
            out.write("login|" + name + "\n");
            out.flush();

            System.out.println("[Cliente] Digite mensagens ou 'sair' para sair.");

            String input;
            while ((input = console.readLine()) != null) {
                if (input.equalsIgnoreCase("sair")) {
                    out.write("logout|\n");
                    out.flush();
                    break;
                }
                out.write("message|" + input + "\n");
                out.flush();
            }

        } catch (IOException e) {
            System.err.println("[Erro] Falha na conexão com o servidor.");
        }
    }
}
