package ir.maktab.web;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.dto.mappers.UserMapper;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Manager;
import ir.maktab.data.entity.Specialist;
import ir.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final CustomerServiceImpl customerService;
    private final SpecialistServiceImpl specialistService;
    private final ManagerServiceImpl managerService;
    private final OrderServiceImpl orderService;

    @RequestMapping("/new")
    public String newUser(Map<String, Object> model) {
        UserDto userDto = new UserDto();
        model.put("userDto", userDto);
        return "signup";
    }

    @PostMapping(value = "/signup")
    public ModelAndView register(@ModelAttribute("userDto") UserDto userDto,
                                 @RequestParam("image") CommonsMultipartFile image) {

        userService.saveUserByType(userDto, image);
        return userLogin();
    }

    @RequestMapping("/signIn")
    public ModelAndView userLogin() {
        return new ModelAndView("loginPage");
    }

    @PostMapping("/login")
    public ModelAndView userLoginEvaluation(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("userType") String userType) {

        ModelAndView modelAndView = new ModelAndView();

        if(userType.equalsIgnoreCase("customer")){
            Customer customer = customerService.findByEmailAndPassword(email, password);
            if (customer != null) {
                return getCustomerAccountModelAndView(modelAndView, customer,orderService);
            }
        }else if(userType.equalsIgnoreCase("specialist")){
            Specialist specialist = specialistService.findByEmailAndPassword(email, password);
            if (specialist != null) {
                return getSpecialistModelAndView(modelAndView, specialist);
            }
        }else if(userType.equalsIgnoreCase("manager")){
            Manager manager = managerService.findByEmailAndPassword(email, password);
            if (manager != null) {
                return getManagerModelAndView(modelAndView, manager);
            }
        }

        modelAndView.setViewName("/");
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }

    private ModelAndView getManagerModelAndView(ModelAndView modelAndView, Manager manager) {
        UserDto managerDto = UserMapper.toUserDto(manager.getName(), manager.getFamily(), manager.getEmail());
        modelAndView.setViewName("managerAccountPage");
        modelAndView.addObject("userDto", managerDto);
        return modelAndView;
    }

    private ModelAndView getSpecialistModelAndView(ModelAndView modelAndView, Specialist specialist) {
        List<OrderDto> orderList = orderService.getSpecialistOrders(specialist.getEmail());
        UserDto specialistDto = UserMapper.toUserDto(specialist.getName(), specialist.getFamily(), specialist.getEmail(), specialist.getBalance());
        modelAndView.setViewName("specialistAccountPage");
        modelAndView.addObject("userDto", specialistDto);
        modelAndView.addObject("orders", orderList);
        // add pic
        return modelAndView;
    }

    public static ModelAndView getCustomerAccountModelAndView(ModelAndView modelAndView, Customer customer,OrderServiceImpl orderService) {
        List<OrderDto> orderList = orderService.getCustomerOrders(customer.getEmail());
        UserDto customerDto = UserMapper.toUserDto(customer.getName(), customer.getFamily(), customer.getEmail(), customer.getBalance());
        modelAndView.setViewName("customerAccountPage");
        modelAndView.addObject("userDto", customerDto);
        modelAndView.addObject("orders", orderList);
        return modelAndView;
    }

}
