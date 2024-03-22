package org.example.model;

public class Message {

    private String from;
    private String text;
    private String name;

    public Message() {
    }

    public Message(String from, String text, String name) {
        this.from = from;
        this.text = text;
        this.name = name;
    }

    public Message(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}