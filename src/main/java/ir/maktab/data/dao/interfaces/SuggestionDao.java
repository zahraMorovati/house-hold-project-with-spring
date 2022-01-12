package ir.maktab.data.dao.interfaces;

import ir.maktab.data.model.entity.Suggestion;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SuggestionDao extends PagingAndSortingRepository<Suggestion,Integer> {
}
