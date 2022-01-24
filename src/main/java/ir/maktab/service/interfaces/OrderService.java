package ir.maktab.service.interfaces;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.entity.*;

import java.util.Date;
import java.util.List;


public interface OrderService {

    void save(Order order);

    void delete(Order order);

    void update(Order order);

    Iterable<Order> findAll(int page, int size);

    Order findById(int id);

    Order findByOrderCode(String orderCode);

    void addCustomerOrder(Customer customer, SubService subService, double suggestedPrice, String explanations, Address address, Date startDate);

    void selectSpecialistSuggestion(Order order , Suggestion suggestion);

    List<Suggestion> getOrderSuggestions(Order order);

    List<OrderDto> getCustomerOrders(String email);

    List<OrderDto> getSpecialistOrders(String email);

}
