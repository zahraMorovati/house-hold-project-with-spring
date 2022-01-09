package ir.maktab.service;

import ir.maktab.data.dao.interfaces.ManagerDao;
import ir.maktab.data.model.entity.Manager;
import ir.maktab.service.interfaces.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    protected ManagerDao managerDao;

    @Autowired
    public ManagerServiceImpl(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @Override
    public void save(Manager manager){
        managerDao.save(manager);
    }

    @Override
    public void delete(Manager manager){
        managerDao.delete(manager);
    }

    @Override
    public void update(Manager manager) {
        managerDao.updateById(manager.getName(),manager.getFamily(),manager.getEmail(),manager.getPassword(),manager.getId());
    }

    @Override
    public Iterable<Manager> findAll() {
        return managerDao.findAll();
    }

    @Override
    public void changePassword(String email,String newPass){
        List<Manager> result = managerDao.findManagerByEmail(email);
        if(!result.isEmpty()){
            Manager manager = result.get(0);
            manager.setPassword(newPass);
            managerDao.updatePasswordByEmail(newPass,email);
        }else throw new RuntimeException("wrong email!");
    }

    @Override
    public Manager findById(int id) {
        return managerDao.findById(id).orElse(null);
    }



}
