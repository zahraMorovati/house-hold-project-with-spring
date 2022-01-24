package ir.maktab.service.interfaces;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.SubService;

import java.util.List;

public interface SubServiceService {

    void save(SubServiceDto subServiceDto);

    void delete(SubService subService);

    void update(SubService subService);

    Iterable<SubService> findAll(int page , int size);

    SubService findById(int id);

    SubService findByName(String name);

    void addSpecialistToSubService(SubService subservice , Specialist specialist);

    void removeSpecialistFromSubService(SubService subservice , Specialist specialist);

    List<SubServiceDto> getAllSubServices();

}
