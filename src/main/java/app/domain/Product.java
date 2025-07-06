package app.domain;

import java.util.Objects;

public class Product {

    private Long id;
    private boolean isActive;
    private String name;
    private double price;

    public Product(boolean isActive, String name, double price) {
        this.isActive = isActive;
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price) {// перегрузка метода
        this.name = name;
        this.price = price;
    }

    public Product(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public Product(Long id) {
        this.id = id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return isActive == product.isActive && Double.compare(price, product.price) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, name, price);
    }

    @Override
    public String toString() {
        return "\u001B[32m"+"                            Product: " +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", price=" + price  + "\u001B[0m";
    }


}