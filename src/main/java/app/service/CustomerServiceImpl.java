package app.service;

import app.domain.Customer;
import app.domain.Product;
import app.exceptions.*;
import app.repositories.CustomerRepository;
import app.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private Product product;

    public CustomerServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Customer save(Customer customer) {//Сохранить покупателя в базе данных (при сохранении покупатель автоматически считается
        //активным).
        if (customer == null) {
            throw new CustomerSaveException("Customer cannot be null");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            throw new CustomerSaveException("Customer name should be at least 3 characters long");
        }

        customer.setActive(true);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllActiveCustomers() {//Вернуть всех покупателей из базы данных (активных)
        return customerRepository.findAll().stream()
                .filter(x -> x.isActive())
                .collect(Collectors.toList());
    }

    @Override
    public Customer getById(Long id) {// Вернуть одного покупателя из базы данных по его идентификатору (если он активен)
        Customer customer = customerRepository.getById(id);

        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException("Customer with id = " + id + " not found");
        }
        return customer;
    }

    @Override
    public void updateById(Customer customer) {//Изменить одного покупателя в базе данных по его идентификатору

        if (customer == null) {
            throw new CustomerUpdateException("Customer cannot be null");
        }

        Long id = customer.getId();
        if (id == null || id < 0) {
            throw new CustomerUpdateException("Customer id should be positive");
        }

        String name = customer.getName();
        if (name == null || name.trim().isEmpty() || name.length() < 3) {
            throw new CustomerUpdateException("Customer name should be at least 3 characters long");
        }
        customerRepository.updateById(customer);
    }

    @Override
    public void deleteCustomerById(Long id) {//Удалить покупателя из базы данных по его идентификатору
        Customer customer=getById(id);
        getById(id).setActive(false);
        customerRepository.updateById(customer);
    }

    @Override
    public void deleteCustomerByName(String name) {//Удалить покупателя из базы данных по его имени.
        Customer customer = getAllActiveCustomers()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with name = " + name + " not found");
        }
        customer.setActive(false);
        customerRepository.updateById(customer);
    }

    @Override
    public void restoreById(Long id) {
        Customer customer = customerRepository.getById(id);// Восстановить удалённого покупателя в базе данных по его идентификатору

        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id = " + id + " not found");
        }
        customer.setActive(true);
        customerRepository.updateById(customer);
    }

    @Override
    public long getAllActiveCustomersTotalCount() {//Вернуть общее количество покупателей в базе данных (активных).
        return getAllActiveCustomers().size();
    }

    @Override
    public double getAllProductsInBasket(Long id) {//Вернуть стоимость корзины покупателя по его идентификатору (если он активен)
        Customer customer = customerRepository.getById(id);

        if (id == null || id < 0) {
            throw new CustomerUpdateException("Customer id should be positive");
        }
        List<Product> products = customer.getProducts();
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    @Override
    public double getAverageCostProductsInBasket(Long id) {// Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он
        //активен)
        Customer customer = customerRepository.getById(id);
        if (id == null || id < 0) {
            throw new CustomerUpdateException("Customer id should be positive");
        }
        List<Product> products = customer.getProducts();

        if (products == null || products.isEmpty()) {
            return 0.0;
        }

        return products.stream()
                .mapToDouble(Product::getPrice)
                .average()
                .orElse(0.0);
    }

    @Override
    public void addActiveProductInBasket(Long id, Long id1) {//Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
        Customer customer = customerRepository.getById(id);

        if (id == null || id < 0) {
            throw new CustomerUpdateException("Customer id should be positive");
        }
        Product products = productRepository.findById(id1);

        if (products == null || !products.isActive()) {
            throw new ProductNotFoundException("Product cannot be null");
        }
        customer.getProducts().add(product);
        customerRepository.updateById(customer);
    }

    @Override
    public List<Product> deleteProductById(Long id, Long id1) {//Удалить товар из корзины покупателя по их идентификаторам
        Customer customer = customerRepository.getById(id);
        if (id == null || id < 0) {
            throw new CustomerUpdateException("Customer id should be positive");
        }
        List<Product> products = customer.getProducts();
        if (products == null || products.isEmpty()) {
            return products;
        }
        List<Product> updatedProducts = products.stream()
                .filter(p -> !p.getId().equals(id1))
                .collect(Collectors.toList());

        customer.setProducts(updatedProducts);
        customerRepository.updateById(customer);
        return updatedProducts;
    }

    @Override
    public void clearProductBasket(Long id) {//Полностью очистить корзину покупателя по его идентификатору (если он активен)
        Customer customer = customerRepository.getById(id);
        if (id == null || id < 0) {
            throw new CustomerUpdateException("Customer id should be positive");
        }

        List<Product> products = customer.getProducts();
        if (products == null || products.isEmpty()) {
            throw new ProductNotFoundException("Product not found"); // No products to clear
        }

        products.clear();
        customerRepository.updateById(customer);
    }
}

