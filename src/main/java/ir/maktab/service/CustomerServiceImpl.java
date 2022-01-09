package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.model.entity.Customer;
import ir.maktab.service.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Iterable<Customer> findAll() {
        return null;
    }

    @Override
    public void delete(Customer customer){
        customerDao.delete(customer);
    }

    public Customer findCustomerById(int id){
        List<Customer> customerList = customerDao.findCustomerById(id);
        if(customerList.isEmpty())
            throw new RuntimeException("cannot find customer by id!");
        else return customerList.get(0);
    }

    @Override
    public void changePassword(String email,String newPassword){
        List<Customer> result = customerDao.findCustomerByEmail(email);
        if(!result.isEmpty()){
            customerDao.updateCustomerPasswordByEmail(newPassword,email);
        }else throw new RuntimeException("wrong email!");
    }

    @Override
    public Customer findById(int id) {
        return customerDao.findById(id).orElse(null);
    }


}
