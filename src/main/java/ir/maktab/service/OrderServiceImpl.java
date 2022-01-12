package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.dao.interfaces.OrderDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dao.interfaces.SubServiceDao;
import ir.maktab.data.model.entity.*;
import ir.maktab.data.model.enums.OrderState;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.exception.orderExceptions.CannotSaveOrderException;
import ir.maktab.exception.orderExceptions.OrderNotFoundException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.exception.suggestionExceptions.EmptySuggestionList;
import ir.maktab.exception.suggestionExceptions.SuggestedPriceIsHigherThanBasePriceException;
import ir.maktab.exception.subServiceExceptions.SubServiceNotFoundException;
import ir.maktab.exception.suggestionExceptions.SuggestionNotFoundException;
import ir.maktab.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;
    CustomerDao customerDao;
    SubServiceDao subServiceDao;
    SpecialistDao specialistDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, CustomerDao customerDao, SubServiceDao subServiceDao, SpecialistDao specialistDao) {
        this.orderDao = orderDao;
        this.customerDao = customerDao;
        this.subServiceDao = subServiceDao;
        this.specialistDao = specialistDao;
    }

    @Override
    public void save(Order order) {
        orderDao.save(order);
        if (order.getId() < 0)
            throw new CannotSaveOrderException();
    }

    @Override
    public void delete(Order order) {
        orderDao.save(order);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order.getSubService(), order.getSuggestedPrice(), order.getExplanations(), order.getStartDate(), order.getAddress(), order.getId());
    }

    @Override
    public Iterable<Order> findAll(int page, int size) {
        return orderDao.findAll(PageRequest.of(page, size));
    }

    @Override
    public Order findById(int id) {
        Optional<Order> optionalOrder = orderDao.findById(id);
        if (optionalOrder.isPresent())
            return optionalOrder.get();
        else throw new OrderNotFoundException();
    }

    @Override
    public void addCustomerOrder(Customer customer, SubService subService, double suggestedPrice, String explanations, Address address, Date startDate) {
        //check customer existence
        Optional<Customer> optionalCustomer = customerDao.findById(customer.getId());
        if (optionalCustomer.isPresent()) {
            //check subService existence
            Optional<SubService> optionalSubService = subServiceDao.findById(subService.getId());
            if (optionalSubService.isPresent()) {

                customer = optionalCustomer.get();
                subService = optionalSubService.get();

                Order order = Order.builder()
                        .setSubService(subService)
                        .setCustomer(customer)
                        .setSuggestedPrice(suggestedPrice)
                        .setExplanations(explanations)
                        .setAddress(address)
                        .setStartDate(startDate)
                        .setOrderState(OrderState.WAITING_FOR_SPECIALIST_SUGGESTION)
                        .build();

                orderDao.save(order);

                if (order.getId() < 0)
                    throw new CannotSaveOrderException();

            } else throw new SubServiceNotFoundException();

        } else throw new CustomerNotFoundException();


    }

    @Override
    public void addSpecialistSuggestion(Order order, Specialist specialist, double suggestedPrice, double workHour, Date startTime) {

        // check order existence
        Optional<Order> optionalOrder = orderDao.findById(order.getId());
        if (optionalOrder.isPresent()) {
            // check specialist existence
            Optional<Specialist> optionalSpecialist = specialistDao.findById(specialist.getId());
            if (optionalSpecialist.isPresent()) {

                order = optionalOrder.get();
                specialist = optionalSpecialist.get();

                if (suggestedPrice < order.getSubService().getPrice()){

                    Suggestion suggestion = Suggestion.builder()
                            .setSpecialist(specialist)
                            .setSuggestedPrice(suggestedPrice)
                            .setWorkHour(workHour)
                            .setStartTime(startTime).build();
                    order.getSuggestions().add(suggestion);
                    order.setOrderState(OrderState.WAITING_FOR_SPECIALIST_SELECTION);
                    orderDao.save(order);


                }else throw new SuggestedPriceIsHigherThanBasePriceException();

            } else throw new SpecialistNotFoundException();

        } else throw new OrderNotFoundException();
    }

    @Override
    public void selectSpecialistSuggestion(Order order, Suggestion suggestion) {

        // check order existence
        Optional<Order> optionalOrder = orderDao.findById(order.getId());

        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            int suggestionId = suggestion.getId();
            Optional<Suggestion> optionalSuggestion = order.getSuggestions().stream().filter(i -> i.getId() == suggestionId).findAny();
            if(optionalSuggestion.isPresent()){
                suggestion = optionalSuggestion.get();

                // set suggestion specialist to order
                order.setSpecialist(suggestion.getSpecialist());

                // set order state to WAITING_FOR_SPECIALIST_TO_COME
                order.setOrderState(OrderState.WAITING_FOR_SPECIALIST_TO_COME);

                orderDao.save(order);

            }else throw new SuggestionNotFoundException();

        } else throw new OrderNotFoundException();
    }

    @Override
    public List<Suggestion> getOrderSuggestions(Order order) {

        Optional<Order> optionalOrder = orderDao.findById(order.getId());
        if(optionalOrder.isPresent()){
            order = optionalOrder.get();
            List<Suggestion> suggestions = order.getSuggestions();
            if(!suggestions.isEmpty()){
                 suggestions.sort(Comparator.comparingDouble(Suggestion::getSuggestedPrice)
                        .thenComparing((o1,o2)-> Double.compare(o1.getSpecialist().getPoint(),o2.getSpecialist().getPoint())));

                 return suggestions;

            }else throw new EmptySuggestionList();
        }else throw new OrderNotFoundException();
    }


}
