package org.example.rentalofproperty.Models;

import jakarta.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(length = 1024)
    private String path;

    @ManyToOne
    @JoinColumn(name="advertisement_id")
    private Advertisement advertisement;

    public Image(String path,Advertisement advertisement){
        this.path=path;
        this.advertisement=advertisement;
    }

    public Image() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
