package ir.maktab.data.dao.interfaces;

import ir.maktab.data.entity.Service;
import ir.maktab.data.entity.SubService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubServiceDao extends PagingAndSortingRepository<SubService, Integer> {

    @Transactional
    @Modifying
    @Query(value = "update SubService s set " +
            "s.service=:service ," +
            "s.subServiceName=:subServiceName , " +
            "s.explanations=:explanations where s.id=:id")
    void update(@Param("service") Service service,
                @Param("subServiceName") String subServiceName,
                @Param("explanations") String explanations,
                @Param("id") int id);

    List<SubService> findSubServiceBySubServiceName(String subServiceName);



}
