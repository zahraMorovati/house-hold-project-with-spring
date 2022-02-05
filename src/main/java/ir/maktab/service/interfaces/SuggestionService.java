package ir.maktab.service.interfaces;

import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.entity.Suggestion;

import java.util.Date;
import java.util.List;

public interface SuggestionService {

    Suggestion findById(int id);

    void addSpecialistSuggestion(String orderCode, String specialistEmail, double suggestedPrice , double workHour , Date startTime );

    Suggestion findBySuggestionCode(String suggestionCode);

    List<SuggestionDto> findSuggestionByOrder(String orderCode);
}
