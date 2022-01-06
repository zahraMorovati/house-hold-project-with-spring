package service;

import dao.SpecialistDaoImpl;
import dto.UserDto;
import model.entity.Specialist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialistService {

    SpecialistDaoImpl specialistDao;

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

    public void changePassword(String email,String newPass){
        List<Specialist> result = specialistDao.getSpecialistByEmail(email);
        if(!result.isEmpty()){
            Specialist specialist = result.get(0);
            specialist.setPassword(newPass);
            specialistDao.update(specialist);
        }else throw new RuntimeException("wrong email!");
    }

    public List<UserDto> filter(String name , String password , String email){
        //pagination is not handled that's why i put zero for max result
        List<UserDto> list = specialistDao.filter(name, email, password, 0, 0);
        if(list.isEmpty())
            throw new RuntimeException("cannot find any user!");
        else return list;
    }

    public List<Specialist> getAllSpecialists(){
        List<Specialist> list = specialistDao.getAllSpecialists();
        if(list.isEmpty())
            throw new RuntimeException("there is no specialist!");
        else return list;
    }


}
