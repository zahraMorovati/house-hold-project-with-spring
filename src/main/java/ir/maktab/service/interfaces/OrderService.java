package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.*;

import java.util.Date;
import java.util.List;


public interface OrderService {

    void save(Order order);

    void delete(Order order);

    void update(Order order);

    Iterable<Order> findAll(int page, int size);

    Order findById(int id);

    void addCustomerOrder(Customer customer, SubService subService, double suggestedPrice, String explanations, Address address, Date startDate);

    void addSpecialistSuggestion(Order order ,Specialist specialist, double suggestedPrice , double workHour , Date startTime );

    void selectSpecialistSuggestion(Order order , Suggestion suggestion);

    List<Suggestion> getOrderSuggestions(Order order);

}
