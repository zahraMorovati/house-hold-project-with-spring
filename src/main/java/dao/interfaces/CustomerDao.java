package dao.interfaces;

import dto.UserDto;
import model.entity.Customer;
import model.entity.Order;

import java.util.List;

public interface CustomerDao {

    void save(Customer customer);

    void delete(Customer customer);

    void update(Customer customer);

    List<Customer> getCustomerById(int id);

    List<Customer> getCustomerByEmailAndPassword(String email , String password);

    List<Customer> getAllCustomers();

    void addOrder(Customer customer, Order order);

    List<UserDto> filter(String name,String family,String email,int maxResult,int firstResult);


}
