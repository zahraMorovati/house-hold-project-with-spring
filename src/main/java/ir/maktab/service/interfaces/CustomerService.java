package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    void save(Customer customer);

    void delete(Customer customer);

    void update(Customer customer);

    Iterable<Customer> findAll(int page , int size);

    void changePassword(String email,String newPassword);

    Customer findById(int id);

    List<Customer> filterByNameOrFamilyOrEmail(String name,String family,String email);


}
