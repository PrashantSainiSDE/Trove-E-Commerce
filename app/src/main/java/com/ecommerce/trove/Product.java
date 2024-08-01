package com.ecommerce.trove;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String image;
    private Double price;
    private String description;
    private String fullDescription;

    public Product(String id, String name, String image, Double price, String description, String fullDescription) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.fullDescription = fullDescription;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }
}
