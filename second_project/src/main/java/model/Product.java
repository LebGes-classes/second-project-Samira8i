package model;

import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private double price;
    private double purchasePrice;
    private int quantity;
    private LocalDate arrivalDate;
    private String manufacturer;

    public Product(int id, String name, double price, double purchasePrice,
                   int quantity, String manufacturer) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.arrivalDate = LocalDate.now();
    }


    public double calculateProfit() {
        return (price - purchasePrice) * quantity;
    }
    public boolean increaseQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
            return true;
        }
        return false;
    }

    public boolean decreaseQuantity(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return String.format("%s (ID: %d, Цена: %.2f, Кол-во: %d)",
                name, id, price, quantity);
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}