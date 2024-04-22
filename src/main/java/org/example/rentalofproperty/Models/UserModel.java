package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private  Role role;
    private String mail;
    private String password;
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "renter",cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;
    @OneToMany(mappedBy = "recipient")
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "landLord")
    private List<Advertisement> advertisements;


    private boolean isLocked;   //блокування користувача
    private double rating;       //кількість балів
    private int countOfVoice;   //кількість голосів




    public UserModel(String name, String mail,String password,City city,Role role, LocalDate dateOfBirth){
        this.name=name;
        this.city=city;
        this.mail=mail;
        this.password=password;
        this.dateOfBirth=dateOfBirth;
        this.role=role;
        this.isLocked=false;
        this.countOfVoice=0;
        this.rating=0;
    }
    public UserModel(){

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


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCountOfVoice() {
        return countOfVoice;
    }

    public void setCountOfVoice(int countOfVoice) {
        this.countOfVoice = countOfVoice;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public void setAllParameters(String name, City city, Role role, String mail,String password, LocalDate dateOfBirth){
        this.name=name;
        this.city=city;
        this.role=role;
        this.mail=mail;
        this.password=password;
        this.dateOfBirth=dateOfBirth;
    }
}
