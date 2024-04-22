package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class HousingType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

    @OneToMany(mappedBy = "housingType",cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;

    public HousingType(String name){
        this.name=name;
    }

    public HousingType() {

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

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
