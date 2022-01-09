package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.model.entity.Customer;
import ir.maktab.exception.UserEceptions.WrongEmailException;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void save(Customer customer){
        customerDao.save(customer);
    }

    @Override
    public void update(Customer customer){
        customerDao.update(customer.getName(),customer.getFamily(),customer.getEmail(),customer.getPassword(),customer.getBalance(),customer.getId());
    }

    @Override
    public Iterable<Customer> findAll(int page , int size) {
        return customerDao.findAll(PageRequest.of(page,size));
    }

    @Override
    public void delete(Customer customer){
        customerDao.delete(customer);
    }

    @Override
    public void changePassword(String email,String newPassword){
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if(!result.isEmpty()){
            customerDao.updatePasswordByEmail(newPassword,email);
        }else throw new WrongEmailException();
    }

    @Override
    public Customer findById(int id) {
        Optional<Customer> optionalCustomer = customerDao.findById(id);
        if(optionalCustomer.isPresent())
            return optionalCustomer.get();
        else throw new CustomerNotFoundException();
    }

    @Override
    public List<Customer> filterByNameOrFamilyOrEmail(String name, String family, String email) {
        List<Customer> customers = customerDao.findCustomerByNameOrFamilyOrEmail(name, family, email);
        if(!customers.isEmpty())
            return customers;
        else throw new CustomerNotFoundException();
    }


}
