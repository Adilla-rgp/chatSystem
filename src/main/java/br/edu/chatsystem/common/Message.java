 package br.edu.chatsystem.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String from;
    private final String content;
    private final String type;
    private final String timestamp;

    public Message(String from, String content, String type) {
        this.from = from;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getFrom() {
        return from;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
