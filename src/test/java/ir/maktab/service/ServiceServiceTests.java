package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.model.entity.Service;
import ir.maktab.data.model.enums.HomeServiceTypes;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@DataJpaTest
public class ServiceServiceTests {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    ServiceServiceImpl serviceService = (ServiceServiceImpl) context.getBean(ServiceServiceImpl.class);
    private Service service;

    @Test
    public void save_Service(){
        service = Service.builder()
                .setServiceName(HomeServiceTypes.CLEANING.name())
                .build();
        serviceService.save(service);
        Assertions.assertThat(service.getId()).isGreaterThan(0);

    }
}
