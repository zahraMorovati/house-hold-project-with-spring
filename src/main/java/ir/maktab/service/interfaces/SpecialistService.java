package ir.maktab.service.interfaces;

import ir.maktab.data.dto.SpecialistDto;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.enums.UserState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

public interface SpecialistService {

    void save(Specialist specialist);

    void delete(String email);

    void update(Specialist specialist);

    Iterable<Specialist> findAll(int page, int size);

    void changePassword(String email, String newPassword);

    Specialist findById(int id);

    void uploadSpecialistImage(Specialist specialist, CommonsMultipartFile image) throws IOException;

    List<SpecialistDto> getAllSpecialists();

    Specialist findByEmail(String email);

    Specialist findByEmailAndPassword(String email, String password);

    List<SpecialistDto> filterNotConfirmedSpecialists(String name, String family, String email);

    List<SpecialistDto> advancedFilterSpecialists(String name, String family, String email,
                                                  String startingRegistrationDate,
                                                  String endingRegistrationDate,
                                                  Integer minOrderNumber, Integer maxOrderNumber);
    void confirmSpecialist(String email);

    List<SpecialistDto> getAllNotConfirmedSpecialist();

    void updateSpecialistState(UserState userState, String email);
}
