package ir.maktab.service;

import ir.maktab.data.dao.interfaces.*;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.mappers.OrderMapper;
import ir.maktab.data.entity.*;
import ir.maktab.data.enums.OrderState;
import ir.maktab.data.enums.UserState;
import ir.maktab.exception.UserEceptions.UserNotConfirmedException;
import ir.maktab.exception.customerExceptions.BalanceIsNotEnoughException;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.exception.orderExceptions.CannotSaveOrderException;
import ir.maktab.exception.orderExceptions.MaxReachedOrderNumberException;
import ir.maktab.exception.orderExceptions.OrderNotFoundException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.exception.subServiceExceptions.SubServiceNotFoundException;
import ir.maktab.exception.suggestionExceptions.SuggestedPriceIsHigherThanBasePriceException;
import ir.maktab.exception.suggestionExceptions.SuggestionNotFoundException;
import ir.maktab.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static ir.maktab.util.Convert.parsDate;
import static ir.maktab.util.Convert.toOrderState;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final CustomerDao customerDao;
    private final SubServiceDao subServiceDao;
    private final SpecialistDao specialistDao;
    private final SuggestionDao suggestionDao;
    private final int customerMaxOrders = 5;
    private final double specialistFeeRange = 0.7;

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
        orderDao.save(order);
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

        Optional<Customer> optionalCustomer = customerDao.findById(customer.getId());
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
            if (customer.getState().equals(UserState.CONFIRMED)) {
                int customerUnfinishedOrders = orderDao.findOrderByCustomer_Email(customer.getEmail()).stream().map(i -> i.getOrderState() != OrderState.DONE).collect(Collectors.toList()).size();
                if (customerUnfinishedOrders < customerMaxOrders) {
                    Optional<SubService> optionalSubService = subServiceDao.findById(subService.getId());
                    if (optionalSubService.isPresent()) {
                        if (subService.getPrice() > suggestedPrice) {

                            subService = optionalSubService.get();
                            String code = getRandomCode(orderDao);
                            Order order = getOrder(customer, subService, suggestedPrice, explanations, address, startDate, code);
                            orderDao.save(order);

                        } else throw new SuggestedPriceIsHigherThanBasePriceException();
                    } else throw new SubServiceNotFoundException();
                } else throw new MaxReachedOrderNumberException();
            } else throw new UserNotConfirmedException();
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
    public void selectSpecialistSuggestion(String orderCode, String suggestionCode) {

        List<Order> orders = orderDao.findOrderByOrderCode(orderCode);

        if (orders.size() >= 1) {
            Order order = orders.get(0);
            List<Suggestion> resultSuggestions = suggestionDao.findSuggestionBySuggestionCode(suggestionCode);
            if (!resultSuggestions.isEmpty()) {

                updateOrderState(order, OrderState.WAITING_FOR_SPECIALIST_TO_COME);
                Suggestion suggestion = resultSuggestions.get(0);
                orderDao.selectSpecialist(suggestion.getSpecialist().getId(), order.getOrderCode());

            } else throw new SuggestionNotFoundException();
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

    @Override
    public List<OrderDto> getSpecialistAvailableOrders(String email) {
        List<Specialist> result = specialistDao.findSpecialistByEmail(email);
        if (!result.isEmpty()) {
            Specialist specialist = result.get(0);
            List<String> specialistAvailableOrderCods = orderDao.findSpecialistAvailableOrders(specialist.getId());
            return specialistAvailableOrderCods.stream().map(i -> orderDao.findOrderByOrderCode(i).get(0)).map(OrderMapper::toOrderDto).collect(Collectors.toList());
        } else throw new SpecialistNotFoundException();
    }

    @Override
    public List<OrderDto> getCustomerOrdersByOrderState(String email, OrderState orderState) {
        List<Order> orderList = orderDao.findOrderByCustomer_Email(email);
        return orderList.stream().filter(i -> i.getOrderState() == OrderState.DONE).map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    private String getRandomCode(OrderDao orderDao) {
        Random random = new Random();
        int randomNumber = random.nextInt();
        if (randomNumber < 0) {
            return getRandomCode(orderDao);
        } else {
            String code = String.valueOf(randomNumber).substring(0, 5);
            int result = orderDao.findOrderByOrderCode(code).size();
            if (result <= 0) {
                return code;
            } else return getRandomCode(orderDao);
        }
    }

    @Override
    public void paymentByBalance(String customerEmail, String orderCode) {
        List<Customer> customerResult = customerDao.findCustomerByEmail(customerEmail);
        if (!customerResult.isEmpty()) {
            Customer customer = customerResult.get(0);
            List<Order> orderResult = orderDao.findOrderByOrderCode(orderCode);
            if (!orderResult.isEmpty()) {
                Order order = orderResult.get(0);
                double suggestedPrice = order.getSuggestedPrice();
                if (customer.getBalance() > suggestedPrice) {

                    updateSpecialistBalance(order, suggestedPrice);
                    updateCustomerBalance(customer, suggestedPrice);
                    updateOrderState(order, OrderState.PAID);

                } else throw new BalanceIsNotEnoughException();
            } else throw new OrderNotFoundException();
        } else throw new CustomerNotFoundException();
    }

    private void updateCustomerBalance(Customer customer, double suggestedPrice) {
        double customerBalance = customer.getBalance();
        customerBalance -= suggestedPrice;
        customer.setBalance(customerBalance);
        customerDao.save(customer);
    }


    @Override
    public void paymentByCard(String customerEmail, String orderCode) {

        List<Customer> customerResult = customerDao.findCustomerByEmail(customerEmail);
        if (!customerResult.isEmpty()) {
            List<Order> orderResult = orderDao.findOrderByOrderCode(orderCode);
            if (!orderResult.isEmpty()) {
                Order order = orderResult.get(0);
                double suggestedPrice = order.getSuggestedPrice();
                updateSpecialistBalance(order, suggestedPrice);
                updateOrderState(order, OrderState.PAID);
            } else throw new OrderNotFoundException();
        } else throw new CustomerNotFoundException();
    }

    @Override
    public List<OrderDto> orderAdvancedFilter(String startDate, String endDate, String orderState, String serviceName, String subServiceName) {
        Date orderStartDate = parsDate(startDate);
        Date orderEndDate = parsDate(endDate);
        Specification<Order> specification = OrderDao.filterOrders(orderStartDate, orderEndDate, toOrderState(orderState), serviceName, subServiceName);
        return orderDao.findAll(specification).stream()
                .map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    private void updateOrderState(Order order, OrderState paid) {
        order.setOrderState(paid);
        orderDao.save(order);
    }

    private void updateSpecialistBalance(Order order, double suggestedPrice) {
        Specialist specialist = order.getSpecialist();
        double specialistBalance = specialist.getBalance();
        specialistBalance += suggestedPrice * (specialistFeeRange);
        specialist.setBalance(specialistBalance);
        specialistDao.save(specialist);
    }


}
