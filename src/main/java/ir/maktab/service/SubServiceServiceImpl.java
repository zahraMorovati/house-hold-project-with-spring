package ir.maktab.service;


import ir.maktab.data.dao.interfaces.SubServiceDao;
import ir.maktab.data.model.entity.SubService;
import ir.maktab.exception.subServiceExceptions.SubServiceNotFoundException;
import ir.maktab.service.interfaces.SubServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubServiceServiceImpl implements SubServiceService {

    SubServiceDao subServiceDao;

    @Autowired
    public SubServiceServiceImpl(SubServiceDao subServiceDao) {
        this.subServiceDao = subServiceDao;
    }

    @Override
    public void save(SubService subService) {
        subServiceDao.save(subService);
    }

    @Override
    public void delete(SubService subService) {
        subServiceDao.delete(subService);
    }

    @Override
    public void update(SubService subService) {
        subServiceDao.update(subService.getService(),subService.getSubServiceName(),subService.getExplanations(),subService.getId());
    }

    @Override
    public Iterable<SubService> findAll(int page , int size) {
        return subServiceDao.findAll(PageRequest.of(page,size));
    }

    @Override
    public SubService findById(int id) {
        Optional<SubService> optionalSubService = subServiceDao.findById(id);
        if(optionalSubService.isPresent())
            return optionalSubService.get();
        else throw new SubServiceNotFoundException();
    }

    //todo use interface //service interface check service uniqe


}
