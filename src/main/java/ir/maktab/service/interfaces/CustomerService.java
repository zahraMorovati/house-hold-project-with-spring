package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Customer;

public interface CustomerService {

    void save(Customer customer);

    void delete(Customer customer);

    void update(Customer customer);

    Iterable<Customer> findAll();

    void changePassword(String email,String newPassword);

    Customer findById(int id);


}
