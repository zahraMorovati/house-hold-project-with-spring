package ir.maktab.service;

import ir.maktab.data.dao.interfaces.ServiceDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.model.entity.Service;
import ir.maktab.service.interfaces.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    public Iterable<Service> findAll() {
        return serviceDao.findAll();
    }

    @Override
    public Service findById(int id) {
        return serviceDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Service homeService){
        serviceDao.delete(homeService);
    }





}
