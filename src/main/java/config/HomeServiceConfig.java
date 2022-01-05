package config;


import dao.HomeServiceDaoImpl;
import org.springframework.context.annotation.Configuration;
import service.HomeServiceService;

@Configuration
public class HomeServiceConfig {

    public HomeServiceDaoImpl HomeServiceDao(){
        return new HomeServiceDaoImpl();
    }

    public HomeServiceService homeServiceService(HomeServiceDaoImpl homeServiceDao){
        HomeServiceService homeServiceService = new HomeServiceService();
        homeServiceService.setHomeServiceDao(homeServiceDao);
        return homeServiceService;
    }
}
