package dao.interfaces;

import dto.ManagerDto;
import model.entity.Manager;

import java.util.List;

public interface ManagerDao {

    void save(Manager manager);

    void delete(Manager manager);

    void update(Manager manager);

    List<Manager> getAllManagers();

    List<Manager> getManagerById(int id);

    List<Manager> getManagerByEmailAndPassword(String email, String password);

    List<ManagerDto> filter(String name, String family, String email, int maxResult, int firstResult);
}
