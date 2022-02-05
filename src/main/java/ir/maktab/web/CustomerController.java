package ir.maktab.web;

import com.google.gson.Gson;
import ir.maktab.data.dto.CommentDto;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.dto.mappers.CommentMapper;
import ir.maktab.data.entity.Address;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.SubService;
import ir.maktab.exception.UserEceptions.UserNotConfirmedException;
import ir.maktab.exception.customerExceptions.BalanceIsNotEnoughException;
import ir.maktab.exception.orderExceptions.MaxReachedOrderNumberException;
import ir.maktab.exception.suggestionExceptions.SuggestedPriceIsHigherThanBasePriceException;
import ir.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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


    @GetMapping("/addOrder")
    public ModelAndView addCustomerOrder(@RequestParam String email) {
        return getAddOrderModelAndView(email);
    }

    private ModelAndView getAddOrderModelAndView(String email) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("saveOrderPage");
        Map<String, String> services = new HashMap<>();
        /*serviceService.getServiceNames().forEach(i -> services.put(i.getName(), i.getName()));*/
        subServiceService.getAllSubServices().forEach(i -> services.put(i.getSubServiceName(), i.getSubServiceName()));
        OrderDto orderDto = OrderDto.builder().setCustomer(email).build();
        /*mav.addObject("services", services);*/
        mav.addObject("subServices", services);
        mav.addObject("orderDto", orderDto);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/loadSubServiceByName/{name}", method = RequestMethod.GET)
    public String loadSubServices(@PathVariable("name") String name) {
        Gson gson = new Gson();
        return gson.toJson(subServiceService.findSubServiceByServiceName(name));
    }

    @RequestMapping("/saveOrder")
    public ModelAndView saveCustomerOrder(@ModelAttribute(name = "orderDto") OrderDto orderDto,
                                          @RequestParam(value = "date", required = false) String startDate) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(startDate);
            Customer customer = customerService.findByEmail(orderDto.getCustomer());
            SubService subService = subServiceService.findByName(orderDto.getSubService());
            Address address = new Address();
            orderService.addCustomerOrder(customer, subService, orderDto.getSuggestedPrice(), orderDto.getExplanations(), address, date);
            return UserController.getCustomerAccountModelAndView(modelAndView, customer, orderService);

        } catch (SuggestedPriceIsHigherThanBasePriceException basePriceException) {
            modelAndView = getAddOrderModelAndView(orderDto.getCustomer());
            modelAndView.addObject("errorSuggestedPrice", "suggested price is more than base price!");
            return modelAndView;
        } catch (MaxReachedOrderNumberException maxReachedOrderNumberException) {
            modelAndView = UserController.getCustomerAccountModelAndView(new ModelAndView(), customerService.findByEmail(orderDto.getCustomer()), orderService);
            modelAndView.addObject("errorMaxReachedOrderNumber", "you have too many unfinished orders!");
            return modelAndView;
        } catch (UserNotConfirmedException userNotConfirmedException) {
            modelAndView = UserController.getCustomerAccountModelAndView(new ModelAndView(), customerService.findByEmail(orderDto.getCustomer()), orderService);
            modelAndView.addObject("errorUserNotConfirmed", "you're not confirmed yet!");
            return modelAndView;
        }


    }

    @GetMapping("/viewSuggestions")
    public ModelAndView viewSpecialistSuggestions(@RequestParam("orderCode") String orderCode,
                                                  @RequestParam("email") String email) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("viewSuggestionPage");
        List<SuggestionDto> suggestions = suggestionService.findSuggestionByOrder(orderCode);
        modelAndView.addObject("suggestions", suggestions);
        modelAndView.addObject("email", email);
        return modelAndView;
    }

    @RequestMapping("/selectSuggestion")
    public ModelAndView selectSpecialistSuggestion(@RequestParam("suggestionCode") String suggestionCode,
                                                   @RequestParam("email") String customerEmail,
                                                   @RequestParam("orderCode") String orderCode) {

        Customer customer = customerService.findByEmail(customerEmail);
        orderService.selectSpecialistSuggestion(orderCode, suggestionCode);
        return UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService);
    }

    @RequestMapping("/paymentByBalance")
    public ModelAndView balancePayment(@RequestParam("orderCode") String orderCode,
                                       @RequestParam("email") String email) {
        Customer customer = customerService.findByEmail(email);
        ModelAndView modelAndView = UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService);
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
                                @RequestParam("email") String email) {
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
                                    @RequestParam("email") String email) {

        orderService.paymentByCard(email, orderCode);
        Customer customer = customerService.findByEmail(email);
        return UserController.getCustomerAccountModelAndView(new ModelAndView(), customer, orderService);
    }

    @GetMapping("/newComment")
    public ModelAndView newComment(@RequestParam("orderCode") String orderCode,
                                   @RequestParam("email") String email) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("saveComment");
        mav.addObject("orderCode", orderCode);
        mav.addObject("email", email);
        mav.addObject("commentDto", new CommentDto());
        return mav;
    }

    @RequestMapping("/saveComment")
    public ModelAndView saveComment(@ModelAttribute("commentDto") CommentDto commentDto,
                                    @RequestParam("orderCode") String orderCode,
                                    @RequestParam("email") String email,
                                    @RequestParam("rating")int point) {
        commentDto.setPoint(point);
        Order order = orderService.findByOrderCode(orderCode);
        order.setComment(CommentMapper.toComment(commentDto));
        orderService.save(order);
        Customer customer = customerService.findByEmail(email);
        return UserController.getCustomerAccountModelAndView(new ModelAndView(),customer,orderService);
    }

    @GetMapping("/timeout")
    public ModelAndView dashbord(@RequestParam("email") String email){
        System.out.println(email);
        Customer customer = customerService.findByEmail(email);
        return UserController.getCustomerAccountModelAndView(new ModelAndView(),customer,orderService);
    }


}
