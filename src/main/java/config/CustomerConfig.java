package config;

import dao.CustomerDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    @Bean
    public CustomerDaoImpl customerDao(){
        return new CustomerDaoImpl();
    }


}
