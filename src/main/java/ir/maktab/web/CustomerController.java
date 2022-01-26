package ir.maktab.web;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Address;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.SubService;
import ir.maktab.service.CustomerServiceImpl;
import ir.maktab.service.OrderServiceImpl;
import ir.maktab.service.SubServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    private final OrderServiceImpl orderService;

    @RequestMapping("/list")
    public ModelAndView getListCustomer() {
        List<UserDto> userDtoList = customerService.getAllCustomers();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("listCustomersPage");
        mav.addObject("listCustomers", userDtoList);
        return mav;
    }

    @GetMapping("/addOrder")
    public ModelAndView addCustomerOrder(@RequestParam String email) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("saveOrderPage");
        Map<String, String> subServices = new HashMap<>();
        subServiceService.getAllSubServices().forEach(i -> subServices.put(i.getSubServiceName(), i.getSubServiceName()));
        OrderDto orderDto = OrderDto.builder().setCustomer(email).build();
        mav.addObject("subServices", subServices);
        mav.addObject("orderDto", orderDto);
        return mav;
    }

    @PostMapping("/saveOrder")
    public ModelAndView saveCustomerOrder(@ModelAttribute("orderDto") OrderDto orderDto) {

        Customer customer = customerService.findByEmail(orderDto.getCustomer());
        SubService subService = subServiceService.findByName(orderDto.getSubService());
        Address address = new Address();
        orderService.addCustomerOrder(customer, subService, orderDto.getSuggestedPrice(), orderDto.getExplanations(), address, orderDto.getStartDate());

        ModelAndView modelAndView = new ModelAndView();
        return UserController.getCustomerAccountModelAndView(modelAndView,customer,orderService);

    }
}
