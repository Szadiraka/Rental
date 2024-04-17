package org.example.rentalofproperty.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private long fromUser_id;
    private long toUser_id;
    private long order_id;

    public Message(String description, long fromUser_id, long toUser_id, long order_id){
        this.fromUser_id=fromUser_id;
        this.toUser_id=toUser_id;
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

    public long getFromUser_id() {
        return fromUser_id;
    }

    public void setFromUser_id(long fromUser_id) {
        this.fromUser_id = fromUser_id;
    }

    public long getToUser_id() {
        return toUser_id;
    }

    public void setToUser_id(long toUser_id) {
        this.toUser_id = toUser_id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }
}
