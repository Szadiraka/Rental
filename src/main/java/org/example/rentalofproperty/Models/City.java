package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class City  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    private List<UserModel> users;

    public City(String name,Country country){
        this.name=name;
        this.country=country;
    }

    public City() {

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


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
