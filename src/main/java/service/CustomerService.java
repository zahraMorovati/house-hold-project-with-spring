package service;

import dao.CustomerDaoImpl;
import dto.UserDto;
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

    public void changePassword(String email,String newPass){
        List<Customer> result = customerDao.getCustomerByEmail(email);
        if(!result.isEmpty()){
            Customer customer = result.get(0);
            customer.setPassword(newPass);
            customerDao.update(customer);
        }else throw new RuntimeException("wrong email!");
    }

    public List<UserDto> filter (String name , String family ,String email){
        //pagination is not handled that's why i put zero for max result
        List<UserDto> list = customerDao.filter(name, family, email, 0, 0);
        if(list.isEmpty())
            throw new RuntimeException("cannot find any customer!");
        return list;
    }


}
