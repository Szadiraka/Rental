package org.example.rentalofproperty.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    private long city_id;
    private  long role_id;
    private String mail;
    private String password;
    private LocalDate dateOfBirth;

    private boolean isLocked;   //блокування користувача
    private double rating;       //кількість балів
    private int countOfVoice;   //кількість голосів




    public UserModel(String name, String mail,String password,long city_id,long role_id, LocalDate dateOfBirth){
        this.name=name;
        this.city_id=city_id;
        this.mail=mail;
        this.password=password;
        this.dateOfBirth=dateOfBirth;
        this.role_id=role_id;
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

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
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

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
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
}
