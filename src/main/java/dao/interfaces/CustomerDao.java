package dao.interfaces;

import model.entity.Customer;
import model.entity.Order;

import java.util.List;

public interface CustomerDao {

    void save(Customer customer);

    void delete(Customer customer);

    void update(Customer customer);

    List<Customer> getCustomerById(int id);

    List<Customer> getAllCustomers();

    void addOrder(Customer customer, Order order);


}
