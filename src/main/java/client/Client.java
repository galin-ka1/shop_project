package client;

import app.controller.CustomerController;
import app.controller.ProductController;
import app.domain.Customer;
import app.domain.Product;
import app.repositories.CustomerRepository;
import app.repositories.CustomerRepositoryMap;
import app.repositories.ProductRepository;
import app.repositories.ProductRepositoryMap;
import app.service.CustomerService;
import app.service.CustomerServiceImpl;
import app.service.ProductService;
import app.service.ProductServiceImpl;

import java.util.Scanner;

public class Client {

    private static Scanner scanner;
    private static ProductController productController;
    private static CustomerController customerController;

    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepositoryMap();
        CustomerRepository customerRepository = new CustomerRepositoryMap();

        ProductService productService = new ProductServiceImpl(productRepository);
        CustomerService customerService = new CustomerServiceImpl(customerRepository);

        productController = new ProductController(productService);
        customerController = new CustomerController(customerService);
        scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Выберите действие: ");
                System.out.println("1. Операции с продуктами");
                System.out.println("2. Операции с покупателями");
                System.out.println("0. Выход");
                System.out.println("Ваш выбор: ");

                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        productOperations();
                        break;
                    case 2:
                        customerOperations();
                        break;
                    case 0:
                        return;
                    default:
                        System.err.println("Некорректный ввод");
                        break;
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void productOperations() {
        while (true) {
            try {
                System.out.println("Выберете действие с продуктом: ");
                System.out.println("1. Сохранение продукта");
                System.out.println("2. Получение всех активных продуктов");
                System.out.println("3. Получение одного продукта по ID");
                System.out.println("4. Изменение одного продукта");
                System.out.println("5. Удаление одного продукта по ID");
                System.out.println("6. Удаление одного продукта по наименованию");
                System.out.println("7. Восстановление одного продукта по ID");
                System.out.println("8. Получение количества продуктов");
                System.out.println("9. Получение общей стоимости продуктов");
                System.out.println("10. Получение средней стоимости продуктов");
                System.out.println("0. Выход");
                System.out.println("Ваш выбор: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Введите наименование продукта: ");
                        String name = scanner.nextLine();
                        System.out.println("Введите цену продукта");
                        double price = Double.parseDouble(scanner.nextLine());

                        Product product = productController.save(name, price);
                        System.out.println("Сохраненный продукт: ");
                        System.out.println(product);
                        break;
                    case 2:
                        productController.getAll().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.println("Введите ID продукта: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        Product foundProduct = productController.getById(id);
                        System.out.println("Найденный продукт: ");
                        System.out.println(foundProduct);
                        break;
                    case 4:
                        System.out.println("Введите ID продукта: ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("Введите наименование продукта: ");
                        name = scanner.nextLine();
                        System.out.println("Введите цену продукта");
                        price = Double.parseDouble(scanner.nextLine());

                        productController.update(id, name, price);
                        break;
                    case 5:
                        System.out.println("Введите ID продукта: ");
                        id = Long.parseLong(scanner.nextLine());
                        productController.deleteById(id);
                        break;
                    case 6:
                        System.out.println("Введите наименование продукта: ");
                        name = scanner.nextLine();
                        productController.deleteByName(name);
                        break;
                    case 7:
                        System.out.println("Введите ID продукта: ");
                        id = Long.parseLong(scanner.nextLine());
                        productController.restoreById(id);
                        break;
                    case 8:
                        System.out.println("Общее количество продуктов: "
                                + productController.getActiveProductsTotalCount());
                        break;
                    case 9:
                        System.out.println("Общая стоимость продуктов: "
                                + productController.getActiveProductsTotalCost());
                        break;
                    case 10:
                        System.out.println("Средняя цена продуктов: "
                                + productController.getActiveProductsAveragePrice());
                        break;
                    case 0:
                        return;
                    default:
                        System.err.println("Некорректный ввод");
                        break;
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void customerOperations() {
        while (true) {
            try {
                System.out.println("Выберете действие с клиентом и его корзиной: ");
                System.out.println("1. Сохранить покупателя в базе данных");
                System.out.println("2. Вернуть всех покупателей из базы данных (активных)");
                System.out.println("3. Вернуть одного покупателя из базы данных по его ID");
                System.out.println("4. Изменить одного покупателя в базе данных по его ID");
                System.out.println("5. Удалить покупателя из базы данных по его ID");
                System.out.println("6. Удалить покупателя из базы данных по его имени");
                System.out.println("7. Восстановить удалённого покупателя в базе данных по его ID");
                System.out.println("8. Вернуть общее количество покупателей в базе данных (активных");
                System.out.println("9. Вернуть стоимость корзины покупателя по его ID (если он активен)");
                System.out.println("10. Вернуть среднюю стоимость продукта в корзине покупателя по его ID");
                System.out.println("11. Добавить товар в корзину покупателя по их идентификаторам");
                System.out.println("12. Удалить товар из корзины покупателя по их идентификаторам");
                System.out.println("13. Полностью очистить корзину покупателя по его идентификатору");
                System.out.println("0. Выход");
                System.out.println("Ваш выбор: ");

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Активный ли клиент");
                        Boolean isActive = scanner.nextBoolean();
                        System.out.println("Введите имя клиента: ");
                        String name = scanner.nextLine();

                        Customer customer = customerController.save(isActive, name);
                        System.out.println("Сохраненный покупатель: ");
                        System.out.println(customer);
                        break;
                    case 2:
                        customerController.getAllActiveCustomers().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.println("Введите ID клиента: ");
                        Long id = Long.parseLong(scanner.nextLine());
                        Customer foundCustomer = customerController.getById(id);
                        System.out.println("Найденный клиент: ");
                        System.out.println(foundCustomer);
                        break;
                    case 4:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("Введите имя клиента: ");
                        name = scanner.nextLine();

                        customerController.updateById(id);
                        break;
                    case 5:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        customerController.deleteCustomerById(id);
                        break;
                    case 6:
                        System.out.println("Введите имя клиента: ");
                        name = scanner.nextLine();
                        customerController.deleteCustomerByName(name);
                        break;
                    case 7:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        customerController.restoreById(id);
                        break;
                    case 8:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("Общее количество клиентов: "
                                + customerController.getAllActiveCustomersTotalCount());
                        break;
                    case 9:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("Общая стоимость продуктов в корзине: "
                                + customerController.getAllProductsInBasket(id));
                        break;
                    case 10:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());

                        System.out.println("Средняя цена продуктов в корзине: "
                                + customerController.getAverageCostProductsInBasket(id));
                        break;
                    case 11:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("Введите ID продукта: ");
                        Long id1 = Long.parseLong(scanner.nextLine());
                        customerController.addActiveProductInBasket(id, id1);
                        break;
                    case 12:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        System.out.println("Введите ID продукта: ");
                        Long id2 = Long.parseLong(scanner.nextLine());
                        customerController.deleteProductById(id, id2);
                        break;
                    case 13:
                        System.out.println("Введите ID клиента: ");
                        id = Long.parseLong(scanner.nextLine());
                        customerController.clearProductBasket(id);
                        break;
                    case 0:
                        return;
                    default:
                        System.err.println("Некорректный ввод");
                        break;
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

