package dao.interfaces;

import model.entity.Specialist;

import java.util.List;

public interface SpecialistDao {
    void save(Specialist specialist);

    void delete(Specialist specialist);

    void update(Specialist specialist);

    List<Specialist> getSpecialistById(int id);

    List<Specialist> getAllSpecialists();
}
