//product add karne ka file 
package com.farmermarket.model;

public class Product {
    private int id;
    private int farmerId;
    private String name;
    private String description;
    private double price;
    private String quantity;
    private String category;

    public Product() {}

    public Product(int id, int farmerId, String name, String description, double price, String quantity, String category) {
        this.id = id;
        this.farmerId = farmerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // Getters and Setters
    // (Auto-generate in IDE)
}
