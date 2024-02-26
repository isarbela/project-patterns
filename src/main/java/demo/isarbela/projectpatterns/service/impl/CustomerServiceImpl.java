package demo.isarbela.projectpatterns.service.impl;

import demo.isarbela.projectpatterns.model.Address;
import demo.isarbela.projectpatterns.model.AddressRepository;
import demo.isarbela.projectpatterns.model.Customer;
import demo.isarbela.projectpatterns.model.CustomerRepository;
import demo.isarbela.projectpatterns.service.CustomerService;
import demo.isarbela.projectpatterns.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    // Singleton: Inject Spring components using ths notation (DI)
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ViaCepService viaCepService;

    // Strategy: Implement methods defined in the interface
    // Facade: abstract subsystem integration, providing a simple interface

    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void insert(Customer customer) {
        saveCustomerWithCep(customer);
    }

    @Override
    public void update(Long id, Customer customer) {
        Optional<Customer> customerBd = customerRepository.findById(id);
        if (customerBd.isPresent()) {
            saveCustomerWithCep(customer);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    private void saveCustomerWithCep(Customer customer) {
        String cep = customer.getAddress().getCep();
        Address address = addressRepository.findById(cep).orElseGet(() -> {
            Address newAddress = viaCepService.searchCep(cep);
            addressRepository.save(newAddress);
            return newAddress;
        });

        customer.setAddress(address);
        customerRepository.save(customer);
    }
}
