package ir.maktab.service.interfaces;

import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.entity.Service;

import java.util.List;

public interface ServiceService {

    void save(ServiceDto serviceDto);

    void delete(Service service);

    void update(Service service);

    Iterable<Service> findAll(int page , int size);

    Service findById(int id);

    Service findByName(String name);

    List<ServiceDto> getServiceNames();
}
