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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/saveOrder")
    public ModelAndView addCustomerOrder(@RequestParam("email")String email) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("saveOrderPage");
        Map<String, String> subServices = new HashMap<>();
        subServiceService.getAllSubServices().forEach(i -> subServices.put(i.getSubServiceName(), i.getSubServiceName()));
        UserDto userDto = UserDto.builder().setEmail(email).build();
        mav.addObject("subServices", subServices);
        mav.addObject("orderDto", new OrderDto());
        mav.addObject("userDto",userDto);
        return mav;
    }

    @PostMapping("/saveOrder")
    public ModelAndView saveCustomerOrder(@RequestParam("email") String email, @RequestParam("subService") String subServiceName,
                                          @RequestParam("suggestedPrice") double suggestedPrice, @RequestParam("explanations") String explanations, @RequestParam("startDate") Date startDate, @RequestParam("city") String city, @RequestParam("plaque") String plaque,
                                          @RequestParam("addressExplanations") String addressExplanations) {

        Customer customer = customerService.findByEmail(email);
        SubService subService = subServiceService.findByName(subServiceName);
        Address address = Address.builder().setCity(city).setPlaque(plaque).setExplanations(addressExplanations).build();
        orderService.addCustomerOrder(customer, subService, suggestedPrice, explanations, address, startDate);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("customerAccountPage");
        return mav;

    }
}
