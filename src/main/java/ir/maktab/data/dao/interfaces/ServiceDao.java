package ir.maktab.data.dao.interfaces;

import ir.maktab.data.entity.Service;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ServiceDao extends PagingAndSortingRepository<Service,Integer> {

    @Transactional
    @Modifying
    @Query(value = "update Service s set s.serviceName=:serviceName where s.id=:id")
    void update(@Param("serviceName") String serviceName, @Param("id") int id);

    List<Service> findByServiceName(String serviceName);

}
