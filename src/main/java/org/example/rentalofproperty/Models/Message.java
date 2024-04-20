package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    @ManyToOne
    @JoinColumn(name="sender_id")
    private UserModel sender;

    @ManyToOne
    @JoinColumn(name="recipient_id")
    private UserModel recipient;
    private long order_id;

    public Message(String description, UserModel sender, UserModel recipient, long order_id){
        this.sender=sender;
        this.recipient=recipient;
        this.order_id=order_id;
        this.description=description;
    }

    public Message() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public UserModel getRecipient() {
        return recipient;
    }

    public void setRecipient(UserModel recipient) {
        this.recipient = recipient;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }
}
