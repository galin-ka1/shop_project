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
    public Product save(Product product) {//Сохранить продукт в базе данных (при сохранении продукт автоматически считается
        //активным)
        if (product == null) {
            throw new ProductSaveException("Product cannot be null");
        }

        String name = product.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            throw new ProductSaveException("Product name should be at least 3 characters long");
        }

        if (product.getPrice() <= 0) {
            throw new ProductSaveException("Product price cannot be negative and zero");
        }

        product.setActive(true);
        return repository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {// Вернуть все продукты из базы данных (активные)
        return repository.findAll().stream()
                .filter(x -> x.isActive())
                .collect(Collectors.toList());
    }

    @Override
    public Product getById(Long id) {//Вернуть один продукт из базы данных по его идентификатору (если он активен)
        Product product = repository.findById(id);

        if (product == null || !product.isActive()) {
            throw new ProductNotFoundException("Product with id = " + id + " nor found");
        }
        return product;
    }

    @Override
    public void update(Product product) {// Изменить один продукт в базе данных по его идентификатору.
        if (product == null) {
            throw new ProductUpdateException("Product cannot be null");
        }

        Long id = product.getId();
        if (id == null || id < 0) {
            throw new ProductUpdateException("Product id should be positive");
        }

        String name = product.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            throw new ProductUpdateException("Product name should be at least 3 characters long");
        }

        if (product.getPrice() <= 0) {
            throw new ProductUpdateException("Product price cannot be negative and zero");
        }

        repository.updateById(product);
    }

    @Override
    public void deleteById(Long id) {// Удалить продукт из базы данных по его идентификатору
        getById(id).setActive(false);
    }

    @Override
    public void deleteByName(String name) {// Удалить продукт из базы данных по его наименованию.
        Product product = getAllActiveProducts()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (product == null) {
            throw new ProductNotFoundException("Product with name = " + name + " not found");
        }
        product.setActive(false);
    }

    @Override
    public void restoreById(Long id) {// Восстановить удалённый продукт в базе данных по его идентификатору
        Product product = repository.findById(id);

        if (product == null) {
            throw new ProductNotFoundException("Product with id = " + id + " nor found");
        }
        product.setActive(true);

    }

    @Override
    public long getActiveProductsTotalCount() {// Вернуть общее количество продуктов в базе данных (активных).
        return getAllActiveProducts().size();
    }

    @Override
    public double getActiveProductsTotalCost() {// Вернуть суммарную стоимость всех продуктов в базе данных (активных).
        return getAllActiveProducts().stream()
                .mapToDouble(p -> p.getPrice())
                .sum();
    }

    @Override
    public double getActiveProductsAveragePrice() {// Вернуть среднюю стоимость продукта в базе данных (из активных)
        return getAllActiveProducts().stream()
                .mapToDouble(p -> p.getPrice())
                .average()
                .orElse(0);
    }
}