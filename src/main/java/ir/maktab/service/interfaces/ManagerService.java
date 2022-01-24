package ir.maktab.service.interfaces;

import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Manager;

import java.util.List;

public interface ManagerService {
    void save(Manager manager);

    void delete(Manager manager);

    void update(Manager manager);

    Iterable<Manager> findAll(int page , int size);

    void changePassword(String email,String newPassword);

    Manager findById(int id);

    List<Manager> filterByNameOrFamilyOrEmail(String name, String family, String email); //todo

    Manager findByEmailAndPassword(String email, String password);
}
