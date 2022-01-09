package ir.maktab.service;


import ir.maktab.data.dao.interfaces.SubServiceDao;
import ir.maktab.data.model.entity.SubService;
import ir.maktab.service.interfaces.SubServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Iterable<SubService> findAll() {
        return subServiceDao.findAll();
    }

    @Override
    public SubService findById(int id) {
        return subServiceDao.findById(id).orElse(null);
    }

    //todo use interface //service interface check service uniqe


}
