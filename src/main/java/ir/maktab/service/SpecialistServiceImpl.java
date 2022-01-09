package ir.maktab.service;

import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.model.entity.Specialist;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.service.interfaces.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Iterable<Specialist> findAll(int page , int size) {
        return specialistDao.findAll(PageRequest.of(page,size));
    }

    @Override
    public void changePassword(String email,String newPass){
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if(!result.isEmpty()){
            specialistDao.updatePasswordByEmail(email,newPass);
        }else throw new RuntimeException("wrong email!");
    }

    @Override
    public Specialist findById(int id) {
        Optional<Specialist> optionalSpecialist = specialistDao.findById(id);
        if(optionalSpecialist.isPresent())
            return optionalSpecialist.get();
        else throw new SpecialistNotFoundException();
    }

    @Override
    public List<Specialist> filterByNameOrFamilyOrEmail(String name, String family, String email) {
        List<Specialist> specialists = specialistDao.findSpecialistByNameOrFamilyOrEmail(name, family, email);
        if(!specialists.isEmpty())
            return specialists;
        else throw new SpecialistNotFoundException();

    }


}
