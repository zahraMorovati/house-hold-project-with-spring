package ir.maktab.data.dao.interfaces;

import ir.maktab.data.entity.Address;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.SubService;
import ir.maktab.data.enums.OrderState;
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
public interface OrderDao extends PagingAndSortingRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @Transactional
    @Modifying
    @Query(value = "update Order o set " +
            "o.subService=:subService ," +
            "o.suggestedPrice=:suggestedPrice , " +
            "o.explanations=:explanations ," +
            "o.startDate=:startDate ," +
            "o.address=:address where o.id=:id")
    void update(@Param("subService") SubService subService,
                @Param("suggestedPrice") double suggestedPrice,
                @Param("explanations") String explanations,
                @Param("startDate") Date startDate,
                @Param("address") Address address,
                @Param("id") int id);

    List<Order> findOrderByOrderCode(String orderCode);

    List<Order> findOrderByCustomer_Email(String email);

    List<Order> findOrderBySpecialist_Email(String email);

    @Query(value = "select  orderCode from subservice_specialist join orders o on subservice_specialist.SubService_id = o.subService_id\n" +
            "where specialists_id=:id", nativeQuery = true)
    List<String> findSpecialistAvailableOrders(@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "update orders set specialist_id=:specialistId where orderCode=:orderCode", nativeQuery = true)
    void selectSpecialist(@Param("specialistId") int specialistId, @Param("orderCode") String orderCode);

    static Specification<Order> filterOrders(Date startDate, Date endDate, OrderState orderState, String serviceName, String subServiceName) {
        return (root, criteriaQuery, criteriaBuilder) -> {

            CriteriaQuery<Order> resultCriteria = criteriaBuilder.createQuery(Order.class);
            Join<Order, SubService> subServiceJoin = root.join("subService");
            List<Predicate> filterPredicates = new ArrayList<>();

            if (orderState != null) {
                filterPredicates.add(criteriaBuilder.equal(root.get("orderState"), orderState));
            } else if (subServiceName != null && !subServiceName.isEmpty()) {
                filterPredicates.add(criteriaBuilder.equal(subServiceJoin.get("subServiceName"), subServiceName));
            } else if (serviceName != null && !serviceName.isEmpty()) {
                filterPredicates.add(criteriaBuilder.equal(subServiceJoin.get("service.serviceName"), serviceName));
            } else if (startDate != null && endDate != null) {
                filterPredicates.add(criteriaBuilder.between(root.get("registrationDate"), startDate, endDate));
            }
            resultCriteria.select(root).where(filterPredicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }

}
