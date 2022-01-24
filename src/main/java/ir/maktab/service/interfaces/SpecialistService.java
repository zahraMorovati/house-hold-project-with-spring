package ir.maktab.service.interfaces;

import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Specialist;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

public interface SpecialistService {

    void save(Specialist specialist);

    void delete(Specialist specialist);

    void update(Specialist specialist);

    Iterable<Specialist> findAll(int page , int size);

    void changePassword(String email,String newPassword);

    Specialist findById(int id);

    List<Specialist> filterByNameOrFamilyOrEmail(String name, String family, String email);

    void uploadSpecialistImage(Specialist specialist , CommonsMultipartFile image) throws IOException;

    List<UserDto> getAllSpecialists();

    Specialist findByEmail(String email);

    Specialist findByEmailAndPassword(String email, String password);
}
