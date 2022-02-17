package ir.maktab.web;

import ir.maktab.data.dto.CustomerDto;
import ir.maktab.data.dto.ServiceDto;
import ir.maktab.data.dto.SpecialistDto;
import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.entity.Manager;
import ir.maktab.data.entity.Service;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.SubService;
import ir.maktab.data.validators.Validation;
import ir.maktab.exception.serviceExceptions.DuplicatedServiceException;
import ir.maktab.exception.specialistExceptions.DuplicatedSpecialist;
import ir.maktab.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/dashbord")
    public ModelAndView backToDashbord(HttpServletRequest httpServletRequest) {
        String email = (String) httpServletRequest.getSession().getAttribute("email");
        Manager manager = managerService.findByEmail(email);
        return UserController.getManagerModelAndView(new ModelAndView(), manager, httpServletRequest.getSession());
    }

    @RequestMapping("/listCustomer")
    public ModelAndView getListCustomer(@RequestParam String name,
                                        @RequestParam String family,
                                        @RequestParam String email/*,
                                        @RequestParam("mEmail") String managerEmail*/
            /* HttpServletRequest request*/) {

        /*String emailManager = (String) request.getSession().getAttribute("email");*/
        return getModelAndViewListCustomer(name, family, email/*, emailManager*/);
    }

    private ModelAndView getModelAndViewListCustomer(String name, String family, String email/*, String managerEmail*/) {
        List<CustomerDto> result;
        if (name.isEmpty() && family.isEmpty() && email.isEmpty()) {
            result = customerService.getAllNotConfirmedCustomers();
        } else {
            result = customerService.filterNotConfirmedCustomers(name, email, family);
        }
        ModelAndView mav = new ModelAndView("listCustomersPage");
        mav.addObject("listCustomers", result);
        /*mav.addObject("managerEmail", managerEmail);*/
        return mav;
    }

    @RequestMapping("/newService")
    public ModelAndView newService() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("saveServicePage");
        modelAndView.addObject("newServiceObject", new ServiceDto());
        return modelAndView;
    }

    @RequestMapping(value = "/saveService", method = RequestMethod.POST)
    public ModelAndView saveService(@ModelAttribute("newServiceObject") ServiceDto serviceDto,
                                    HttpServletRequest httpServletRequest) {

        String email = (String) httpServletRequest.getSession().getAttribute("email");
        Manager manager = managerService.findByEmail(email);
        serviceService.save(serviceDto);
        return UserController.getManagerModelAndView(new ModelAndView(), manager, httpServletRequest.getSession());
    }

    @RequestMapping("/listSpecialist")
    public ModelAndView getListSpecialist(@RequestParam String name,
                                          @RequestParam String family,
                                          @RequestParam String email
            /*   @RequestParam("mEmail") String managerEmail*/) {
        return getListSpecialistsModelAndView(name, family, email/*, managerEmail*/);
    }

    private ModelAndView getListSpecialistsModelAndView(String name, String family, String email/*, String managerEmail*/) {
        List<SpecialistDto> result;
        if (name.isEmpty() && family.isEmpty() && email.isEmpty()) {
            result = specialistService.getAllNotConfirmedSpecialist();
        } else {
            result = specialistService.filterNotConfirmedSpecialists(name, email, family);
        }
        ModelAndView mav = new ModelAndView("listSpecialistPage");
        mav.addObject("listSpecialists", result);
        /*mav.addObject("managerEmail", managerEmail);*/
        return mav;
    }

    @GetMapping("/save-specialist-to-subService")
    public ModelAndView addSpecialistToSubService() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("add-specialist-to-subService");
        Map<String, String> services = new HashMap<>();
        serviceService.getServiceNames().forEach(i -> services.put(i.getName(), i.getName()));
        List<SpecialistDto> allSpecialists = specialistService.getAllSpecialists();
        mav.addObject("services", services);
        mav.addObject("specialists", allSpecialists);
        mav.addObject("subService",new SubServiceDto());
        return mav;
    }

    @PostMapping("/add-specialist-to-subService")
    public ModelAndView saveSpecialistToSubService(@RequestParam("subService") String subServiceName,
                                                   @RequestParam("email") String email) {

        try {

            String errors = Validation.onSaveSpecialistToSubService(email, subServiceName);
            if (errors == "") {
                ModelAndView modelAndView = addSpecialistToSubService();
                Specialist specialist = specialistService.findByEmail(email);
                SubService subService = subServiceService.findByName(subServiceName);
                subServiceService.addSpecialistToSubService(subService, specialist);
                modelAndView.addObject("specialistAddedSuccessfully","specialist successfully added!");
                return modelAndView;
            }else {
                ModelAndView modelAndView = addSpecialistToSubService();
                modelAndView.addObject("errors", errors);
                return modelAndView;
            }

        } catch (DuplicatedSpecialist duplicatedSpecialist) {
            ModelAndView modelAndView = addSpecialistToSubService();
            modelAndView.addObject("duplicatedSubService", "this sub service is already in specialist sub services list!");
            return modelAndView;
        }


    }

    @RequestMapping("/newSubService")
    public ModelAndView newSubService() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newSubServiceObject", new SubServiceDto());
        Map<String, String> serviceList = new HashMap<>();
        serviceService.getServiceNames().forEach(i -> serviceList.put(i.getName(), i.getName()));
        modelAndView.addObject("services", serviceList);
        modelAndView.setViewName("saveSubServicePage");
        return modelAndView;
    }

    @PostMapping(value = "/saveSubService")
    public ModelAndView saveSubService(@ModelAttribute("newServiceObject") SubServiceDto subServiceDto,
            /*@RequestParam String email,*/ HttpServletRequest httpServletRequest) {

        ModelAndView modelAndView = newSubService();
        try {
            Service service = serviceService.findByName(subServiceDto.getService().getServiceName());
            subServiceDto.setService(service);
            subServiceService.save(subServiceDto);
            modelAndView.addObject("subServiceSaved", "sub service saved successfully!");
            return modelAndView;
        } catch (Exception e) {
            modelAndView.addObject("duplicatedSubService", "this sub service has been saved before!");
            return modelAndView;
        }

    }

    @RequestMapping(value = "/confirmCustomer")
    public ModelAndView customerConfirmation(@RequestParam("c") String customerEmail
            /*@RequestParam("m") String managerEmail*/
            /*       HttpServletRequest request*/) {
        /*String email = (String)request.getSession().getAttribute("email");*/
        ModelAndView mav = new ModelAndView();
        try {
            customerService.confirmCustomer(customerEmail);
            mav = getModelAndViewListCustomer("", "", ""/*, email*/);
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
    public ModelAndView specialistConfirmation(@RequestParam("s") String specialistEmail/*,
                                               @RequestParam("m") String managerEmail*/) {
        ModelAndView mav = new ModelAndView();
        try {
            specialistService.confirmSpecialist(specialistEmail);
            mav = getListSpecialistsModelAndView("", "", ""/*, managerEmail*/);
            mav.addObject("success", "user successfully confirmed!");
            return mav;
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.getLocalizedMessage());
            mav.addObject("error", "something went wrong please login again!");
            mav.setViewName("loginPage");
            return mav;
        }
    }

    @ExceptionHandler(DuplicatedServiceException.class)
    public ModelAndView duplicatedServiceException() {
        ModelAndView modelAndView = newService();
        modelAndView.addObject("duplicatedService", "this service has been saved before!");
        return modelAndView;
    }

    @GetMapping("/advanced-filter-specialists")
    public String getAdvancedFilterSpecialists() {
        return "advanced-filter-specialist-page";
    }

    @GetMapping("/advanced-filter-customers")
    public String getAdvancedFilterCustomers() {
        return "advanced-filter-customer-page";
    }

    @GetMapping("/advanced-filter-orders")
    public String getAdvancedFilterOrders() {
        return "advanced-filter-orders-page";
    }


}
