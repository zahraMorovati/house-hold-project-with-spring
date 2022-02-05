package ir.maktab.data.dao.interfaces;

import ir.maktab.data.entity.Suggestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SuggestionDao extends PagingAndSortingRepository<Suggestion,Integer> {

    List<Suggestion> findSuggestionBySuggestionCode(String suggestionCode);

    @Query(value = "select * from suggestion where order_id=:orderId",nativeQuery = true)
    List<Suggestion> findSuggestionByOrderAndSpecialist(@Param("orderId")int orderId);

    @Query(value = "select specialist_id from suggestion where suggestionCode=:suggestionCode",nativeQuery = true)
    List<Integer> findSpecialistBySuggestionCode(@Param("suggestionCode")String suggestionCode);
}
