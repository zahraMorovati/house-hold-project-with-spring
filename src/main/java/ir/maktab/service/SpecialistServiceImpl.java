package ir.maktab.service;

import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dto.SpecialistDto;
import ir.maktab.data.dto.mappers.SpecialistMapper;
import ir.maktab.data.entity.Specialist;
import ir.maktab.exception.UserEceptions.WrongEmailException;
import ir.maktab.exception.specialistExceptions.CannotSaveSpecialistException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.service.interfaces.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ir.maktab.util.Convert.parsDate;

@RequiredArgsConstructor
@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistDao specialistDao;

    @Override
    public void save(Specialist specialist) {
        specialistDao.save(specialist);
        if (specialist.getId() < 0) throw new CannotSaveSpecialistException();
    }

    @Override
    public void delete(Specialist specialist) {
        specialistDao.delete(specialist);
    }

    @Override
    public void update(Specialist specialist) {
        specialistDao.save(specialist);
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
        if (optionalSpecialist.isPresent()) return optionalSpecialist.get();
        else throw new SpecialistNotFoundException();
    }

    @Override
    public void uploadSpecialistImage(Specialist specialist, CommonsMultipartFile image) throws IOException {
        specialistDao.updateSpecialistImage(image.getBytes(), specialist.getEmail());
    }

    @Override
    public List<SpecialistDto> getAllSpecialists() {
        List<Specialist> specialists = StreamSupport.stream(specialistDao.findAll().spliterator(), false).collect(Collectors.toList());
        return specialists.stream().map(SpecialistMapper::toSpecialistDto).collect(Collectors.toList());

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
        List<Specialist> result = specialistDao.findSpecialistByEmailAndPassword(email, password);
        if (result.size() >= 1) {
            return result.get(0);
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public List<SpecialistDto> filterSpecialists(String name, String family, String email) {
        Specification<Specialist> specification = SpecialistDao.filterSpecialists(name, family, email);
        return specialistDao.findAll(specification).stream().map(SpecialistMapper::toSpecialistDto).collect(Collectors.toList());
    }

    @Override
    public List<SpecialistDto> advancedFilterSpecialists(String name, String family, String email, String startingRegistrationDate, String endingRegistrationDate, Integer minOrderNumber, Integer maxOrderNumber) {

        Date startingDate = parsDate(startingRegistrationDate);
        Date endingDate = parsDate(startingRegistrationDate);

        int minNumber = 0;
        int maxNumber = 0;
        if (minOrderNumber != null) minNumber = minOrderNumber;
        if (maxOrderNumber != null) maxNumber = maxOrderNumber;

        Specification<Specialist> specification = SpecialistDao.advancedFilter(name, family, email, startingDate, endingDate, minOrderNumber, maxOrderNumber);
        return specialistDao.findAll(specification).stream().map(SpecialistMapper::toSpecialistDto).collect(Collectors.toList());
    }




}
