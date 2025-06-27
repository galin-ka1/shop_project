package app.controller;

import app.domain.Product;
import app.service.ProductService;

import java.util.List;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public Product save(String name, double price) {
        Product product = new Product(name, price);
        return productService.save(product);

    }

    public List<Product> getAll() {
        return productService.getAllActiveProducts();
    }

    public Product getById(Long id){
        return productService.getById(id);
    }
    public void update(Long id, String name, double price){
      Product product= new Product(id, name, price);
      productService.update(product);
    }
    public void deleteById(Long id){
        productService.deleteById(id);
    }

    public void deleteByName(String name){
        productService.deleteByName(name);
    }
    public void restoreById(Long id){
        productService.restoreById(id);
    }

    public long getActiveProductsTotalCount(){
       return productService.getActiveProductsTotalCount();
    }
    public double getActiveProductsTotalCost(){
        return productService.getActiveProductsTotalCost();
    }
    public double getActiveProductsAveragePrice(){
        return productService.getActiveProductsAveragePrice();
    }
}
