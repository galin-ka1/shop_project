package app.repositories;

import app.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductRepositoryMap implements ProductRepository {

    private final Map<Long, Product> database = new HashMap<>();
    private long currentId = 0;


    @Override
    public Product save(Product product) {
        product.setId(++currentId);// префиксная запись с 1, а если постпрефиксная(currentId++) то c 0
        database.put((int) currentId, product);// но можно database.put(product.getId(), product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(database.values());

    }

    @Override
    public Product findById(Long id) {
      /*  return database.values().stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElse(null);*/
        return database.get(id);
    }

    @Override
    public boolean deleteById(Long id) {

        Product oldProduct = findById(id);
        //
        if (oldProduct == null) {
            return false;
        }
        oldProduct.setActive(false);
        return true;
    }

    @Override
    public Product updateById(Product product) {
        Long id = product.getId();// из продукта достаем id
        double newPrice = product.getPrice();// из продукта достаем price
        String name = product.getName();// // из продукта достаем name

        Product oldProduct = findById(id);
        // если он существует то поставить новую цену и имя
        if (oldProduct != null) {
            oldProduct.setName();
            oldProduct.setPrice();
        }
        return oldProduct;
    }
}
