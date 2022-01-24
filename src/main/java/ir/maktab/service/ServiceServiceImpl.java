package ir.maktab.service;

import ir.maktab.data.dao.interfaces.ServiceDao;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.mappers.ServiceMapper;
import ir.maktab.data.entity.Service;
import ir.maktab.exception.serviceExceptions.DuplicatedServiceException;
import ir.maktab.exception.serviceExceptions.ServiceNotFoundException;
import ir.maktab.service.interfaces.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    ServiceDao serviceDao;

    @Autowired
    public ServiceServiceImpl(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public void save(ServiceDto serviceDto) {

        int result = serviceDao.findByServiceName(serviceDto.getName()).size();

        if (result <= 0) {
            Service service = ServiceMapper.toService(serviceDto);
            serviceDao.save(service);
        } else throw new DuplicatedServiceException();
    }

    @Override
    public void update(Service service) {
        serviceDao.update(service.getServiceName(), service.getId());
    }

    @Override
    public Iterable<Service> findAll(int page, int size) {
        return serviceDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public Service findById(int id) {
        Optional<Service> optionalService = serviceDao.findById(id);
        if (optionalService.isPresent())
            return optionalService.get();
        else throw new ServiceNotFoundException();
    }

    @Override
    public Service findByName(String name) {
        List<Service> result = serviceDao.findByServiceName(name);
        if(result.isEmpty())
            throw new ServiceNotFoundException();
        else return result.get(0);
    }

    @Override
    public List<ServiceDto> getServiceNames() {
        List<Service> services = StreamSupport
                .stream(serviceDao.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return services.stream().map(ServiceMapper::toServiceDto).collect(Collectors.toList());

    }

    @Override
    public void delete(Service service) {
        serviceDao.delete(service);
    }


}
