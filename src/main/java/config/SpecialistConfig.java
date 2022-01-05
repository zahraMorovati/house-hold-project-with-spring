package config;

import dao.SpecialistDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecialistConfig {

    @Bean
    public SpecialistDaoImpl specialistDao(){
        return new SpecialistDaoImpl();
    }
}
