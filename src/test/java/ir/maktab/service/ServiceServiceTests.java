package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.entity.Service;
import ir.maktab.data.enums.HomeServiceTypes;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@DataJpaTest
public class ServiceServiceTests {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    ServiceServiceImpl serviceService = (ServiceServiceImpl) context.getBean(ServiceServiceImpl.class);


    @Test
    public void save_Service(){
        ServiceDto service = ServiceDto.builder()
                .setName(HomeServiceTypes.CLEANING.name())
                .build();
        serviceService.save(service);
        Service saved = serviceService.findByName(service.getName());
        Assertions.assertThat(saved.getId()).isGreaterThan(0);

    }
}
