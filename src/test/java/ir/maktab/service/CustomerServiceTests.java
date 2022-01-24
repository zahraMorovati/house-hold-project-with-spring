package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.entity.Customer;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomerServiceTests {

    CustomerServiceImpl customerService ;

    @Autowired
    public CustomerServiceTests(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }
    private Customer customer;

    @Test
    public void save_Customer(){
        customer = Customer.CustomerBuilder.aCustomer()
                .setName("fateme")
                .setFamily("morovati")
                .setBalance(14000)
                .setEmail("fateme@gmai.com")
                .setPassword("1234").build();
        customerService.save(customer);
        Assertions.assertThat(customer.getId()).isGreaterThan(0);

    }

}
