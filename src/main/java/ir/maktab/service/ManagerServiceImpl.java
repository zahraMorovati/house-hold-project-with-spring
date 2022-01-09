package ir.maktab.service;

import ir.maktab.data.dao.interfaces.ManagerDao;
import ir.maktab.data.model.entity.Manager;
import ir.maktab.exception.UserEceptions.WrongEmailException;
import ir.maktab.exception.managerExceptions.ManagerNotFoundException;
import ir.maktab.service.interfaces.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Iterable<Manager> findAll(int page , int size) {
        return managerDao.findAll(PageRequest.of(page,size));
    }

    @Override
    public void changePassword(String email,String newPass){
        List<Manager> result = managerDao.findManagerByEmail(email);
        if(!result.isEmpty()){
            Manager manager = result.get(0);
            manager.setPassword(newPass);
            managerDao.updatePasswordByEmail(newPass,email);
        }else throw new WrongEmailException();
    }

    @Override
    public Manager findById(int id) {
        Optional<Manager> optionalManager = managerDao.findById(id);
        if(optionalManager.isPresent())
            return optionalManager.get();
        else throw new ManagerNotFoundException();
    }

    @Override
    public List<Manager> filterByNameOrFamilyOrEmail(String name, String family, String email) {
        List<Manager> managers = managerDao.findManagerByNameOrFamilyOrEmail(name, family, email);
        if(!managers.isEmpty())
            return managers;
        else throw new ManagerNotFoundException();
    }


}
