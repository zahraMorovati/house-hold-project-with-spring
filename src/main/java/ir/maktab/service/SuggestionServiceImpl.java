package ir.maktab.service;

import ir.maktab.data.dao.interfaces.OrderDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dao.interfaces.SuggestionDao;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.dto.mappers.SuggestionMapper;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    public void addSpecialistSuggestion(String orderCode, String specialistEmail, double suggestedPrice,
                                        double workHour, Date startTime) {

        List<Order> optionalOrder = orderDao.findOrderByOrderCode(orderCode);
        if (optionalOrder.size()>=1) {

            List<Specialist> optionalSpecialist = specialistDao.findSpecialistByEmail(specialistEmail);
            if (optionalSpecialist.size()>=1) {

                Order order = optionalOrder.get(0);
                Specialist specialist = optionalSpecialist.get(0);

                if (suggestedPrice < order.getSubService().getPrice()) {
                    Suggestion suggestion = getSuggestion(specialist,suggestedPrice, workHour, startTime, order);

                    order.setOrderState(OrderState.WAITING_FOR_SPECIALIST_SELECTION);
                    orderDao.save(order);

                    suggestion.setSuggestionCode(getRandomCode(suggestionDao));
                    suggestionDao.save(suggestion);

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

    @Override
    public List<SuggestionDto> findSuggestionByOrder(String orderCode) {
        List<Order> resultOrders = orderDao.findOrderByOrderCode(orderCode);
        if(resultOrders.size()>=1){
            Order order = resultOrders.get(0);
            List<Suggestion> suggestions = suggestionDao.findSuggestionByOrderAndSpecialist(order.getId());
            return suggestions
                    .stream().map(i-> SuggestionMapper.toSuggestionDto(i,order)).collect(Collectors.toList());
        }else throw new OrderNotFoundException();
    }

    private Suggestion getSuggestion(Specialist specialist,double suggestedPrice, double workHour, Date startTime, Order order) {
        return Suggestion.builder()
                .setSpecialist(specialist)
                .setSuggestedPrice(suggestedPrice)
                .setWorkHour(workHour)
                .setOrder(order)
                .setStartTime(startTime).build();
    }

    private String getRandomCode(SuggestionDao suggestionDao) {
        Random random = new Random();
        int randomNumber = random.nextInt();
        if(randomNumber<0){
            return getRandomCode(suggestionDao);
        }else {
            String code = String.valueOf(randomNumber).substring(0,5);
            int result = suggestionDao.findSuggestionBySuggestionCode(code).size();
            if (result <= 0) {
                return code;
            } else return getRandomCode(suggestionDao);
        }
    }
}
