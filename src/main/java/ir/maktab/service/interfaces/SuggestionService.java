package ir.maktab.service.interfaces;

import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.Suggestion;

import java.util.Date;

public interface SuggestionService {

    Suggestion findById(int id);

    void addSpecialistSuggestion(Order order , Specialist specialist, double suggestedPrice , double workHour , Date startTime );

    Suggestion findBySuggestionCode(String suggestionCode);
}
