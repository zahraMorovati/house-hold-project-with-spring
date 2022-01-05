package config;

import dao.OrderDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.OrderService;

@Configuration
public class OrderConfig {

    @Bean
    public OrderDaoImpl orderDao(){
        return new OrderDaoImpl();
    }

    @Bean
    public OrderService orderService(OrderDaoImpl orderDao){
        OrderService orderService = new OrderService();
        orderService.setOrderDao(orderDao);
        return orderService;
    }
}
