package app.repositories;

import app.domain.Customer;
import app.domain.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepositoryMap implements CustomerRepository {

    private Map<Long, Customer> database = new HashMap<>();
    private long currentId = 0;

    @Override
    public Customer save(Customer customer) {
        customer.setId(++currentId);// префиксная запись с 1, а если постпрефиксная(currentId++) то c 0
        database.put(currentId, customer);// но можно database.put(product.getId(), product);
        return customer;

    }

    @Override
    public List<Customer> findAll(Long id) {
        return new ArrayList<>(database.values());
    }

    @Override
    public Customer findById(Long id) {
        /*  return database.values().stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);*/
        return database.get(id);

    }

    @Override
    public Customer updateById(Customer customer) {
        Long id = customer.getId(); // из customer достаем id
        String name = customer.getName();// из customer достаем name

        Customer oldCustomer = findById(id);
        // если он существует то поставить новое имя
        if (oldCustomer != null) {
            oldCustomer.setName();
        }
        return oldCustomer;
    }

    @Override
    public boolean deleteById(Long id) {
        Customer oldCustomer = findById(id);
        //
        if (oldCustomer == null) {
            return false;
        }
        oldCustomer.setActive(false);
        return true;
    }

}
