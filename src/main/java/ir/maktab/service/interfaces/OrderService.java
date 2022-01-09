package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Order;


public interface OrderService {

    void save(Order order);

    void delete(Order order);

    void update(Order order);

    Iterable<Order> findAll(int page , int size);

    Order findById(int id);
}
