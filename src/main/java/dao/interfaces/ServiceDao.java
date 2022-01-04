package dao.interfaces;

import model.entity.Service;

import java.util.List;

public interface ServiceDao {
    void save(Service service);

    void delete(Service service);

    void update(Service service);

    List<Service> getServiceById(int id);

    List<Service> getAllServices();
}
