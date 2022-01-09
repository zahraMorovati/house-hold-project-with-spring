package ir.maktab.data.dao.interfaces;

import ir.maktab.data.model.entity.Specialist;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SpecialistDao {
    void save(Specialist specialist);

    void delete(Specialist specialist);

    void update(Specialist specialist);

    List<Specialist> getSpecialistById(int id);

    List<Specialist> getSpecialistByEmail(String email);

    List<Specialist> getAllSpecialists();

    List<UserDto> filter(String name, String family, String email, int maxResult, int firstResult);
}
