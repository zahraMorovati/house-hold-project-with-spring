package dao.interfaces;

import dto.UserDto;
import model.entity.Specialist;

import java.util.List;

public interface SpecialistDao {
    void save(Specialist specialist);

    void delete(Specialist specialist);

    void update(Specialist specialist);

    List<Specialist> getSpecialistById(int id);

    List<Specialist> getSpecialistByEmailAndPassword(String email , String password);

    List<Specialist> getAllSpecialists();

    List<UserDto> filter(String name, String family, String email, int maxResult, int firstResult);
}
