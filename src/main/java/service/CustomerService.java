package service;

import dao.CustomerDaoImpl;
import model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    CustomerDaoImpl customerDao;

    public CustomerDaoImpl getCustomerDao() {
        return customerDao;
    }

    @Autowired
    public void setCustomerDao(@Qualifier("customerDao") CustomerDaoImpl customerDao) {
        this.customerDao = customerDao;
    }

    public void save(Customer customer){
        customerDao.save(customer);
    }

    public void update(Customer customer){
        customerDao.update(customer);
    }

    public void delete(Customer customer){
        customerDao.delete(customer);
    }

    public Customer findCustomerById(int id){
        List<Customer> customerList = customerDao.getCustomerById(id);
        if(customerList.isEmpty())
            throw new RuntimeException("cannot find customer by id!");
        else return customerList.get(0);
    }

    public Customer findCustomerByEmailAndPassword(String email , String password){
        List<Customer> customerList = customerDao.getCustomerByEmailAndPassword(email, password);
        if(customerList.isEmpty())
            throw new RuntimeException("wrong email or password!");
        else return customerList.get(0);
    }

}
