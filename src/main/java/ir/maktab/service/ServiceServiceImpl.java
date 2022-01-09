package ir.maktab.service;

import ir.maktab.data.dao.interfaces.ServiceDao;
import ir.maktab.data.model.entity.Service;
import ir.maktab.exception.serviceExceptions.ServiceNotFoundException;
import ir.maktab.service.interfaces.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import java.util.Optional;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    protected ServiceDao serviceDao;

    @Autowired
    public ServiceServiceImpl(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    public void save(Service homeService){
        serviceDao.save(homeService);
    }

    @Override
    public void update(Service service){
        serviceDao.update(service.getServiceName(),service.getPrice(),service.getId());
    }

    @Override
    public Iterable<Service> findAll(int page , int size) {
        return serviceDao.findAll(PageRequest.of(page,size));
    }

    @Override
    public Service findById(int id) {
        Optional<Service> optionalService = serviceDao.findById(id);
        if(optionalService.isPresent())
            return optionalService.get();
        else throw new ServiceNotFoundException();
    }

    @Override
    public void delete(Service homeService){
        serviceDao.delete(homeService);
    }





}
