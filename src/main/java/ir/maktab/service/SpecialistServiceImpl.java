package ir.maktab.service;

import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.dto.mappers.UserMapper;
import ir.maktab.data.entity.Specialist;
import ir.maktab.exception.UserEceptions.WrongEmailException;
import ir.maktab.exception.specialistExceptions.CannotSaveSpecialistException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.service.interfaces.SpecialistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    SpecialistDao specialistDao;

    @Autowired
    public SpecialistServiceImpl(SpecialistDao specialistDao) {
        this.specialistDao = specialistDao;
    }

    @Override
    public void save(Specialist specialist) {
        specialistDao.save(specialist);
        if (specialist.getId() < 0)
            throw new CannotSaveSpecialistException();
    }

    @Override
    public void delete(Specialist specialist) {
        specialistDao.delete(specialist);
    }

    @Override
    public void update(Specialist specialist) {
        specialistDao.update(specialist.getName(), specialist.getFamily(), specialist.getEmail(), specialist.getPassword(), specialist.getBalance(), specialist.getId());
    }

    @Override
    public Iterable<Specialist> findAll(int page, int size) {
        return specialistDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public void changePassword(String email, String newPass) {
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if (!result.isEmpty()) {
            specialistDao.updatePasswordByEmail(email, newPass);
        } else throw new WrongEmailException();
    }

    @Override
    public Specialist findById(int id) {
        Optional<Specialist> optionalSpecialist = specialistDao.findById(id);
        if (optionalSpecialist.isPresent())
            return optionalSpecialist.get();
        else throw new SpecialistNotFoundException();
    }

    @Override
    public List<Specialist> filterByNameOrFamilyOrEmail(String name, String family, String email) {
        List<Specialist> specialists = specialistDao.findSpecialistByNameOrFamilyOrEmail(name, family, email);
        if (!specialists.isEmpty())
            return specialists;
        else throw new SpecialistNotFoundException();

    }

    @Override
    public void uploadSpecialistImage(Specialist specialist, CommonsMultipartFile image) throws IOException {
        specialistDao.updateSpecialistImage(image.getBytes(), specialist.getEmail());
    }

    @Override
    public List<UserDto> getAllSpecialists() {
        List<Specialist> specialists = StreamSupport
                .stream(specialistDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return specialists.stream()
                .map(i -> UserMapper.toUserDto(i.getName(), i.getFamily(), i.getEmail())).collect(Collectors.toList());

    }

    @Override
    public Specialist findByEmail(String email) {
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public Specialist findByEmailAndPassword(String email, String password) {
        List<Specialist> result = specialistDao.findSpecialistByEmailAndPassword(email,password);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new SpecialistNotFoundException();
    }


}
