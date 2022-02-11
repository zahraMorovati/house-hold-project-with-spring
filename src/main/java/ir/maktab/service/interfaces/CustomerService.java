package ir.maktab.service.interfaces;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.enums.UserState;

import java.util.List;

public interface CustomerService {

    void save(Customer customer);

    void delete(String email);

    void update(Customer customer);

    Iterable<Customer> findAll(int page, int size);

    void changePassword(String email, String newPassword);

    Customer findById(int id);

    List<CustomerDto> getAllCustomers();

    Customer findByEmail(String email);

    Customer findByEmailAndPassword(String email, String password);

    List<CustomerDto> filterNotConfirmedCustomers(String name, String family, String email);

    List<CustomerDto> advancedFilterCustomers(String name, String family, String email,
                                              String startingRegistrationDate,
                                              String endingRegistrationDate,
                                              Integer minOrderNumber,Integer maxOrderNumber);

    void confirmCustomer(String email);

    List<CustomerDto> getAllNotConfirmedCustomers();

    void updateCustomerState(UserState userState,String email);





}
