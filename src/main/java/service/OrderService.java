package service;

import dao.OrderDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    OrderDaoImpl orderDao;

    public OrderDaoImpl getOrderDao() {
        return orderDao;
    }

    @Autowired
    public void setOrderDao(@Qualifier("orderDao") OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }


}
