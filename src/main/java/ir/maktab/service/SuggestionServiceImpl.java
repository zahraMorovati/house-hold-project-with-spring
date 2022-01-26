package ir.maktab.service;

import ir.maktab.data.dao.interfaces.OrderDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dao.interfaces.SuggestionDao;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.Suggestion;
import ir.maktab.data.enums.OrderState;
import ir.maktab.exception.orderExceptions.OrderNotFoundException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.exception.suggestionExceptions.SuggestedPriceIsHigherThanBasePriceException;
import ir.maktab.exception.suggestionExceptions.SuggestionNotFoundException;
import ir.maktab.service.interfaces.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionDao suggestionDao;
    private final OrderDao orderDao;
    private final SpecialistDao specialistDao;

    @Override
    public Suggestion findById(int id) {
        Optional<Suggestion> optionalSuggestion = suggestionDao.findById(id);
        if (optionalSuggestion.isPresent())
            return optionalSuggestion.get();
        else throw new SuggestionNotFoundException();
    }

    @Override
    public void addSpecialistSuggestion(Order order, Specialist specialist, double suggestedPrice,
                                        double workHour, Date startTime) {

        /* check order existence */
        List<Order> optionalOrder = orderDao.findOrderByOrderCode(order.getOrderCode());
        if (!optionalOrder.isEmpty()) {
            /* check specialist existence */
            //todo check if orderStatus is not WAITING_FOR_SPECIALIST_TO_COME
            List<Specialist> optionalSpecialist = specialistDao.findSpecialistByEmail(specialist.getEmail());
            if (!optionalSpecialist.isEmpty()) {

                order = optionalOrder.get(0);
                specialist = optionalSpecialist.get(0);

                if (suggestedPrice < order.getSubService().getPrice()) {

                    Suggestion suggestion = getSuggestion(specialist, suggestedPrice, workHour, startTime, order);
                    suggestionDao.save(suggestion);
                    order.setOrderState(OrderState.WAITING_FOR_SPECIALIST_SELECTION);
                    orderDao.save(order);

                } else throw new SuggestedPriceIsHigherThanBasePriceException();
            } else throw new SpecialistNotFoundException();
        } else throw new OrderNotFoundException();
    }

    @Override
    public Suggestion findBySuggestionCode(String suggestionCode) {
        List<Suggestion> result = suggestionDao.findSuggestionBySuggestionCode(suggestionCode);
        if(result.isEmpty())
            throw new SuggestionNotFoundException();
        else return result.get(0);
    }

    private Suggestion getSuggestion(Specialist specialist, double suggestedPrice, double workHour, Date startTime, Order order) {
        return Suggestion.builder()
                .setSpecialist(specialist)
                .setSuggestedPrice(suggestedPrice)
                .setWorkHour(workHour)
                .setOrder(order)
                .setStartTime(startTime).build();
    }
}
