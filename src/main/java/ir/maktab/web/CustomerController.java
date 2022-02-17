package ir.maktab.web;

import ir.maktab.data.dto.CommentDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.dto.mappers.CommentMapper;
import ir.maktab.data.entity.*;
import ir.maktab.data.validators.Validation;
import ir.maktab.exception.customerExceptions.BalanceIsNotEnoughException;
import ir.maktab.exception.orderExceptions.MaxReachedOrderNumberException;
import ir.maktab.exception.suggestionExceptions.SuggestedPriceIsHigherThanBasePriceException;
import ir.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/customer")
@Controller
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final SubServiceServiceImpl subServiceService;
    private final ServiceServiceImpl serviceService;
    private final OrderServiceImpl orderService;
    private final SuggestionServiceImpl suggestionService;


    @RequestMapping("/dashbord")
    public ModelAndView backToDashbord(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Customer customer = customerService.findByEmail(email);
        return UserController.getCustomerAccountModelAndView(new ModelAndView(),customer,orderService,request.getSession());
    }

    @GetMapping("/addOrder")
    public ModelAndView addCustomerOrder(/*@RequestParam String email*/) {
        return getAddOrderModelAndView(/*email*/);
    }

    private ModelAndView getAddOrderModelAndView(/*String email*/) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("saveOrderPage");
        Map<String, String> services = new HashMap<>();
        serviceService.getServiceNames().forEach(i -> services.put(i.getName(), i.getName()));
        /*OrderDto orderDto = OrderDto.builder().setCustomer(email).build();*/
        mav.addObject("services", services);
        mav.addObject("orderDto", new OrderDto());
        return mav;
    }

    @RequestMapping("/saveOrder")
    public ModelAndView saveCustomerOrder(@ModelAttribute(name = "orderDto") OrderDto orderDto,
                                          @RequestParam(value = "date", required = false) String startDate,
                                          HttpServletRequest request) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        String errors = Validation.onOrderDto(orderDto);
        Customer customer = getCustomerByEmailFromHttpRequest(request);
        if (errors.equals("")) {
            try {
                Date date = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
                SubService subService = subServiceService.findByName(orderDto.getSubService());
                Address address = Address.builder().setCity(orderDto.getCity()).setCityState(orderDto.getCityState()).setPlaque(orderDto.getPlaque()).setExplanations(orderDto.getExplanations()).build();
                orderService.addCustomerOrder(customer, subService, orderDto.getSuggestedPrice(), orderDto.getExplanations(), address, date);
                return UserController.getCustomerAccountModelAndView(modelAndView, customer, orderService, request.getSession());

            } catch (SuggestedPriceIsHigherThanBasePriceException basePriceException) {
                return exceptionHandlerSuggestedPriceIsHighrThanBasePrice(/*orderDto*/);
            } catch (MaxReachedOrderNumberException maxReachedOrderNumberException) {
                return exceptionHandlerMaxReachedOrderNumberException(request, customer);
            }
        } else {
            ModelAndView mav = getAddOrderModelAndView();
            mav.addObject("saveOrderErrors", errors);
            return mav;
        }

    }

    private Customer getCustomerByEmailFromHttpRequest(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Customer customer = customerService.findByEmail(email);
        return customer;
    }

    private ModelAndView exceptionHandlerMaxReachedOrderNumberException(HttpServletRequest request, Customer customer) {
        ModelAndView modelAndView;
        modelAndView = UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService, request.getSession());
        modelAndView.addObject("errorMaxReachedOrderNumber", "you have too many unfinished orders!");
        return modelAndView;
    }

    private ModelAndView exceptionHandlerSuggestedPriceIsHighrThanBasePrice(/*OrderDto orderDto*/) {
        ModelAndView modelAndView;
        modelAndView = getAddOrderModelAndView(/*orderDto.getCustomer()*/);
        modelAndView.addObject("errorSuggestedPrice", "suggested price is more than base price!");
        return modelAndView;
    }

    @GetMapping("/viewSuggestions")
    public ModelAndView viewSpecialistSuggestions(@RequestParam("orderCode") String orderCode) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("viewSuggestionPage");
        List<SuggestionDto> suggestions = suggestionService.findSuggestionByOrder(orderCode);
        modelAndView.addObject("suggestions", suggestions);
        return modelAndView;
    }

    @RequestMapping("/selectSuggestion")
    public ModelAndView selectSpecialistSuggestion(@RequestParam("suggestionCode") String suggestionCode,
                                                   @RequestParam("orderCode") String orderCode,
                                                   HttpServletRequest request) {

        Customer customer = getCustomerByEmailFromHttpRequest(request);
        orderService.selectSpecialistSuggestion(orderCode, suggestionCode);
        return UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService, request.getSession());
    }

    @RequestMapping("/paymentByBalance")
    public ModelAndView balancePayment(@RequestParam("orderCode") String orderCode,
                                       HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Customer customer = customerService.findByEmail(email);
        ModelAndView modelAndView = UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService, request.getSession());
        try {
            orderService.paymentByBalance(email, orderCode);
        } catch (BalanceIsNotEnoughException e) {
            modelAndView.addObject("errorBalanceIsNotEnough", "your balance is not enough please pay by card!");
        } finally {
            return modelAndView;
        }

    }

    @GetMapping("/paymentByCard")
    public ModelAndView payment(@RequestParam("orderCode") String orderCode,
                                HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("paymentPage");
        Order order = orderService.findByOrderCode(orderCode);
        Customer customer = customerService.findByEmail(email);
        UserDto userDto = UserDto.builder().setEmail(customer.getEmail()).setName(customer.getName()).setFamily(customer.getFamily()).build();
        mav.addObject("order", order);
        mav.addObject("userDto", userDto);
        return mav;
    }

    @RequestMapping("/savePaymentByCard")
    public ModelAndView cardPayment(@RequestParam("orderCode") String orderCode,
                                    HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        orderService.paymentByCard(email, orderCode);
        Customer customer = customerService.findByEmail(email);
        return UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService, request.getSession());
    }

    @GetMapping("/newComment")
    public ModelAndView newComment(@RequestParam("orderCode") String orderCode) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("saveComment");
        mav.addObject("orderCode", orderCode);
        mav.addObject("commentDto", new CommentDto());
        return mav;
    }

    @RequestMapping("/saveComment")
    public ModelAndView saveComment(@ModelAttribute("commentDto") CommentDto commentDto,
                                    @RequestParam("orderCode") String orderCode,
                                    @RequestParam("point") Integer point,
                                    HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        if (point == null) point = 0;
        commentDto.setPoint(point);
        String errors = Validation.onCommentDto(commentDto);
        if (errors == "") {
            Order order = orderService.findByOrderCode(orderCode);
            order.setComment(CommentMapper.toComment(commentDto));
            orderService.save(order);
            Customer customer = customerService.findByEmail(email);
            return UserController.getCustomerAccountModelAndView(new ModelAndView(), customer,
                    orderService, request.getSession());
        } else {
            ModelAndView modelAndView = newComment(orderCode);
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }

    }

    @GetMapping("/timeout")
    public ModelAndView dashbord(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Customer customer = customerService.findByEmail(email);
        ModelAndView modelAndView = UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService, request.getSession());
        modelAndView.addObject("timeoutError", "your time is finished please try again!");
        return modelAndView;

    }


}
