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

    public void setName(String newName) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double newPrice) {
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
        final StringBuffer sb = new StringBuffer("Product{");
        sb.append("id=").append(id);
        sb.append(", isActive=").append(isActive);
        sb.append(", name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
