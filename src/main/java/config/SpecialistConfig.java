package config;

import dao.SpecialistDaoImpl;
import dao.interfaces.SpecialistDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.SpecialistService;

@Configuration
public class SpecialistConfig {

    @Bean
    public SpecialistDaoImpl specialistDao(){
        return new SpecialistDaoImpl();
    }

    @Bean
    public SpecialistService specialistService(SpecialistDaoImpl specialistDao){

        SpecialistService specialistService = new SpecialistService();
        specialistService.setSpecialistDao(specialistDao);
        return specialistService;

    }
}
