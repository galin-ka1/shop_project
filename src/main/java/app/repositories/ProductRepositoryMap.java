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
        product.setId(++currentId);// префиксная запись с 1, а если пост префиксная(currentId++) то с 0
        database.put(currentId, product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Product findById(Long id) {
//        return database.values().stream()
//                .filter(p -> p.getId().equals(id))
//                .findFirst()
//                .orElse(null);
        return database.get(id);
    }

    @Override
    public boolean deleteById(Long id) {
        Product oldProduct = findById(id);

        if (oldProduct == null) {
            return false;
        }
        oldProduct.setActive(false);
        return true;
    }

    @Override
    public Product updateById(Product product) {
        Long id = product.getId();// из продукта достаем id
        double newPrice = product.getPrice();
        String newName = product.getName();
        boolean newActive = product.isActive();

        Product oldProduct = findById(id);

        if (oldProduct != null) {
            oldProduct.setName(newName);
            oldProduct.setPrice(newPrice);
        }

        return oldProduct;
    }

 /*   public static void main(String[] args) {
        ProductRepository repository = new ProductRepositoryMap();

        System.out.println(repository.save(new Product(true, "Coffee", 3.0)));
        System.out.println(repository.save(new Product(false, "Baguette", 4.0)));
        System.out.println(repository.findAll());
        System.out.println(repository.findById(2L));
        System.out.println("------------------ delete-------------------");
        repository.deleteById(1L); // удалить
        System.out.println(repository.findById(1L));// найти по ид номеру 1
        System.out.println("------------------ update-------------------");
        Product newProduct = new Product(true, "Baguette", 7.0);// создаем новый продукт
        newProduct.setId(2L);
        System.out.println(repository.updateById(newProduct));

    }*/
}
/*
    @Override
    public Product save(Product product) {
        product.setId(++currentId);
        database.put( currentId, product);// но можно database.put(product.getId(), product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(database.values());

    }

    @Override
    public Product findById(Long id) {

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
        Long id = product.getId();
        double newPrice = product.getPrice();// из продукта достаем price
        String name = product.getName();// // из продукта достаем name
        boolean newActive=product.isActive();

        Product oldProduct = findById(id);
        // если он существует, то поставить новую цену и имя
        if (oldProduct != null) {
            oldProduct.setName();
            oldProduct.setPrice();
        }
        return oldProduct;
    }
*/

