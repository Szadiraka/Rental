package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MessageType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @OneToMany(mappedBy = "messageType", cascade = CascadeType.ALL)
    private List<Message> messages;

    public MessageType(String name) {
        this.name = name;
    }

    public MessageType() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}