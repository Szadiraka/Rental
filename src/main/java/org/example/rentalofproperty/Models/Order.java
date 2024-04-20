package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name="advertisement_id")

    private Advertisement advertisement;    // id повідомлення
    @ManyToOne
    @JoinColumn(name="renter_id")
    private UserModel renter;     //  id орендаря
    private LocalDate date;    //дата створення

    @ManyToOne
    @JoinColumn(name="status_id",nullable = false)
    private Status status;    //статус ордера (створений, підтверджений, відхилений, виконаний

    public Order(Advertisement advertisement, UserModel renter, Status status){
        this.date=LocalDate.now();
        this.advertisement=advertisement;
        this.renter=renter;
        this.status=status;
    }

    public Order() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserModel getRenter() {
        return renter;
    }

    public void setRenter(UserModel renter) {
        this.renter = renter;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
