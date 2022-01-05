package config;

import dao.OrderDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public OrderDaoImpl orderDao(){
        return new OrderDaoImpl();
    }
}
