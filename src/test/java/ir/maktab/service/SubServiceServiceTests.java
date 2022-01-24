package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.dto.mappers.SubServiceMapper;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.SubService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.annotation.Order;

@DataJpaTest
public class SubServiceServiceTests {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    SubServiceServiceImpl subServiceService = (SubServiceServiceImpl) context.getBean(SubServiceServiceImpl.class);
    ServiceServiceImpl serviceService = (ServiceServiceImpl) context.getBean(ServiceServiceImpl.class);
    SpecialistServiceImpl specialistService = (SpecialistServiceImpl) context.getBean(SpecialistServiceImpl.class);

    SubService subService;

    @Order(value = 1)
    @Test
    public void save_subService() {
        subService = SubService.builder()
                .setService(serviceService.findById(1))
                .setSubServiceName("office_clean")
                .setExplanations("--")
                .setPrice(14880)
                .build();
        subServiceService.save(SubServiceMapper.toSubServiceDto(subService));
        Assertions.assertThat(subService.getId()).isGreaterThan(0);
    }

    @Order(value = 2)
    @Test
    public void add_Specialist() {

        Specialist specialist = specialistService.findById(1);
        SubService subService = subServiceService.findById(1);
        subServiceService.addSpecialistToSubService(subService, specialist);
        subService = subServiceService.findById(1);
        Assertions.assertThat(subService.getSpecialists().size()).isGreaterThan(0);
    }

    @Order(value = 3)
    @Test
    public void remove_Specialist() {
        Specialist specialist = specialistService.findById(1);
        SubService subService = subServiceService.findById(1);
        int subServiceSpecialistsSize = subService.getSpecialists().size();
        subServiceService.removeSpecialistFromSubService(subService, specialist);
        subService = subServiceService.findById(1);
        Assertions.assertThat(subService.getSpecialists().size()).isLessThan(subServiceSpecialistsSize);
    }
}
