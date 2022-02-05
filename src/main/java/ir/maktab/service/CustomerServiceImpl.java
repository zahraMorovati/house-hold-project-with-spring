package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.mappers.CustomerMapper;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Specialist;
import ir.maktab.exception.UserEceptions.WrongEmailException;
import ir.maktab.exception.customerExceptions.BalanceIsNotEnoughException;
import ir.maktab.exception.customerExceptions.CannotSaveCustomerException;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import static ir.maktab.util.Convert.parsDate;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final SpecialistDao specialistDao;


    @Override
    public void save(Customer customer) {
        customerDao.save(customer);
        if (customer.getId() < 0)
            throw new CannotSaveCustomerException();
    }

    @Override
    public void update(Customer customer) {
        customerDao.save(customer);
    }

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
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = StreamSupport
                .stream(customerDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return customers.stream()
                .map(CustomerMapper::toCustomerDto).collect(Collectors.toList());
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
        List<Customer> result = customerDao.findCustomerByEmailAndPassword(email, password);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new CustomerNotFoundException();
    }

    @Override
    public List<CustomerDto> filterCustomers(String name, String family, String email) {

        Specification<Customer> specification = CustomerDao.filterCustomers(name, family, email);
        return customerDao.findAll(specification).stream()
                .map(CustomerMapper::toCustomerDto).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> advancedFilterCustomers(String name, String family, String email, String startingRegistrationDate, String endingRegistrationDate, Integer minOrderNumber, Integer maxOrderNumber) {

        Date startingDate = parsDate(startingRegistrationDate);
        Date endingDate = parsDate(startingRegistrationDate);
        int minNumber = 0;
        int maxNumber = 0;
        if (minOrderNumber != null) minNumber = minOrderNumber;
        if (maxOrderNumber != null) maxNumber = maxOrderNumber;
        Specification<Customer> specification = CustomerDao.advancedFilter(name,family,email,startingDate,endingDate,minNumber,maxNumber);
        return customerDao.findAll(specification).stream()
                .map(CustomerMapper::toCustomerDto).collect(Collectors.toList());
    }




}
