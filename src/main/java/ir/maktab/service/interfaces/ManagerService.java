package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Manager;


public interface ManagerService {
    void save(Manager manager);

    void delete(Manager manager);

    void update(Manager manager);

    Iterable<Manager> findAll();

    void changePassword(String email,String newPassword);

    Manager findById(int id);
}
