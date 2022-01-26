package ir.maktab.service;

import ir.maktab.data.dao.interfaces.CustomerDao;
import ir.maktab.data.dao.interfaces.OrderDao;
import ir.maktab.data.dao.interfaces.SpecialistDao;
import ir.maktab.data.dao.interfaces.SubServiceDao;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.mappers.OrderMapper;
import ir.maktab.data.entity.*;
import ir.maktab.data.enums.OrderState;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.exception.orderExceptions.CannotSaveOrderException;
import ir.maktab.exception.orderExceptions.OrderNotFoundException;
import ir.maktab.exception.subServiceExceptions.SubServiceNotFoundException;
import ir.maktab.exception.suggestionExceptions.EmptySuggestionList;
import ir.maktab.exception.suggestionExceptions.SuggestionNotFoundException;
import ir.maktab.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final CustomerDao customerDao;
    private final SubServiceDao subServiceDao;
    private final SpecialistDao specialistDao;

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
    public Order findByOrderCode(String orderCode) {
        List<Order> result = orderDao.findOrderByOrderCode(orderCode);
        if (result.isEmpty())
            throw new OrderNotFoundException();
        else return result.get(0);
    }

    @Override
    public void addCustomerOrder(Customer customer, SubService subService, double suggestedPrice,
                                 String explanations, Address address, Date startDate) {
        //check customer existence
        Optional<Customer> optionalCustomer = customerDao.findById(customer.getId());
        if (optionalCustomer.isPresent()) {
            //check subService existence
            //todo check customer orders is unfinished
            Optional<SubService> optionalSubService = subServiceDao.findById(subService.getId());
            if (optionalSubService.isPresent()) {

                customer = optionalCustomer.get();
                subService = optionalSubService.get();

                String code = getRandomCode(orderDao);
                Order order = getOrder(customer, subService, suggestedPrice, explanations, address, startDate, code);
                orderDao.save(order);
            }else throw new SubServiceNotFoundException();

        } else throw new CustomerNotFoundException();

    }



    private Order getOrder(Customer customer, SubService subService, double suggestedPrice, String explanations, Address address, Date startDate, String orderCode) {
        Order order = Order.builder()
                .setOrderCode(orderCode)
                .setSubService(subService)
                .setCustomer(customer)
                .setSuggestedPrice(suggestedPrice)
                .setExplanations(explanations)
                .setAddress(address)
                .setStartDate(startDate)
                .setOrderState(OrderState.WAITING_FOR_SPECIALIST_SUGGESTION)
                .build();
        return order;
    }

    @Override
    public void selectSpecialistSuggestion(Order order, Suggestion suggestion) {

        // check order existence
        Optional<Order> optionalOrder = orderDao.findById(order.getId());

        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            int suggestionId = suggestion.getId();
            Optional<Suggestion> optionalSuggestion = order.getSuggestions().stream().filter(i -> i.getId() == suggestionId).findAny();
            if (optionalSuggestion.isPresent()) {

                suggestion = optionalSuggestion.get();
                order.setSpecialist(suggestion.getSpecialist());
                order.setOrderState(OrderState.WAITING_FOR_SPECIALIST_TO_COME);
                orderDao.save(order);

            } else throw new SuggestionNotFoundException();

        } else throw new OrderNotFoundException();
    }

    @Override
    public List<Suggestion> getOrderSuggestions(Order order) {

        Optional<Order> optionalOrder = orderDao.findById(order.getId());
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            List<Suggestion> suggestions = order.getSuggestions();
            if (!suggestions.isEmpty()) {
                /*suggestions.sort(Comparator.comparingDouble(Suggestion::getSuggestedPrice)
                        .thenComparing((o1, o2) -> Double.compare(o1.getSpecialist().getPoint(),
                                o2.getSpecialist().getPoint())));*/

                return suggestions;

            } else throw new EmptySuggestionList();
        } else throw new OrderNotFoundException();
    }

    @Override
    public List<OrderDto> getCustomerOrders(String email) {
        List<Order> orderList = orderDao.findOrderByCustomer_Email(email);
        return orderList.stream().map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getSpecialistOrders(String email) {
        List<Order> orderList = orderDao.findOrderBySpecialist_Email(email);
        return orderList.stream().map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    private String getRandomCode(OrderDao orderDao) {
        Random random = new Random();
        String code = String.valueOf(random.nextInt()).substring(0,5);
        int result = orderDao.findOrderByOrderCode(code).size();
        if (result <= 0) {
            return code;
        } else return getRandomCode(orderDao);
    }


}
