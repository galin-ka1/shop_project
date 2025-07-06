package app.domain;

//POGO  - Plain Old Java Object POJO (Plain Old Java Object) — это обычный Java-объект, который не зависит от
// каких-либо специфических фреймворков или интерфейсов. Он может содержать любое количество полей и методов,
// которые не обязательно должны быть приватными или публичными. POJO обычно используется
// для инкапсуляции бизнес-логики в приложениях


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//POJO - Plain Old Java Object
public class Customer {


    private Long id;
    private boolean isActive;
    private String name;
    private final List<Product> products = new ArrayList<>();

    public Customer(boolean isActive, String name) {
        this.isActive = isActive;
        this.name = name;
    }
    public Customer(Long id) {
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

    public List<Product> getProducts() {
        return products;

    }
    public List<Product> addProducts(Long id1) {
        Product product = new Product(id1);
        products.add(product);
        return products;
    }
    public void setProducts(List<Product> updatedProducts) {

    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return isActive == customer.isActive && Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(products, customer.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isActive, name, products);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }



}