package app.controller;


import app.domain.Customer;
import app.service.CustomerService;

import java.util.List;


public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    public Customer save(boolean isActive, String name) {//Сохранить покупателя в базе данных (при сохранении покупатель автоматически считается
        Customer customer = new Customer(isActive, name);
        return customerService.save(customer);
    }

    public List<Customer> getAllActiveCustomers() {//Вернуть всех покупателей из базы данных (активных)
        return customerService.getAllActiveCustomers();
    }

    public Customer getById(Long id) {// Вернуть одного покупателя из базы данных по его идентификатору (если он активен)
        return customerService.getById(id);
    }

    public void updateById(Long id) {//Изменить одного покупателя в базе данных по его идентификатору
        Customer customer = new Customer(id);
        customerService.updateById(customer);
    }

    public void deleteCustomerById(Long id) {//Удалить покупателя из базы данных по его идентификатору
        customerService.deleteCustomerById(id);
    }

    public void deleteCustomerByName(String name) {//Удалить покупателя из базы данных по его имени.
        customerService.deleteCustomerByName(name);
    }

    public void restoreById(Long id) {
        customerService.restoreById(id);
    }

    public long getAllActiveCustomersTotalCount() {//Вернуть общее количество покупателей в базе данных (активных).
        return customerService.getAllActiveCustomersTotalCount();
    }

    public double getAllProductsInBasket(Long id) {//Вернуть стоимость корзины покупателя по его идентификатору (если он активен)
        return customerService.getAllProductsInBasket(id);
    }

    public double getAverageCostProductsInBasket(Long id) {// Вернуть среднюю стоимость продукта в корзине покупателя по его идентификатору (если он
        return customerService.getAverageCostProductsInBasket(id);
    }

    public void addActiveProductInBasket(Long id, Long id1) {//Добавить товар в корзину покупателя по их идентификаторам (если оба активны)
        customerService.addActiveProductInBasket(id, id1);
    }

    public void deleteProductById(Long id, Long id2) {//Удалить товар из корзины покупателя по их идентификаторам
        customerService.deleteProductById(id, id2);
    }

    public void clearProductBasket(Long id) {//Полностью очистить корзину покупателя по его идентификатору (если он активен)
        customerService.clearProductBasket(id);
    }
}

}
