package config;

import dao.ManagerDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.ManagerService;

@Configuration
public class ManagerConfig {

    @Bean
    public ManagerDaoImpl managerDao(){
        return new ManagerDaoImpl();
    }

    @Bean
    public ManagerService managerService(ManagerDaoImpl managerDao){
        ManagerService managerService = new ManagerService();
        managerService.setManagerDao(managerDao);
        return managerService;
    }
}
