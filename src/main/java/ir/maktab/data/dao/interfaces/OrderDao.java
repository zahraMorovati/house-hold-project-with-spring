package ir.maktab.data.dao.interfaces;

import ir.maktab.data.model.entity.Address;
import ir.maktab.data.model.entity.Order;
import ir.maktab.data.model.entity.SubService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface OrderDao extends PagingAndSortingRepository<Order, Integer> {

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
}
