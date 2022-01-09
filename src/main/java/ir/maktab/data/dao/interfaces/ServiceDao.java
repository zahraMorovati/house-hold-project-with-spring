package ir.maktab.data.dao.interfaces;

import ir.maktab.data.model.entity.Address;
import ir.maktab.data.model.entity.Service;
import ir.maktab.data.model.entity.SubService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ServiceDao extends PagingAndSortingRepository<Service,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update Service s set s.serviceName=:serviceName , s.price=:price where s.id=:id")
    void update(@Param("serviceName") String serviceName, @Param("price") double price, @Param("id") int id);


}
