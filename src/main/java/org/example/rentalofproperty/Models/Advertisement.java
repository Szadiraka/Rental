package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name="landLOrd_id")
    private UserModel landLord;      //орендодавець
    @ManyToOne
    @JoinColumn(name="housingType_id")
    private HousingType housingType;    // тип житла
    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;
    private int price;            // ціна оренди
    private int rentalDate;  // строк оренди(днів)
    @Column(length = 1024)
    private String description;  // опис житла

    private LocalDate date;      //дат розміщення оголошення

    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "advertisement")
    private List<Image> images;

    //колекція шляхів до сховища з зображеннями

    public Advertisement(UserModel landLord, HousingType housingType, City city, int price, int rentalDate, String description){
        this.city=city;
        this.rentalDate=rentalDate;
        this.description=description;
        this.housingType= housingType;
        this.price=price;
        this.landLord=landLord;
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

    public HousingType getHousingType() {
        return housingType;
    }

    public void setHousingType(HousingType housingType) {
        this.housingType = housingType;
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

    public UserModel getLandLord() {
        return landLord;
    }

    public void setLandLord(UserModel landLord) {
        this.landLord = landLord;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
