package ir.maktab.web;

import ir.maktab.data.dto.UserDto;
import ir.maktab.service.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@RequiredArgsConstructor
@RequestMapping("/customer")
@Controller
public class CustomerController {

    private final CustomerServiceImpl customerService;

    @RequestMapping("/list")
    public ModelAndView getListCustomer(){
        List<UserDto> userDtoList = customerService.getAllCustomers();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("listCustomersPage");
        mav.addObject("listCustomers", userDtoList);
        return mav;
    }
}
