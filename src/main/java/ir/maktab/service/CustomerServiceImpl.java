package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.dto.mappers.UserMapper;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Specialist;
import ir.maktab.exception.UserEceptions.WrongEmailException;
import ir.maktab.exception.customerExceptions.CannotSaveCustomerException;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    @Override
    public void save(Customer customer) { //todo add mapper class customerMapper - customerDto to customer
        customerDao.save(customer);
        if (customer.getId() < 0)
            throw new CannotSaveCustomerException();
    }

    @Override
    public void update(Customer customer) {
        customerDao.update(customer.getName(), customer.getFamily(), customer.getEmail(), customer.getPassword(), customer.getBalance(), customer.getId());
    }

    //todo return CustomerDto
    @Override
    public Iterable<Customer> findAll(int page, int size) {
        return customerDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
    }

    @Override
    public void changePassword(String email, String newPassword) {
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if (!result.isEmpty()) {
            customerDao.updatePasswordByEmail(newPassword, email);
        } else throw new WrongEmailException();
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> optionalCustomer = customerDao.findById(id);
        if (optionalCustomer.isPresent())
            return optionalCustomer.get();
        else throw new CustomerNotFoundException();
    }

    @Override
    public List<Customer> filterByNameOrFamilyOrEmail(String name, String family, String email) {
        List<Customer> customers = customerDao.findCustomerByNameOrFamilyOrEmail(name, family, email);
        if (!customers.isEmpty())
            return customers;
        else throw new CustomerNotFoundException();
    }

    @Override
    public List<UserDto> getAllCustomers() {
        List<Customer> customers = StreamSupport
                .stream(customerDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return customers.stream()
                .map(i -> UserMapper.toUserDto(i.getName(), i.getFamily(), i.getEmail())).collect(Collectors.toList());
    }

    @Override
    public Customer findByEmail(String email) {
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new CustomerNotFoundException();
    }

    @Override
    public Customer findByEmailAndPassword(String email, String password) {
        List<Customer> result = customerDao.findCustomerByEmailAndPassword(email,password);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new CustomerNotFoundException();
    }


}
