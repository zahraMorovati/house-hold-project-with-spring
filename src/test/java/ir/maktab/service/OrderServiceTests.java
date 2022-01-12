package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.model.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class OrderServiceTests {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    OrderServiceImpl orderService = (OrderServiceImpl) context.getBean(OrderServiceImpl.class);
    CustomerServiceImpl customerService = (CustomerServiceImpl) context.getBean(CustomerServiceImpl.class);
    SubServiceServiceImpl subServiceService = (SubServiceServiceImpl) context.getBean(SubServiceServiceImpl.class);
    SpecialistServiceImpl specialistService=(SpecialistServiceImpl) context.getBean(SpecialistServiceImpl.class);
    SuggestionServiceImpl suggestionService=(SuggestionServiceImpl) context.getBean(SuggestionServiceImpl.class);

    @Test
    public void add_customer_order(){

        Customer customer = customerService.findById(1);
        SubService subService = subServiceService.findById(1);
        Address address = Address.builder().setCity("tehran").setCityState("tehran").setExplanations("--").build();
        orderService.addCustomerOrder(customer,subService,1000,"--",address,new Date());
    }

    @Test
    public void add_specialist_suggestion(){

        Order order = orderService.findById(1);
        Specialist specialist = specialistService.findById(1);
        orderService.addSpecialistSuggestion(order,specialist,100,2,new Date());
    }

    @Test
    public void get_suggestions_list(){
        Order order = orderService.findById(1);
        List<Suggestion> list = orderService.getOrderSuggestions(order);
        Assertions.assertThat(list.size()).isGreaterThan(0);
    }

    @Test
    public void select_Specialist(){
        Order order = orderService.findById(1);
        Suggestion suggestion = suggestionService.findById(1);
        orderService.selectSpecialistSuggestion(order,suggestion);
    }


}
