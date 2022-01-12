package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Service;

public interface ServiceService {

    void save(Service service);

    void delete(Service service);

    void update(Service service);

    Iterable<Service> findAll(int page , int size);

    Service findById(int id);
}
