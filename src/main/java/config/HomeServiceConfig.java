package config;


import dao.HomeServiceDaoImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HomeServiceConfig {

    public HomeServiceDaoImpl serviceDao(){
        return new HomeServiceDaoImpl();
    }
}
