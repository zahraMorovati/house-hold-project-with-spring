package dao.interfaces;

import model.entity.Manager;
import model.entity.Order;

import java.util.List;

public interface OrderDao {
    void save(Order order);
    void delete(Order order);
    void update(Order order);
    List<Manager> getAllManagers();
    List<Manager> getManagerById();
}
