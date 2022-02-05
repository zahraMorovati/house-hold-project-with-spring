package ir.maktab.web;

import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.dto.mappers.UserMapper;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Manager;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.enums.OrderState;
import ir.maktab.exception.UserEceptions.DuplicatedEmailException;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.exception.managerExceptions.ManagerNotFoundException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final CustomerServiceImpl customerService;
    private final SpecialistServiceImpl specialistService;
    private final ManagerServiceImpl managerService;
    private final OrderServiceImpl orderService;
    private final SubServiceServiceImpl subServiceService;

    @RequestMapping("/newUser")
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
                return getSpecialistModelAndView(modelAndView, specialist,orderService,subServiceService);
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

    public static ModelAndView getManagerModelAndView(ModelAndView modelAndView, Manager manager) {
        UserDto managerDto = UserMapper.toUserDto(manager.getName(), manager.getFamily(), manager.getEmail());
        modelAndView.setViewName("managerAccountPage");
        modelAndView.addObject("userDto", managerDto);
        return modelAndView;
    }

    public static ModelAndView getSpecialistModelAndView(ModelAndView modelAndView, Specialist specialist,OrderServiceImpl orderService,SubServiceServiceImpl subServiceService){
        List<OrderDto> orderList = orderService.getSpecialistOrders(specialist.getEmail());
        UserDto specialistDto = UserMapper.toUserDto(specialist.getName(), specialist.getFamily(), specialist.getEmail(), specialist.getBalance());
        List<String> subServices = subServiceService.getSpecialistSubServices(specialist.getEmail());
        List<OrderDto> availableOrders = orderService.getSpecialistAvailableOrders(specialist.getEmail());
        List<OrderDto> specialistOrders = orderService.getSpecialistOrders(specialist.getEmail());
        //todo add pic
        modelAndView.setViewName("specialistAccountPage");
        modelAndView.addObject("userDto", specialistDto);
        modelAndView.addObject("orders", orderList);
        modelAndView.addObject("subServices",subServices);
        modelAndView.addObject("availableOrders",availableOrders);
        modelAndView.addObject("specialistOrders",specialistOrders);

        return modelAndView;
    }

    public static ModelAndView getCustomerAccountModelAndView(ModelAndView modelAndView, Customer customer,OrderServiceImpl orderService) {
        List<OrderDto> orderList = orderService.getCustomerOrders(customer.getEmail());
        List<OrderDto> paymentOrders = orderService.getCustomerOrdersByOrderState(customer.getEmail(), OrderState.DONE);
        UserDto customerDto = UserMapper.toUserDto(customer.getName(), customer.getFamily(), customer.getEmail(), customer.getBalance());
        modelAndView.setViewName("customerAccountPage");
        modelAndView.addObject("userDto", customerDto);
        modelAndView.addObject("orders", orderList);
        modelAndView.addObject("paymentOrders",paymentOrders);
        return modelAndView;
    }

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ModelAndView loginExceptionHandler(CustomerNotFoundException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "wrong email or password!");
        return new ModelAndView("loginPage", model);
    }

    @ExceptionHandler(value = SpecialistNotFoundException.class)
    public ModelAndView loginExceptionHandler(SpecialistNotFoundException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "wrong email or password!");
        return new ModelAndView("loginPage", model);
    }

    @ExceptionHandler(value = ManagerNotFoundException.class)
    public ModelAndView loginExceptionHandler(ManagerNotFoundException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "wrong email or password!");
        return new ModelAndView("loginPage", model);
    }

    @ExceptionHandler(value = DuplicatedEmailException.class)
    public ModelAndView signupExceptionHandler(DuplicatedEmailException ex) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", "this email has been used before!");
        model.put("userDto", new UserDto());
        return new ModelAndView("signup", model);
    }

}
