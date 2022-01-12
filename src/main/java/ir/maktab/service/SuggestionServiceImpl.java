package ir.maktab.service;

import ir.maktab.data.dao.interfaces.SuggestionDao;
import ir.maktab.data.model.entity.Suggestion;
import ir.maktab.exception.suggestionExceptions.SuggestionNotFoundException;
import ir.maktab.service.interfaces.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    SuggestionDao suggestionDao;

    @Autowired
    public SuggestionServiceImpl(SuggestionDao suggestionDao) {
        this.suggestionDao = suggestionDao;
    }


    @Override
    public Suggestion findById(int id) {
        Optional<Suggestion> optionalSuggestion = suggestionDao.findById(id);
        if(optionalSuggestion.isPresent())
            return optionalSuggestion.get();
        else throw new SuggestionNotFoundException();
    }
}
