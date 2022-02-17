package ir.maktab.web;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.SuggestionDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Manager;
import ir.maktab.data.entity.Order;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.SubService;
import ir.maktab.data.enums.OrderState;
import ir.maktab.data.validators.Validation;
import ir.maktab.exception.specialistExceptions.DuplicatedSpecialist;
import ir.maktab.exception.suggestionExceptions.SuggestedPriceIsHigherThanBasePriceException;
import ir.maktab.service.OrderServiceImpl;
import ir.maktab.service.SpecialistServiceImpl;
import ir.maktab.service.SubServiceServiceImpl;
import ir.maktab.service.SuggestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/specialist")
@Controller
public class SpecialistController {

    private final SpecialistServiceImpl specialistService;
    private final SubServiceServiceImpl subServiceService;
    private final SuggestionServiceImpl suggestionService;
    private final OrderServiceImpl orderService;

    @RequestMapping("/dashbord")
    public ModelAndView backToDashbord(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        Specialist specialist = specialistService.findByEmail(email);
        return UserController.getSpecialistModelAndView(new ModelAndView(),specialist,orderService,subServiceService,request.getSession());
    }

    @GetMapping("/addSuggestion")
    public ModelAndView addSpecialistSuggestion(@RequestParam("orderCode") String orderCode/*,
                                                @RequestParam("email") String email*/) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("saveSuggestionPage");
        SuggestionDto suggestionDto = SuggestionDto.builder().setOrderCode(orderCode).build();
        modelAndView.addObject("suggestionDto", suggestionDto);
        return modelAndView;
    }

    @PostMapping("/saveSuggestion")
    public ModelAndView saveSpecialistSuggestion(@ModelAttribute("suggestionDto") SuggestionDto suggestionDto,
                                                 @RequestParam("timePicker") String timePicker,HttpServletRequest request) {
        try {
            Date startTime = getDate(timePicker);
            ModelAndView modelAndView = addSpecialistSuggestion(suggestionDto.getOrderCode());
            String email = (String)request.getSession().getAttribute("email");
            suggestionDto.setSpecialistEmail(email);
            suggestionDto.setStartTime(startTime);
            String saveSuggestionErrors = Validation.onSuggestionDto(suggestionDto);
            if(saveSuggestionErrors.equals("")){
                suggestionService.addSpecialistSuggestion(suggestionDto.getOrderCode(),
                        suggestionDto.getSpecialistEmail(), suggestionDto.getSuggestedPrice(),
                        suggestionDto.getWorkHour(), suggestionDto.getStartTime());
                modelAndView.addObject("suggestionSaved","suggestion saved successfully!");
                return modelAndView;
            }else {
                modelAndView.addObject("saveSuggestionErrors",saveSuggestionErrors);
                return modelAndView;
            }


        } catch (SuggestedPriceIsHigherThanBasePriceException e) {
            return exceptionHandlerSpecialistSaveSuggestion(suggestionDto);
        }
    }

    private ModelAndView exceptionHandlerSpecialistSaveSuggestion(SuggestionDto suggestionDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("saveSuggestionPage");
        suggestionDto = SuggestionDto.builder()
                .setOrderCode(suggestionDto.getOrderCode())
                .setSpecialistEmail(suggestionDto.getSpecialistEmail()).build();
        modelAndView.addObject("suggestionDto", suggestionDto);
        modelAndView.addObject("error", "suggested price is more than base price!");
        return modelAndView;
    }

    private Date getDate(String timePicker) {
        if (!timePicker.isEmpty()) {
            System.out.println(timePicker);
            String[] time = timePicker.split(":");
            if (time.length >= 2)
                return new Date(0, 0, 0, Integer.parseInt(time[0]), Integer.parseInt(time[1]), 0);
        }
        return null;
    }

    @RequestMapping("/newSubService")
    public ModelAndView addSpecialistToSubService(/*@RequestParam String email*/) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("saveSubServiceSpecialistPage");
        /*UserDto userDto = UserDto.builder().setEmail(email).build();*/
        mav.addObject("userDto", new UserDto());
        List<SubServiceDto> subServiceDtoList = subServiceService.getAllSubServices();
        mav.addObject("subServiceDtoList", subServiceDtoList);
        return mav;
    }

    @RequestMapping("/saveSubService")
    public ModelAndView saveSpecialistSubService(@RequestParam String subServiceName,
                                                 HttpServletRequest request) {
        String email = (String)request.getSession().getAttribute("email");
        Specialist specialist = specialistService.findByEmail(email);
        SubService subService = subServiceService.findByName(subServiceName);
        subServiceService.addSpecialistToSubService(subService, specialist);
        ModelAndView modelAndView = addSpecialistToSubService();
        modelAndView.addObject("specialistSavedToSubService","this sub service added to your sub service list!");
        return modelAndView;
    }

    @RequestMapping("/completeOrder")
    public ModelAndView completeOrderBySpecialist(@RequestParam("orderCode") String orderCode,
                                                  HttpServletRequest request) {
        String email = (String)request.getSession().getAttribute("email");
        Order order = orderService.findByOrderCode(orderCode);
        Specialist specialist = specialistService.findByEmail(email);
        ModelAndView modelAndView = UserController.getSpecialistModelAndView(new ModelAndView(), specialist, orderService, subServiceService,request.getSession());
        if (order.getOrderState().equals(OrderState.PAID) || order.getOrderState().equals(OrderState.STARTED) || order.getOrderState().equals(OrderState.DONE)) {
            modelAndView.addObject("errorCompleteOrder", "this order is completed!");
        } else {
            order.setOrderState(OrderState.DONE);
            orderService.update(order);
        }
        return modelAndView;
    }

    @ExceptionHandler(DuplicatedSpecialist.class)
    public ModelAndView duplicatedSpecialist(){
        ModelAndView modelAndView = addSpecialistToSubService();
        modelAndView.addObject("duplicatedSubService","this sub service is already in your list!");
        return modelAndView;
    }




}
