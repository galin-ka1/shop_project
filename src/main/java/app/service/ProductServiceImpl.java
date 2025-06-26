package app.service;

import app.domain.Product;
import app.exceptions.ProductNotFoundException;
import app.exceptions.ProductSaveException;
import app.exceptions.ProductUpdateException;
import app.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        if (product == null) {
            throw new ProductSaveException("Product can not be null");
        }
        String name = product.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            throw new ProductSaveException("Product name should be at least 3 characters long");
        }
        if (product.getPrice() <= 0) {
            throw new ProductSaveException("Product price van not be negative and zero");
        }

        product.setActive(true);
        return repository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return repository.findAll().stream()
                .filter(x -> x.isActive())
                .collect(Collectors.toList());
    }

    @Override
    public Product getById(Long id) {
        Product product = repository.findById(id);
        if (product == null || !product.isActive()) { // если продукт неактивный!
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return product;
    }

    @Override
    public void update(Product product) {

        if (product == null) {
            throw new ProductUpdateException("Product can not be null");
        }
        Long id = product.getId();
        if (id == null || id < 0) {
            throw new ProductUpdateException("Product name should be positive");
        }

        String name = product.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            throw new ProductUpdateException("Product name should be at least 3 characters long");
        }
        if (product.getPrice() <= 0) {
            throw new ProductUpdateException("Product price van not be negative and zero");
        }
        repository.updateById(product);

    }

    @Override
    public void deleteById(Long id) {
        getById(id).setActive(false);

    }

    @Override
    public void deleteByName(String name) {
        Product product = getAllActiveProducts()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);
        if (product == null) {
            throw new ProductNotFoundException("Product with name " + name + " not found");
        }
        product.setActive(false);
    }

    @Override
    public void restoreById(Long id) {
        getById(id).setActive(true);
    }

    @Override
    public long getActiveProductsTotalCount() {

        return getAllActiveProducts().size();
    }

    @Override
    public double getActiveProductsTotalCost() {
        return getAllActiveProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public double getActiveProductsAveragePrice() {
        return getAllActiveProducts()
                .stream()
                .mapToDouble(Product::getPrice)
                .average().orElse(0);
    }
}
