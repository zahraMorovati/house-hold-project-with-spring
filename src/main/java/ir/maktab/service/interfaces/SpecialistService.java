package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Specialist;

import java.util.List;

public interface SpecialistService {

    void save(Specialist specialist);

    void delete(Specialist specialist);

    void update(Specialist specialist);

    Iterable<Specialist> findAll(int page , int size);

    void changePassword(String email,String newPassword);

    Specialist findById(int id);

    List<Specialist> filterByNameOrFamilyOrEmail(String name, String family, String email);
}
