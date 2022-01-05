package service;

import dao.SpecialistDaoImpl;
import model.entity.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistService {

    SpecialistDaoImpl specialistDao;

    public void sayHello(){
        System.out.println("hello");
    }

    public SpecialistDaoImpl getSpecialistDao() {
        return specialistDao;
    }

    @Autowired
    public void setSpecialistDao(@Qualifier("specialistDao") SpecialistDaoImpl specialistDao) {
        this.specialistDao = specialistDao;
    }

    public void save(Specialist specialist){
        specialistDao.save(specialist);
    }

    public void update(Specialist specialist){
        specialistDao.update(specialist);
    }

    public void delete(Specialist specialist){
        specialistDao.delete(specialist);
    }

    public Specialist findSpecialistById(int id){
        List<Specialist> specialistList = specialistDao.getSpecialistById(id);
        if(specialistList.isEmpty())
            throw new RuntimeException("cannot find specialist by id!");
        else return specialistList.get(0);
    }

    public Specialist findSpecialistByEmailAndPassword(String email , String password){
        List<Specialist> specialists = specialistDao.getSpecialistByEmailAndPassword(email, password);
        if(specialists.isEmpty())
            throw new RuntimeException("wrong email or password!");
        else return specialists.get(0);
    }
}
