package dao.interfaces;

import model.entity.Manager;

import java.util.List;

public interface ManagerDao {

    void save(Manager manager);
    void delete(Manager manager);
    void update(Manager manager);
    List<Manager> getAllManagers();
    List<Manager> getManagerById();
    List<Manager> getManagerByEmailAndPassword();
}
