package org.example.rentalofproperty.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long advertisement_id;    // id повідомлення
    private long renter_id;     //  id орендаря
    private LocalDate date;    //дата створення

    private long status_id;    //статус ордера (створений, підтверджений, відхилений, виконаний

    public Order(long advertisement_id, long renter_id, long status_id){
        this.date=LocalDate.now();
        this.advertisement_id=advertisement_id;
        this.renter_id=renter_id;
        this.status_id=status_id;
    }

    public Order() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAdvertisement_id() {
        return advertisement_id;
    }

    public void setAdvertisement_id(long advertisement_id) {
        this.advertisement_id = advertisement_id;
    }

    public long getRenter_id() {
        return renter_id;
    }

    public void setRenter_id(long renter_id) {
        this.renter_id = renter_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(long status_id) {
        this.status_id = status_id;
    }
}
