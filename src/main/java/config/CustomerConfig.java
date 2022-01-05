package config;

import dao.CustomerDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.CustomerService;

@Configuration
public class CustomerConfig {

    @Bean
    public CustomerDaoImpl customerDao(){
        return new CustomerDaoImpl();
    }

    @Bean
    public CustomerService customerService(CustomerDaoImpl customerDao){
        CustomerService customerService = new CustomerService();
        customerService.setCustomerDao(customerDao);
        return customerService;
    }


}
