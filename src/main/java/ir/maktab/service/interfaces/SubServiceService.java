package ir.maktab.service.interfaces;

import ir.maktab.data.model.entity.SubService;

public interface SubServiceService {

    void save(SubService subService);

    void delete(SubService subService);

    void update(SubService subService);

    Iterable<SubService> findAll(int page , int size);

    SubService findById(int id);
}
