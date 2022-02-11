package ir.maktab.web;

import ir.maktab.config.LastViewInterceptor;
import ir.maktab.data.dto.OrderDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.dto.mappers.UserMapper;
import ir.maktab.data.entity.Customer;
import ir.maktab.data.entity.Manager;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.enums.OrderState;
import ir.maktab.data.enums.UserState;
import ir.maktab.data.validators.Validation;
import ir.maktab.exception.UserEceptions.DuplicatedEmailException;
import ir.maktab.exception.UserEceptions.UserNotConfirmedException;
import ir.maktab.exception.customerExceptions.CustomerNotFoundException;
import ir.maktab.exception.managerExceptions.ManagerNotFoundException;
import ir.maktab.exception.specialistExceptions.SpecialistNotFoundException;
import ir.maktab.service.*;
import ir.maktab.util.Convert;
import ir.maktab.util.SendEmail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final CustomerServiceImpl customerService;
    private final SpecialistServiceImpl specialistService;
    private final ManagerServiceImpl managerService;
    private final OrderServiceImpl orderService;
    private final SubServiceServiceImpl subServiceService;

    @RequestMapping("/signup")
    public String newUser(Map<String, Object> model) {
        UserDto userDto = new UserDto();
        model.put("userDto", userDto);
        return "signup";
    }

    @RequestMapping(value = "/doSignup")
    public ModelAndView register(@ModelAttribute("userDto") UserDto userDto,
                                 @RequestParam(value = "image",required = false) CommonsMultipartFile image) {

        ModelAndView modelAndView = new ModelAndView();
        String errors = Validation.onRegister(userDto);
        if(errors.equals("")){
            String verificationCode = getRandomCode();
            SendEmail.sendEmail(userDto.getEmail(),"betterHouse email verification","your verify code is: "+verificationCode);
            modelAndView.setViewName("confirmEmailPage");
            modelAndView.addObject("verificationCode", Convert.toHexString(verificationCode));
            modelAndView.addObject("email",userDto.getEmail());
            modelAndView.addObject("userType",userDto.getUserType());
            userService.saveUserByType(userDto, userDto.getImage());
            return modelAndView;
        }else {
            modelAndView.addObject("signupErrors",errors);
            modelAndView.setViewName("signup");
            return modelAndView;
        }

    }

    @RequestMapping(value = "/confirmEmail")
    public ModelAndView confirmEmail(@RequestParam("email")String email,
                                     @RequestParam("userType")String userType,
                                     @RequestParam("v")String verificationCode,
                                     @RequestParam("code")String code) {
        ModelAndView modelAndView = new ModelAndView();
        verificationCode = Convert.fromHexString(verificationCode);
        if(verificationCode.equals(code)){
            if(userType.equalsIgnoreCase("customer")){
                customerService.updateCustomerState(UserState.WAITING_FOR_CONFIRM,email);
            }else{
                specialistService.updateSpecialistState(UserState.WAITING_FOR_CONFIRM,email);
            }
            modelAndView.setViewName("userRegisteredSuccessfullyPage");
            return modelAndView;
        }else {
            if(userType.equalsIgnoreCase("customer")){
                customerService.delete(email);
            }else {
                specialistService.delete(email);
            }
            modelAndView.setViewName("signup");
            modelAndView.addObject("userDto", new UserDto());
            modelAndView.addObject("emailNotValid","your email is not valid, please try again!");
            return modelAndView;
        }

    }

    @RequestMapping("/signIn")
    public ModelAndView userLogin() {
        return new ModelAndView("loginPage");
    }

    @PostMapping("/login")
    public ModelAndView userLoginEvaluation(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("userType") String userType,
                                            HttpServletRequest httpServletRequest){

        ModelAndView modelAndView = new ModelAndView();
        HttpSession session;
        if(userType.equalsIgnoreCase("customer")){
            Customer customer = customerService.findByEmailAndPassword(email, password);
            if (customer != null) {
                session=httpServletRequest.getSession();
                session.setAttribute("testSession","hello");
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

    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, ex.getBindingResult().getModel());
    }

    @ExceptionHandler(value = UserNotConfirmedException.class)
    public ModelAndView handleUserNotConfirmedException(){
        ModelAndView modelAndView = new ModelAndView();
        String message = "you're not confirmed yet come back <br/> after you received our confirmation email!";
        modelAndView.addObject("UserNotConfirmed",message);
        modelAndView.setViewName("loginPage");
        return modelAndView;
    }

    private String getRandomCode() {
        Random random = new Random();
        int randomNumber = random.nextInt();
        if (randomNumber < 0) {
            return getRandomCode();
        } else {
           return String.valueOf(randomNumber).substring(0, 5);
        }
    }

}
