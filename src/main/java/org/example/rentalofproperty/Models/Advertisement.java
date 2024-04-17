package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long landLord_id;      //орендодавець
    private long housingType_id;    // тип житла
    private long city_id;
    private int price;            // ціна оренди
    private int rentalDate;  // строк оренди(днів)
    @Column(length = 1024)
    private String description;  // опис житла

    private LocalDate date;      //дат розміщення оголошення

    //колекція шляхів до сховища з зображеннями

    public Advertisement(long landLord_id, long housingType_id, long city_id, int price, int rentalDate, String description){
        this.city_id=city_id;
        this.rentalDate=rentalDate;
        this.description=description;
        this.housingType_id= housingType_id;
        this.price=price;
        this.landLord_id=landLord_id;
        this.date=LocalDate.now();
    }


    public Advertisement() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLandLord_id() {
        return landLord_id;
    }

    public void setLandLord_id(long landLord_id) {
        this.landLord_id = landLord_id;
    }

    public long getHousingType_id() {
        return housingType_id;
    }

    public void setHousingType_id(long housingType_id) {
        this.housingType_id = housingType_id;
    }

    public long getCity_id() {
        return city_id;
    }

    public void setCity_id(long city_id) {
        this.city_id = city_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(int rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
