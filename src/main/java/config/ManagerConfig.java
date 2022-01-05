package config;

import dao.ManagerDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManagerConfig {

    @Bean
    public ManagerDaoImpl managerDao(){
        return new ManagerDaoImpl();
    }
}
