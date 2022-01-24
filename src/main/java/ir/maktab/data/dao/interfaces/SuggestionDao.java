package ir.maktab.data.dao.interfaces;

import ir.maktab.data.entity.Suggestion;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface SuggestionDao extends PagingAndSortingRepository<Suggestion,Integer> {

    List<Suggestion> findSuggestionBySuggestionCode(String suggestionCode);
}
