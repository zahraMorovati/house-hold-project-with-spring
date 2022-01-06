package service;

import dao.ManagerDaoImpl;
import model.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    protected ManagerDaoImpl managerDao;

    public ManagerDaoImpl getManagerDao() {
        return managerDao;
    }

    @Autowired
    public void setManagerDao(@Qualifier("managerDao") ManagerDaoImpl managerDao) {
        this.managerDao = managerDao;
    }

    public void save(Manager manager){
        managerDao.save(manager);
    }

    public void update(Manager manager){
        managerDao.update(manager);
    }

    public void delete(Manager manager){
        managerDao.delete(manager);
    }

    public Manager findManagerBYId(int id){
        List<Manager> managerList = managerDao.getManagerById(id);
        if(managerList.isEmpty())
            throw new RuntimeException("cannot find manager by id!");
        else return managerList.get(0);
    }

    public void changePassword(String email,String newPass){
        List<Manager> result = managerDao.getManagerByEmail(email);
        if(!result.isEmpty()){
            Manager manager = result.get(0);
            manager.setPassword(newPass);
            managerDao.update(manager);
        }else throw new RuntimeException("wrong email!");
    }
}
