package model;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private int id;
    private String location;
    private Map<Integer, Product> products; // key: productId
    private Employee manager;
    private boolean isActive;
    private double income;

    public Shop(int id, String location) {
        this.id = id;
        this.location = location;
        this.products = new HashMap<>();
        this.isActive = true;
        this.income = 0;
    }
    //не забыть посмотреть
    public boolean sellProduct(int productId, int quantity) {
        Product product = products.get(productId);
        if (product != null && product.decreaseQuantity(quantity)) {
            income += product.getPrice() * quantity;
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
    public boolean returnProduct(int productId, int quantity) {
        Product product = products.get(productId);
        if (product != null && product.increaseQuantity(quantity)) {
            income -= product.getPrice() * quantity; // Уменьшаем доход при возврате
            return true;
        }
        return false;
    }
}