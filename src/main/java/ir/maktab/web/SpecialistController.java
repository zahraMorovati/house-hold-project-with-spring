package ir.maktab.web;

import ir.maktab.data.dto.SubServiceDto;
import ir.maktab.data.dto.UserDto;
import ir.maktab.data.entity.Specialist;
import ir.maktab.data.entity.SubService;
import ir.maktab.service.SpecialistServiceImpl;
import ir.maktab.service.SubServiceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/specialist")
@Controller
public class SpecialistController {

    private final SpecialistServiceImpl specialistService;
    private final SubServiceServiceImpl subServiceService;

    @RequestMapping("/list")
    public ModelAndView getListSpecialist(){
        List<UserDto> userDtoList = specialistService.getAllSpecialists();
        ModelAndView mav = new ModelAndView();
        mav.setViewName("listSpecialistPage");
        mav.addObject("listSpecialists", userDtoList);
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
                                             @RequestParam("subServiceName") String subServiceName ) {

        Specialist specialist = specialistService.findByEmail(email);
        SubService subService = subServiceService.findByName(subServiceName);
        subServiceService.addSpecialistToSubService(subService,specialist);
        return "redirect:/";
    }
}
