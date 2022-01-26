package ir.maktab.service;


import ir.maktab.data.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

@DataJpaTest
public class OrderServiceTests {


     OrderServiceImpl orderService ;
     CustomerServiceImpl customerService ;
     SubServiceServiceImpl subServiceService ;
     SpecialistServiceImpl specialistService;
     SuggestionServiceImpl suggestionService;

     @Autowired
    public OrderServiceTests(OrderServiceImpl orderService, CustomerServiceImpl customerService, SubServiceServiceImpl subServiceService, SpecialistServiceImpl specialistService, SuggestionServiceImpl suggestionService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.subServiceService = subServiceService;
        this.specialistService = specialistService;
        this.suggestionService = suggestionService;
    }

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
        suggestionService.addSpecialistSuggestion(order,specialist,200,3,new Date());
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
