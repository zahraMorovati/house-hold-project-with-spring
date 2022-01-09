package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Service;

public interface ServiceService {

    void save(Service homeService);

    void delete(Service homeService);

    void update(Service homeService);

    Iterable<Service> findAll();

    Service findById(int id);
}
