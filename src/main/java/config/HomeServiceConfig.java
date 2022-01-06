package config;


import dao.HomeServiceDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import service.HomeServiceService;
@Import(value = {SpecialistConfig.class})
@Configuration
public class HomeServiceConfig {

    @Bean
    public HomeServiceDaoImpl HomeServiceDao(){
        return new HomeServiceDaoImpl();
    }

    @Bean
    public HomeServiceService homeServiceService(HomeServiceDaoImpl homeServiceDao){
        HomeServiceService homeServiceService = new HomeServiceService();
        homeServiceService.setHomeServiceDao(homeServiceDao);
        return homeServiceService;
    }
}
