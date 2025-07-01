package app.service;

import app.domain.Customer;


import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> getAllActiveCustomer();

    Customer getById(Long id);

    void updateById(Customer customer);

    void deleteCustomerById(Long id);

    void deleteCustomerByName(String name);

    void restoreById(Long id);

    long getAllActiveCustomersTotalCount();

    double getAllProductsInBasket(Long id);

    double getAverageCostProductsInBasket(Long id);

    void addActiveProductInBasket();

    void deleteProductById(Long id, Long id);

    void clearProductBasket(Long id);

}

