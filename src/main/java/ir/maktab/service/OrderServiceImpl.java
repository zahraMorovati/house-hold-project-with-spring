package ir.maktab.service;

import ir.maktab.data.dao.interfaces.OrderDao;
import ir.maktab.data.model.entity.Order;
import ir.maktab.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public void delete(Order order) {
        orderDao.save(order);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order.getSubService(),order.getSuggestedPrice(),order.getExplanations(),order.getStartDate(),order.getAddress(),order.getId());
    }

    @Override
    public Iterable<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(int id) {
        return orderDao.findById(id).orElse(null);
    }
}
