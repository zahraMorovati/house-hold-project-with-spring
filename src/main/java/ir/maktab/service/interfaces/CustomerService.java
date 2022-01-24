package ir.maktab.service.interfaces;

import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Customer;

import java.util.List;

public interface CustomerService {

    void save(Customer customer);

    void delete(Customer customer);

    void update(Customer customer);

    Iterable<Customer> findAll(int page, int size);

    void changePassword(String email, String newPassword);

    Customer findById(int id); //todo

    List<Customer> filterByNameOrFamilyOrEmail(String name, String family, String email); //todo

    List<UserDto> getAllCustomers();

    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);
}
