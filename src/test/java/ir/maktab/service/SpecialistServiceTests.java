package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.enums.UserState;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@DataJpaTest
public class SpecialistServiceTests {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    SpecialistServiceImpl specialistService=(SpecialistServiceImpl) context.getBean(SpecialistServiceImpl.class);

    @Test
    public void save_Specialist(){
        Specialist specialist = Specialist.SpecialistBuilder.aSpecialist()
                .setName("mohamad")
                .setFamily("mohamadi")
                .setEmail("mohamad9@gmail.com")
                .setPassword("1234")
                .setBalance(4855900)
                .setState(UserState.NEW_USER).build();
        specialistService.save(specialist);
        Assertions.assertThat(specialist.getId()).isGreaterThan(0);
    }

    @Test
    public void change_specialist_image_throw_IOException(){
        Specialist specialist = specialistService.findById(1);

    }
}
