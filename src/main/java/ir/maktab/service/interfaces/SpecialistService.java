package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.Specialist;

public interface SpecialistService {

    void save(Specialist specialist);

    void delete(Specialist specialist);

    void update(Specialist specialist);

    Iterable<Specialist> findAll();

    void changePassword(String email,String newPassword);

    Specialist findById(int id);
}
