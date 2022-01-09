package ir.maktab.service;

import ir.maktab.config.SpringConfig;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.model.entity.Specialist;
import ir.maktab.service.interfaces.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    SpecialistDao specialistDao;

    @Autowired
    public SpecialistServiceImpl(SpecialistDao specialistDao) {
        this.specialistDao = specialistDao;
    }

    @Override
    public void save(Specialist specialist){
        specialistDao.save(specialist);
    }

    @Override
    public void delete(Specialist specialist){
        specialistDao.delete(specialist);
    }

    @Override
    public void update(Specialist specialist){
        specialistDao.update(specialist.getName(),specialist.getFamily(),specialist.getEmail(),specialist.getPassword(),specialist.getBalance(),specialist.getId());
    }

    @Override
    public Iterable<Specialist> findAll() {
        return specialistDao.findAll();
    }

    @Override
    public void changePassword(String email,String newPass){
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if(!result.isEmpty()){
            specialistDao.updateCustomerPasswordByEmail(email,newPass);
        }else throw new RuntimeException("wrong email!");
    }

    @Override
    public Specialist findById(int id) {
        return specialistDao.findById(id).orElse(null);
    }



}
