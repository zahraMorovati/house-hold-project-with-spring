package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.model.entity.Specialist;
import ir.maktab.data.model.entity.SubService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@DataJpaTest
public class SubServiceServiceTests {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    SubServiceServiceImpl subServiceService = (SubServiceServiceImpl) context.getBean(SubServiceServiceImpl.class);
    ServiceServiceImpl serviceService = (ServiceServiceImpl) context.getBean(ServiceServiceImpl.class);
    SpecialistServiceImpl specialistService = (SpecialistServiceImpl) context.getBean(SpecialistServiceImpl.class);

    SubService subService;

    @Test
    public void save_subService() {
        subService = SubService.builder()
                .setService(serviceService.findById(1))
                .setSubServiceName("house_clean")
                .setExplanations("--")
                .setPrice(14880)
                .build();
        subServiceService.save(subService);
        Assertions.assertThat(subService.getId()).isGreaterThan(0);
    }

    @Test
    public void add_Specialist() {

        Specialist specialist = specialistService.findById(1);
        SubService subService = subServiceService.findById(1);
        subServiceService.addSpecialistToSubService(subService, specialist);
        subService = subServiceService.findById(1);
        Assertions.assertThat(subService.getSpecialists().size()).isGreaterThan(0);
    }

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
