package ir.maktab.web;

import com.google.gson.Gson;
import ir.maktab.data.dto.*;
import ir.maktab.data.entity.*;
import ir.maktab.data.enums.UserState;
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
@RequestMapping("/manager")
@Controller
public class ManagerController {

    private final CustomerServiceImpl customerService;
    private final ServiceServiceImpl serviceService;
    private final SpecialistServiceImpl specialistService;
    private final SubServiceServiceImpl subServiceService;
    private final ManagerServiceImpl managerService;

    @RequestMapping("/listCustomer")
    public ModelAndView getListCustomer(@RequestParam String name,
                                        @RequestParam String family,
                                        @RequestParam String email,
                                        @RequestParam("mEmail") String managerEmail) {

        return getModelAndViewListCustomer(name, family, email, managerEmail);
    }

    private ModelAndView getModelAndViewListCustomer(String name, String family, String email, String managerEmail) {
        List<CustomerDto> result;
        if (name.isEmpty() && family.isEmpty() && email.isEmpty()) {
            result = customerService.getAllCustomers();
        } else {
            result = customerService.filterCustomers(name, email, family);
        }
        ModelAndView mav = new ModelAndView("listCustomersPage");
        mav.addObject("listCustomers", result);
        mav.addObject("managerEmail", managerEmail);
        return mav;
    }

    @RequestMapping("/newService")
    public String newService(Map<String, Object> model, @RequestParam String email) {
        ServiceDto newService = new ServiceDto();
        model.put("newServiceObject", newService);
        model.put("email", email);
        return "saveServicePage";
    }

    @RequestMapping(value = "/saveService", method = RequestMethod.POST)
    public ModelAndView saveService(@ModelAttribute("newServiceObject") ServiceDto serviceDto,
                                    @RequestParam String email) {
        Manager manager = managerService.findByEmail(email);
        serviceService.save(serviceDto);
        return UserController.getManagerModelAndView(new ModelAndView(), manager);
    }

    @RequestMapping("/listSpecialist")
    public ModelAndView getListSpecialist(@RequestParam String name,
                                          @RequestParam String family,
                                          @RequestParam String email,
                                          @RequestParam("mEmail") String managerEmail) {
        return getListSpecialistsModelAndView(name, family, email, managerEmail);
    }

    private ModelAndView getListSpecialistsModelAndView(String name, String family, String email, String managerEmail) {
        List<SpecialistDto> result;
        if (name.isEmpty() && family.isEmpty() && email.isEmpty()) {
            result = specialistService.getAllSpecialists();
        } else {
            result = specialistService.filterSpecialists(name, email, family);
        }
        ModelAndView mav = new ModelAndView("listSpecialistPage");
        mav.addObject("listSpecialists", result);
        mav.addObject("managerEmail", managerEmail);
        return mav;
    }

    @RequestMapping("/addToSubService")
    public ModelAndView addSpecialistToSubService(@RequestParam String email) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("listSubServicesPage");

        UserDto specialist = UserDto.builder().setEmail(email).build();
        mav.addObject("specialist", specialist);

        List<SubServiceDto> subServiceDtoList = subServiceService.getAllSubServices();
        mav.addObject("subServiceDtoList", subServiceDtoList);

        return mav;
    }

    @RequestMapping("/saveSubServiceWithSpecialist")
    public String saveSpecialistToSubService(@RequestParam("email") String email,
                                             @RequestParam("subServiceName") String subServiceName) {

        Specialist specialist = specialistService.findByEmail(email);
        SubService subService = subServiceService.findByName(subServiceName);
        subServiceService.addSpecialistToSubService(subService, specialist);
        return "redirect:/";
    }

    @RequestMapping("/newSubService")
    public String newSubService(Map<String, Object> model, @RequestParam String email) {

        SubServiceDto subServiceDto = new SubServiceDto();
        model.put("newSubServiceObject", subServiceDto);

        Map<String, String> serviceList = new HashMap<>();
        serviceService.getServiceNames().forEach(i -> serviceList.put(i.getName(), i.getName()));
        model.put("services", serviceList);
        model.put("email", email);

        return "saveSubServicePage";
    }

    @PostMapping(value = "/saveSubService")
    public ModelAndView saveSubService(@ModelAttribute("newServiceObject") SubServiceDto subServiceDto,
                                       @RequestParam String email) {

        Service service = serviceService.findByName(subServiceDto.getService().getServiceName());
        subServiceDto.setService(service);
        subServiceService.save(subServiceDto);
        Manager manager = managerService.findByEmail(email);
        return UserController.getManagerModelAndView(new ModelAndView(), manager);
    }

    @RequestMapping(value = "/confirmCustomer")
    public ModelAndView customerConfirmation(@RequestParam("c") String customerEmail,
                                             @RequestParam("m") String managerEmail) {
        ModelAndView mav = new ModelAndView();
        try {
            Customer customer = customerService.findByEmail(customerEmail);
            customer.setState(UserState.CONFIRMED);
            customerService.update(customer);
            mav = getModelAndViewListCustomer("", "", "", managerEmail);
            mav.addObject("success", "user successfully confirmed!");
            return mav;
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getLocalizedMessage());
            mav.addObject("error", "something went wrong please login again!");
            mav.setViewName("loginPage");
            return mav;
        }

    }

    @RequestMapping(value = "/confirmSpecialist")
    public ModelAndView specialistConfirmation(@RequestParam("s") String specialistEmail,
                                               @RequestParam("m") String managerEmail) {
        ModelAndView mav = new ModelAndView();
        try {
            Specialist specialist = specialistService.findByEmail(specialistEmail);
            specialist.setState(UserState.CONFIRMED);
            specialistService.update(specialist);
            mav = getListSpecialistsModelAndView("", "", "", managerEmail);
            mav.addObject("success", "user successfully confirmed!");
            return mav;
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getLocalizedMessage());
            mav.addObject("error", "something went wrong please login again!");
            mav.setViewName("loginPage");
            return mav;
        }
    }





}
