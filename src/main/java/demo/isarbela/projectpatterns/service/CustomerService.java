package demo.isarbela.projectpatterns.service;

import demo.isarbela.projectpatterns.model.Customer;

/**
 *  Defines the Strategy pattern, by using this interface it's possible to create multiple implementations.
 *
 * @author isarbela
 */
public interface CustomerService {

    Iterable<Customer> findAll();

    Customer findById(Long id);

    void insert(Customer customer);

    void update(Long id, Customer customer);

    void delete(Long id);

}
