package ir.maktab.data.dao.interfaces;

import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Service;
import ir.maktab.data.entity.SubService;
import ir.maktab.data.enums.OrderState;
import ir.maktab.data.enums.UserState;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public interface SubServiceDao extends PagingAndSortingRepository<SubService, Integer> , JpaSpecificationExecutor<SubService> {

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


    @Query(value = "select subServiceName from subservice_specialist join subservice s on subservice_specialist.SubService_id = s.id where specialists_id=:id", nativeQuery = true)
    List<String> findSubServiceNameBySpecialistId(@Param("id") int id);

    static Specification<SubService> filterSubServiceByServiceName(String serviceName) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            CriteriaQuery<SubService> resultCriteria = criteriaBuilder.createQuery(SubService.class);
            Join<SubService, Service> subServiceJoin = root.join("service");
            List<Predicate> filterPredicates = new ArrayList<>();

            if (serviceName != null && !serviceName.isEmpty()) {
                filterPredicates.add(criteriaBuilder.equal(subServiceJoin.get("serviceName"), serviceName));
            }
            resultCriteria.select(root).where(filterPredicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }


}
